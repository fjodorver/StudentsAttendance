package ee.ttu.vk.sa.pages.statistics;

import com.google.common.collect.Lists;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.BootstrapForm;
import ee.ttu.vk.sa.CustomAuthenticatedWebSession;
import ee.ttu.vk.sa.domain.*;
import ee.ttu.vk.sa.pages.AbstractPage;
import ee.ttu.vk.sa.pages.providers.StudentDataProvider;
import ee.ttu.vk.sa.service.AttendanceService;
import ee.ttu.vk.sa.service.GroupService;
import ee.ttu.vk.sa.service.StudentService;
import ee.ttu.vk.sa.service.SubjectService;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * Created by fjodor on 2.03.16.
 */
public class StatisticsPage extends AbstractPage {
    @SpringBean
    private AttendanceService attendanceService;

    @SpringBean
    private SubjectService subjectService;

    @SpringBean
    private StudentService studentService;

    @SpringBean
    private GroupService groupService;

    private Subject subject;
    private Group group;

    private StudentDataProvider studentDataProvider;
    private WebMarkupContainer body;
    private BootstrapForm<Void> searchForm;

    public StatisticsPage() {
        studentDataProvider = new StudentDataProvider();
        studentDataProvider.setFilterState(new Student().setCode("***"));
        add(getStudentsTable(studentDataProvider));
        add(searchForm = getSearchForm());

    }

    private BootstrapForm<Void> getSearchForm() {
        List<Subject> subjects = subjectService.findAll(CustomAuthenticatedWebSession.getSession().getTeacher());
        List<Group> groups = Lists.newArrayList();
        subjects.forEach(x -> groups.addAll(x.getGroups()));
        BootstrapForm<Void> form = new BootstrapForm<>("searchForm");
        form.add(new DropDownChoice<>("subject", new PropertyModel<>(this, "subject"), subjects).add(getOnUpdate()));
        form.add(new DropDownChoice<>("group", new PropertyModel<>(this, "group"), groups).add(getOnUpdate()));
        return form;
    }

    private AjaxFormComponentUpdatingBehavior getOnUpdate() {
        return new AjaxFormComponentUpdatingBehavior("change") {
            @Override
            protected void onUpdate(AjaxRequestTarget ajaxRequestTarget) {
                if(subject != null && group != null && groupService.findAll(Lists.newArrayList(subject)).get(0).equals(group))
                    studentDataProvider.setFilterState(new Student().setGroup(group));
                else
                    studentDataProvider.setFilterState(new Student().setCode("***"));
                ajaxRequestTarget.add(body);
            }
        };
    }


    private WebMarkupContainer getStudentsTable(IDataProvider<Student> dataProvider) {
        body = new WebMarkupContainer("body");
        body.setOutputMarkupId(true);
        DataView<Student> rows = new DataView<Student>("rows", dataProvider) {
            @Override
            protected void populateItem(Item<Student> item) {
                int presents = attendanceService.getPresentsNumber(subject, group, item.getModelObject());
                int absents = attendanceService.getAbsentsNumber(subject, group, item.getModelObject());
                double percent = presents / (presents + absents)*100;
                item.add(new Label("fullname"));
                item.add(new Label("presents", Model.of(presents)));
                item.add(new Label("absents", Model.of(absents)));
                item.add(new Label("result", Model.of(percent + "%")));
            }
        };
        body.add(rows);
        return body;
    }
}

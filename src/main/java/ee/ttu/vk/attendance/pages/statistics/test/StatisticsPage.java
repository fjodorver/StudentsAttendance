package ee.ttu.vk.attendance.pages.statistics.test;

import com.google.common.collect.Lists;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.BootstrapForm;
import ee.ttu.vk.attendance.domain.Programme;
import ee.ttu.vk.attendance.domain.Student;
import ee.ttu.vk.attendance.domain.Subject;
import ee.ttu.vk.attendance.domain.Timetable;
import ee.ttu.vk.attendance.pages.AbstractPage;
import ee.ttu.vk.attendance.pages.providers.StudentDataProvider;
import ee.ttu.vk.attendance.pages.statistics.ChartPanel;
import ee.ttu.vk.attendance.service.GroupService;
import ee.ttu.vk.attendance.service.StudentService;
import ee.ttu.vk.attendance.service.SubjectService;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by fjodor on 2.03.16.
 */
public class StatisticsPage extends AbstractPage {


    @SpringBean
    private SubjectService subjectService;

    @SpringBean
    private StudentService studentService;

    @SpringBean
    private GroupService groupService;

    private Subject subject;
    private Programme programme;

    private StudentDataProvider studentDataProvider;
    private BootstrapForm<Void> searchForm;
    private ChartPanel chartPanel;

    private ListModel<Student> studentListModel;

    public StatisticsPage() {
        studentListModel = new ListModel<>(Lists.newArrayList());
        studentDataProvider = new StudentDataProvider();
        add(searchForm = getSearchForm());
        add(chartPanel = new ChartPanel("chartPanel", studentListModel, new LoadableDetachableModel<Subject>() {
            @Override
            protected Subject load() {
                return subject;
            }
        }));
    }

    private BootstrapForm<Void> getSearchForm() {
        List<Subject> subjects = null;
        List<Programme> programmes = Lists.newArrayList();
        subjects.forEach(x -> programmes.addAll(x.getTimetables().stream().map(Timetable::getProgramme).collect(Collectors.toList())));
        BootstrapForm<Void> form = new BootstrapForm<>("searchForm");
        form.add(new DropDownChoice<>("subject", new PropertyModel<>(this, "subject"), subjects).add(getOnUpdate()));
        form.add(new DropDownChoice<>("programme", new PropertyModel<>(this, "programme"), programmes).add(getOnUpdate()));
        return form;
    }

    private AjaxFormComponentUpdatingBehavior getOnUpdate() {
        return new AjaxFormComponentUpdatingBehavior("change") {
            @Override
            protected void onUpdate(AjaxRequestTarget ajaxRequestTarget) {
//                if(subject != null && programme != null && groupService.findAllBySubject(Lists.newArrayList(subject)).get(0).equals(programme))
//                    studentListModel.setObject(Lists.newArrayList(studentService.findAllBySubject(subject)));
//                else
//                    studentListModel.getObject().clear();
                chartPanel.modelChanged();
                ajaxRequestTarget.add(chartPanel);
            }
        };
    }
}

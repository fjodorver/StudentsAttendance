package ee.ttu.vk.sa.pages.students;

import com.google.common.collect.Lists;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapAjaxLink;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Modal;
import de.agilecoders.wicket.core.markup.html.bootstrap.navigation.ajax.BootstrapAjaxPagingNavigator;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeIconType;
import ee.ttu.vk.sa.domain.Student;
import ee.ttu.vk.sa.pages.components.BootstrapIndicatingAjaxLink;
import ee.ttu.vk.sa.pages.panels.NoRecordsPanel;
import ee.ttu.vk.sa.service.StudentService;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * Created by fjodor on 19.02.16.
 */
public class StudentsUploadPanel extends Modal<List<Student>> {
    private static final long ITEMS_PER_PAGE = 10;

    @SpringBean
    private StudentService studentService;

    private WebMarkupContainer container;
    private DataView<Student> students;

    private List<Student> studentList = Lists.newArrayList();

    public StudentsUploadPanel(String id) {
        super(id);
        ListDataProvider<Student>  dataProvider = getDataProvider(studentList);
        container = new WebMarkupContainer("studentTable");
        container.setOutputMarkupId(true);
        students = getStudents(dataProvider);
        students.setItemsPerPage(ITEMS_PER_PAGE);
        container.add(students, new NoRecordsPanel<>("noRecordsPanel", students));
        add(new BootstrapAjaxPagingNavigator("navigator", students), container);
        addButton(getSaveButton());
    }

    private BootstrapAjaxLink<Void> getSaveButton() {
        return new BootstrapIndicatingAjaxLink<Void>("button", Buttons.Type.Primary) {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                studentService.saveStudents(studentList);
                appendCloseDialogJavaScript(ajaxRequestTarget);
            }
        }.setLabel(Model.of(new ResourceModel("students.upload.buttons.save"))).setIconType(FontAwesomeIconType.save);
    }

    private DataView<Student> getStudents(ListDataProvider<Student> dataProvider) {
        return new DataView<Student>("students", dataProvider) {
            @Override
            protected void populateItem(Item<Student> item) {
                item.add(new Label("code"));
                item.add(new Label("fullname"));
                item.add(new Label("group"));
                item.add(new AjaxLink<Student>("remove") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        studentList.remove((int)(students.getCurrentPage() * ITEMS_PER_PAGE + item.getIndex()));
                        target.add(container);
                    }
                });
            }
        };
    }


    private ListDataProvider<Student> getDataProvider(final List<Student> students) {
        return new ListDataProvider<Student>(students){
            @Override
            public IModel<Student> model(Student student) {
                return new CompoundPropertyModel<>(student);
            }
        };
    }

    @Override
    protected void onModelChanged() {
        studentList.clear();
        studentList.addAll(getModelObject());
    }

}

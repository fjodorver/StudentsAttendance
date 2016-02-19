package ee.ttu.vk.sa.pages.students;

import de.agilecoders.wicket.core.markup.html.bootstrap.form.BootstrapForm;
import de.agilecoders.wicket.core.markup.html.bootstrap.navigation.ajax.BootstrapAjaxPagingNavigator;
import ee.ttu.vk.sa.CustomAuthenticatedWebSession;
import ee.ttu.vk.sa.domain.Student;
import ee.ttu.vk.sa.domain.Teacher;
import ee.ttu.vk.sa.pages.AbstractPage;
import ee.ttu.vk.sa.pages.panels.FileUploadPanel;
import ee.ttu.vk.sa.pages.panels.SearchPanel;
import ee.ttu.vk.sa.pages.providers.StudentDataProvider;
import ee.ttu.vk.sa.service.GroupService;
import ee.ttu.vk.sa.service.StudentService;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.io.InputStream;

/**
 * Created by fjodor on 6.02.16.
 */

@AuthorizeInstantiation(Roles.ADMIN)
public class StudentsPage extends AbstractPage {

    @SpringBean
    private StudentService studentService;

    @SpringBean
    private GroupService groupService;

    private StudentsUploadPanel uploadPanel;

    private DataView<Student> students;
    private StudentDataProvider studentDataProvider;
    private WebMarkupContainer studentTable;
    private StudentPanel studentPanel;

	private FileUploadPanel<Student> panel;

    public StudentsPage() {
        studentDataProvider = new StudentDataProvider();
        studentTable = new WebMarkupContainer("studentTable");
        uploadPanel = new StudentsUploadPanel("uploadPanel");
        studentTable.setOutputMarkupId(true);
        students = getStudents();
        students.setItemsPerPage(10);
        studentTable.add(students);
        studentPanel = new StudentPanel("studentPanel", new CompoundPropertyModel<>(new Student()));
        uploadPanel.setOutputMarkupId(true);
        panel = new FileUploadPanel<Student>("docPanel", ".doc") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, InputStream inputStream) {
                uploadPanel.setModel(new ListModel<>(studentService.parseStudents(inputStream)));
                target.add(uploadPanel);
                uploadPanel.appendShowDialogJavaScript(target);
            }
        };
        add(studentTable, panel, new BootstrapAjaxPagingNavigator("navigator", students), studentPanel, getButtonAddStudent(), uploadPanel);
        add(new SearchPanel<Student>("searchPanel", new CompoundPropertyModel<>(new Student()), "lastname") {
            @Override
            protected void onUpdate(AjaxRequestTarget target, IModel<Student> model) {
                studentDataProvider.setFilterState(model.getObject());
                target.add(studentTable);
            }
        });
    }

    private DataView<Student> getStudents(){
        return new DataView<Student>("students", studentDataProvider) {
            @Override
            protected void populateItem(Item<Student> item) {
                item.add(new AjaxLink<Student>("edit") {
                    @Override
                    public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                        studentPanel.setModel(item.getModel());
                        ajaxRequestTarget.add(studentPanel);
                        studentPanel.appendShowDialogJavaScript(ajaxRequestTarget);
                    }
                }.add(new Label("code")));
                item.add(new Label("fullname"));
                item.add(new Label("group"));
                item.add(new AjaxLink<Teacher>("delete") {
                    @Override
                    public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                        studentService.deleteStudent(item.getModelObject());
                        ajaxRequestTarget.add(studentTable);
                    }
                    @Override
                    public boolean isVisible() {
                        return CustomAuthenticatedWebSession.getSession().isSignedIn();
                    }
                });
            }
        };
    }

    private AjaxLink<Student> getButtonAddStudent(){
        return new AjaxLink<Student>("addStudent") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                studentPanel.setModel(new CompoundPropertyModel<>(new Student()));
                ajaxRequestTarget.add(studentPanel);
                studentPanel.appendShowDialogJavaScript(ajaxRequestTarget);
            }
            @Override
            public boolean isEnabled() {
                return CustomAuthenticatedWebSession.getSession().isSignedIn();
            }
        };
    }
}

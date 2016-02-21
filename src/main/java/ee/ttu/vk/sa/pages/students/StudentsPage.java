package ee.ttu.vk.sa.pages.students;

import de.agilecoders.wicket.core.markup.html.bootstrap.navigation.ajax.BootstrapAjaxPagingNavigator;
import ee.ttu.vk.sa.CustomAuthenticatedWebSession;
import ee.ttu.vk.sa.domain.Student;
import ee.ttu.vk.sa.domain.Teacher;
import ee.ttu.vk.sa.pages.AbstractPage;
import ee.ttu.vk.sa.pages.panels.FileUploadPanel;
import ee.ttu.vk.sa.pages.panels.NoRecordsPanel;
import ee.ttu.vk.sa.pages.panels.SearchPanel;
import ee.ttu.vk.sa.pages.providers.StudentDataProvider;
import ee.ttu.vk.sa.service.GroupService;
import ee.ttu.vk.sa.service.StudentService;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxNavigationToolbar;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.io.InputStream;

/**
 * Created by fjodor on 6.02.16.
 */

@AuthorizeInstantiation(Roles.ADMIN)
public class StudentsPage extends AbstractPage {
    private final static long ITEMS_PER_PAGE = 10;

    @SpringBean
    private StudentService studentService;

    private StudentsUploadPanel uploadPanel;

    private StudentDataProvider studentDataProvider;
    private WebMarkupContainer studentTable;
    private StudentPanel studentPanel;

    public StudentsPage() {
        studentDataProvider = new StudentDataProvider();
        studentTable = new WebMarkupContainer("studentTable");
        studentTable.setOutputMarkupId(true);
        DataView<Student> students = getStudents();
        students.setItemsPerPage(ITEMS_PER_PAGE);
        studentPanel = new StudentPanel("studentPanel", new CompoundPropertyModel<>(new Student()));
        uploadPanel = new StudentsUploadPanel("uploadPanel");
        studentTable.add(students, new NoRecordsPanel<>("noRecordsPanel", students));
        add(getFileUploadPanel(), getSearchPanel(), getButtonAddStudent(), studentPanel, uploadPanel, studentTable);
        add(new BootstrapAjaxPagingNavigator("navigator", students));
    }

    private SearchPanel<Student> getSearchPanel() {
        return new SearchPanel<Student>("searchPanel", new CompoundPropertyModel<>(new Student()), "lastname") {
            @Override
            protected void onUpdate(AjaxRequestTarget target, IModel<Student> model) {
                studentDataProvider.setFilterState(model.getObject());
                target.add(studentTable);
            }
        };
    }

    private FileUploadPanel<Student> getFileUploadPanel() {
        return new FileUploadPanel<Student>("docPanel", new ResourceModel("students.upload.header"), ".doc") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, InputStream inputStream) {
                uploadPanel.setModel(new ListModel<>(studentService.parse(inputStream)));
                uploadPanel.header(new ResourceModel("students.upload.header"));
                target.add(uploadPanel);
                uploadPanel.appendShowDialogJavaScript(target);
            }
        };
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
                        studentService.delete(item.getModelObject().getId());
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

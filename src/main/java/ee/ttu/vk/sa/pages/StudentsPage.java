package ee.ttu.vk.sa.pages;

import ee.ttu.vk.sa.domain.Attendance;
import ee.ttu.vk.sa.domain.Student;
import ee.ttu.vk.sa.pages.panels.FileUploadPanel;
import ee.ttu.vk.sa.pages.panels.IAction;
import ee.ttu.vk.sa.pages.providers.AttendanceDataProvider;
import ee.ttu.vk.sa.service.StudentService;
import ee.ttu.vk.sa.utils.DocParser;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * Created by fjodor on 6.02.16.
 */

@AuthorizeInstantiation(Roles.ADMIN)
public class StudentsPage extends AbstractPage implements IAction<Student> {

	@SpringBean
	private StudentService studentService;
    @SpringBean
    private StudentService studentService;
    @SpringBean
    private GroupService groupService;
    private DataView<Student> students;
    private StudentDataProvider studentDataProvider;
    private WebMarkupContainer studentTable;
    private StudentsPanel studentsPanel;

	private FileUploadPanel<Student> panel;

	public StudentsPage() {
		panel = new FileUploadPanel<>("panel", ".doc", this);
		add(panel);
	}
    public StudentsPage() {
        studentDataProvider = new StudentDataProvider();
        studentTable = new WebMarkupContainer("studentTable");
        studentTable.setOutputMarkupId(true);
        students = getStudents();
        students.setItemsPerPage(10);
        studentTable.add(students);
        studentsPanel = new StudentsPanel("studentPanel", new CompoundPropertyModel<>(new Student()));
        panel = new FileUploadPanel<>("docPanel", new DocParser(), this);
        add(studentTable, panel, new BootstrapAjaxPagingNavigator("navigator", students), studentsPanel, getButtonAddStudent(), getSearchForm());
    }

    private DataView<Student> getStudents(){
        return new DataView<Student>("students", studentDataProvider) {
            @Override
            protected void populateItem(Item<Student> item) {
                item.add(new AjaxLink<Student>("edit") {
                    @Override
                    public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                        studentsPanel.setModel(item.getModel());
                        ajaxRequestTarget.add(studentsPanel);
                        studentsPanel.appendShowDialogJavaScript(ajaxRequestTarget);
                    }
                }.add(new Label("code")));
                item.add(new Label("firstname"));
                item.add(new Label("lastname"));
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

    private BootstrapForm<Student> getSearchForm(){
        BootstrapForm<Student> form = new BootstrapForm<>("searchForm", new CompoundPropertyModel<>(new Student()));
        form.add(new TextField<String>("lastname").add(new AjaxFormComponentUpdatingBehavior("input") {
            @Override
            protected void onUpdate(AjaxRequestTarget ajaxRequestTarget) {
                studentDataProvider.setFilterState(form.getModelObject());
                ajaxRequestTarget.add(studentTable);
            }
        }));
        return form;
    }

    private AjaxLink<Student> getButtonAddStudent(){
        return new AjaxLink<Student>("addStudent") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                studentsPanel.setModel(new CompoundPropertyModel<>(new Student()));
                ajaxRequestTarget.add(studentsPanel);
                studentsPanel.appendShowDialogJavaScript(ajaxRequestTarget);
            }
            @Override
            public boolean isEnabled() {
                return CustomAuthenticatedWebSession.getSession().isSignedIn();
            }
        };
    }

	@Override
	public void save(InputStream inputStream) {
		List<Student> students = studentService.parseStudents(inputStream);
		studentService.saveStudents(students);
	}
}

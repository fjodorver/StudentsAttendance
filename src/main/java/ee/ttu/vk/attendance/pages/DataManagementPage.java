package ee.ttu.vk.attendance.pages;

import com.google.common.collect.Lists;
import com.google.common.net.MediaType;
import de.agilecoders.wicket.core.markup.html.bootstrap.tabs.AjaxBootstrapTabbedPanel;
import ee.ttu.vk.attendance.domain.Student;
import ee.ttu.vk.attendance.domain.Subject;
import ee.ttu.vk.attendance.domain.Teacher;
import ee.ttu.vk.attendance.pages.panels.AbstractTablePanel;
import ee.ttu.vk.attendance.pages.panels.DMPanel;
import ee.ttu.vk.attendance.pages.panels.FileUploadPanel;
import ee.ttu.vk.attendance.pages.providers.StudentDataProvider;
import ee.ttu.vk.attendance.pages.providers.SubjectDataProvider;
import ee.ttu.vk.attendance.pages.providers.TeacherDataProvider;
import ee.ttu.vk.attendance.service.StudentService;
import ee.ttu.vk.attendance.service.SubjectService;
import ee.ttu.vk.attendance.service.TeacherService;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.io.IOException;
import java.util.List;

/**
 * Created by fjodor on 27.02.16.
 */
public class DataManagementPage extends AbstractPage {


    @SpringBean
    private TeacherService teacherService;

    @SpringBean
    private StudentService studentService;

    @SpringBean
    private SubjectService subjectService;

    private SubjectDataProvider subjectDataProvider = new SubjectDataProvider();
    private TeacherDataProvider teacherDataProvider = new TeacherDataProvider();
    private StudentDataProvider studentDataProvider = new StudentDataProvider();

    private AjaxBootstrapTabbedPanel<AbstractTab> tabbedPanel;

    private DMPanel panel;

    public DataManagementPage() {
        List<AbstractTab> tabs = Lists.newArrayList();
        tabs.add(new AbstractTab(new ResourceModel("data-management.tabs.students")) {
            @Override
            public WebMarkupContainer getPanel(String s) {
                return new AbstractTablePanel<Student, Student>(s, studentDataProvider) {
                    @Override
                    protected List<IColumn<Student, String>> getColumnList() {
                        List<IColumn<Student, String>> columns = Lists.newArrayList();
                        columns.add(getFilteredColumn("Code", "code", "filterState.code", "col-lg-2"));
                        columns.add(getFilteredColumn("Firstname", "firstname", "filterState.firstname", "col-lg-4"));
                        columns.add(getFilteredColumn("Lastname", "lastname", "filterState.lastname", "col-lg-4"));
                        columns.add(getFilteredColumn("Group", "group", "filterState.group.name", "col-lg-2"));
                        return columns;
                    }
                };
            }
        });
        tabs.add(new AbstractTab(new ResourceModel("data-management.tabs.subjects")) {
            @Override
            public WebMarkupContainer getPanel(String s) {
                return new AbstractTablePanel<Subject, Subject>(s, subjectDataProvider) {
                    @Override
                    protected List<IColumn<Subject, String>> getColumnList() {
                        List<IColumn<Subject, String>> columns = Lists.newArrayList();
                        columns.add(getFilteredColumn("Code", "code", "filterState.code", "col-lg-2"));
                        columns.add(getFilteredColumn("Name", "name", "filterState.name", "col-lg-10"));
                        return columns;
                    }
                };
            }
        });
        tabs.add(new AbstractTab(new ResourceModel("data-management.tabs.teachers")) {
            @Override
            public WebMarkupContainer getPanel(String s) {
                return new AbstractTablePanel<Teacher, Teacher>(s, teacherDataProvider) {
                    @Override
                    protected List<IColumn<Teacher, String>> getColumnList() {
                        List<IColumn<Teacher, String>> columns = Lists.newArrayList();
                        columns.add(getFilteredColumn("Username", "username", "filterState.username", "col-lg-4"));
                        columns.add(getFilteredColumn("Name", "fullname", "filterState.fullname", "col-lg-10"));
                        return columns;
                    }
                };
            }
        });
        add(new FileUploadPanel("uploadPanel") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, List<FileUpload> fileUploads) throws IOException {
                List<Student> students = Lists.newArrayList();
                for (FileUpload fileUpload : fileUploads) {
                    MediaType mediaType = MediaType.parse(fileUpload.getContentType());
                    if(mediaType.equals(MediaType.MICROSOFT_WORD))
                        students = studentService.parse(fileUpload.getInputStream());
                }
                panel.setStudentsModel(new ListModel<>(students));
                panel.appendShowDialogJavaScript(target);
                target.add(panel);
            }
        });
        add(tabbedPanel = new AjaxBootstrapTabbedPanel<>("tabs", tabs));
        add(panel = new DMPanel("panel"));
    }
}

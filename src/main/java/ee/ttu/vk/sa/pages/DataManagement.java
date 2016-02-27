package ee.ttu.vk.sa.pages;

import com.google.common.collect.Lists;
import com.google.common.net.MediaType;
import de.agilecoders.wicket.core.markup.html.bootstrap.tabs.AjaxBootstrapTabbedPanel;
import ee.ttu.vk.sa.domain.Student;
import ee.ttu.vk.sa.domain.Subject;
import ee.ttu.vk.sa.domain.Teacher;
import ee.ttu.vk.sa.pages.panels.AbstractTablePanel;
import ee.ttu.vk.sa.pages.panels.FileUploadPanel;
import ee.ttu.vk.sa.pages.providers.StudentDataProvider;
import ee.ttu.vk.sa.pages.providers.SubjectDataProvider;
import ee.ttu.vk.sa.pages.providers.TeacherDataProvider;
import ee.ttu.vk.sa.service.StudentService;
import ee.ttu.vk.sa.service.TeacherService;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.io.IOException;
import java.util.List;

/**
 * Created by fjodor on 27.02.16.
 */
public class DataManagement extends AbstractPage {

    @SpringBean
    private TeacherService teacherService;

    @SpringBean
    private StudentService studentService;

    private SubjectDataProvider subjectDataProvider = new SubjectDataProvider();
    private TeacherDataProvider teacherDataProvider = new TeacherDataProvider();
    private StudentDataProvider studentDataProvider = new StudentDataProvider();

    private AjaxBootstrapTabbedPanel<AbstractTab> tabbedPanel;

    public DataManagement() {
        List<AbstractTab> tabs = Lists.newArrayList();
        tabs.add(new AbstractTab(new ResourceModel("data-management.tabs.students")) {
            @Override
            public WebMarkupContainer getPanel(String s) {
                return new AbstractTablePanel<Student, String>(s, studentDataProvider) {
                    @Override
                    protected List<IColumn<Student, String>> getColumnList() {
                        List<IColumn<Student, String>> columns = Lists.newArrayList();
                        columns.add(getFilteredColumn("Code", "code", "filterState.code"));
                        columns.add(getFilteredColumn("Firstname", "firstname", "filterState.firstname"));
                        columns.add(getFilteredColumn("Lastname", "lastname", "filterState.lastname"));
                        columns.add(getFilteredColumn("Group", "group", "filterState.group.name"));
                        return columns;
                    }
                };
            }
        });
        tabs.add(new AbstractTab(new ResourceModel("data-management.tabs.subjects")) {
            @Override
            public WebMarkupContainer getPanel(String s) {
                return new AbstractTablePanel<Subject, String>(s, subjectDataProvider) {
                    @Override
                    protected List<IColumn<Subject, String>> getColumnList() {
                        List<IColumn<Subject, String>> columns = Lists.newArrayList();
                        columns.add(getFilteredColumn("Code", "code", "filterState.code"));
                        columns.add(getFilteredColumn("Name", "name", "filterState.name"));
                        return columns;
                    }
                };
            }
        });
        tabs.add(new AbstractTab(new ResourceModel("data-management.tabs.teachers")) {
            @Override
            public WebMarkupContainer getPanel(String s) {
                return new AbstractTablePanel<Teacher, String>(s, teacherDataProvider) {
                    @Override
                    protected List<IColumn<Teacher, String>> getColumnList() {
                        List<IColumn<Teacher, String>> columns = Lists.newArrayList();
                        columns.add(getFilteredColumn("Email", "email", "filterState.email"));
                        columns.add(getFilteredColumn("Name", "name", "filterState.name"));
                        return columns;
                    }
                };
            }
        });
        add(new FileUploadPanel("uploadPanel") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, List<FileUpload> fileUploads) throws IOException {
                for (FileUpload fileUpload : fileUploads) {
                    MediaType mediaType = MediaType.parse(fileUpload.getContentType());
                    if(mediaType.equals(MediaType.MICROSOFT_WORD))
                        studentService.save(studentService.parse(fileUpload.getInputStream()));
                    if(mediaType.equals(MediaType.MICROSOFT_EXCEL))
                        teacherService.addTeachers(teacherService.parseTeachers(fileUpload.getInputStream()));

                }
            }
        });
        add(tabbedPanel = new AjaxBootstrapTabbedPanel<>("tabs", tabs));
    }
}
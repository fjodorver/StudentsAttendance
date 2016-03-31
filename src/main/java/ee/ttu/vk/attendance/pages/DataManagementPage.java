package ee.ttu.vk.attendance.pages;

import com.google.common.collect.Lists;
import de.agilecoders.wicket.core.markup.html.bootstrap.tabs.AjaxBootstrapTabbedPanel;
import ee.ttu.vk.attendance.domain.Student;
import ee.ttu.vk.attendance.domain.Subject;
import ee.ttu.vk.attendance.domain.Teacher;
import ee.ttu.vk.attendance.pages.panels.FileUploadPanel;
import ee.ttu.vk.attendance.pages.panels.StudentsPanel;
import ee.ttu.vk.attendance.pages.panels.TablePanel;
import ee.ttu.vk.attendance.pages.providers.StudentDataProvider;
import ee.ttu.vk.attendance.pages.providers.SubjectDataProvider;
import ee.ttu.vk.attendance.pages.providers.TeacherDataProvider;
import ee.ttu.vk.attendance.service.StudentService;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * Created by fjodor on 27.02.16.
 */
public class DataManagementPage extends AbstractPage {
    @SpringBean
    private StudentService studentService;

    private StudentsPanel panel;
    private AjaxBootstrapTabbedPanel<AbstractTab> tabbedPanel;

    private ListModel<Student> studentModel = new ListModel<>(Lists.newArrayList());

    List<AbstractTab> tabs = Lists.newArrayList();

    public DataManagementPage() {
        tabs.add(new AbstractTab(new ResourceModel("data-management.tabs.students")) {
            @Override
            public WebMarkupContainer getPanel(String s) {
                TablePanel<Student, Student> subjectsPanel = new TablePanel<>(s, new StudentDataProvider());
                subjectsPanel.addFilteredColumn("Code", "code", "filterState.code", "col-lg-2");
                subjectsPanel.addFilteredColumn("Firstname", "firstname", "filterState.firstname", "col-lg-4");
                subjectsPanel.addFilteredColumn("Lastname", "lastname", "filterState.lastname", "col-lg-4");
                subjectsPanel.addFilteredColumn("Group", "group", "filterState.group.name", "col-lg-2");
                return subjectsPanel;
            }
        });
        tabs.add(new AbstractTab(new ResourceModel("data-management.tabs.subjects")) {
            @Override
            public WebMarkupContainer getPanel(String s) {
                TablePanel<Subject, Subject> subjectTablePanel = new TablePanel<>(s, new SubjectDataProvider());
                subjectTablePanel.addFilteredColumn("Code", "code", "filterState.code", "col-lg-2");
                subjectTablePanel.addFilteredColumn("Name", "name", "filterState.name", "col-lg-10");
                return subjectTablePanel;
            }
        });
        tabs.add(new AbstractTab(new ResourceModel("data-management.tabs.teachers")) {
            @Override
            public WebMarkupContainer getPanel(String s) {
                TablePanel<Teacher, Teacher> teachersPanel = new TablePanel<>(s, new TeacherDataProvider());
                teachersPanel.addFilteredColumn("Username", "username", "filterState.username", "col-lg-4");
                teachersPanel.addFilteredColumn("Name", "fullname", "filterState.fullname", "col-lg-10");
                return teachersPanel;
            }
        });
        add(new FileUploadPanel("uploadPanel", (target, model) -> {
            studentModel.getObject().clear();
            studentModel.getObject().addAll(studentService.parse(model.getObject().getInputStream()));
            panel.appendShowDialogJavaScript(target);
            target.add(panel);
        }));
        add(tabbedPanel = new AjaxBootstrapTabbedPanel<>("tabs", tabs), panel = new StudentsPanel("panel", studentModel));
    }
}

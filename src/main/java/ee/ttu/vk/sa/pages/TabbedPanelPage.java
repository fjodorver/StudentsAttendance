package ee.ttu.vk.sa.pages;

import com.google.common.collect.Lists;
import de.agilecoders.wicket.core.markup.html.bootstrap.tabs.AjaxBootstrapTabbedPanel;
import ee.ttu.vk.sa.domain.Subject;
import ee.ttu.vk.sa.domain.Teacher;
import ee.ttu.vk.sa.pages.panels.AbstractTablePanel;
import ee.ttu.vk.sa.pages.panels.FileUploadPanel;
import ee.ttu.vk.sa.pages.providers.SubjectDataProvider;
import ee.ttu.vk.sa.pages.providers.TeacherDataProvider;
import ee.ttu.vk.sa.service.StudentService;
import ee.ttu.vk.sa.service.TeacherService;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.io.InputStream;
import java.util.List;

/**
 * Created by fjodor on 27.02.16.
 */
public class TabbedPanelPage extends AbstractPage {

    @SpringBean
    private TeacherService teacherService;

    @SpringBean
    private StudentService studentService;

    private SubjectDataProvider subjectDataProvider = new SubjectDataProvider();
    private TeacherDataProvider teacherDataProvider = new TeacherDataProvider();

    private AjaxBootstrapTabbedPanel<AbstractTab> tabbedPanel;

    public TabbedPanelPage() {
        List<AbstractTab> tabs = Lists.newArrayList();
        tabs.add(new AbstractTab(new ResourceModel("teachers-subjects.tabs.subjects")) {
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
        tabs.add(new AbstractTab(new ResourceModel("teachers-subjects.tabs.teachers")) {
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
        add(new FileUploadPanel("uploadPanel", Model.of("UploadPanel"), ".xls") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, InputStream inputStream) {
                teacherService.addTeachers(teacherService.parseTeachers(inputStream));
                studentService.save(studentService.parse(inputStream));
                target.add(tabbedPanel);
            }
        });
        add(tabbedPanel = new AjaxBootstrapTabbedPanel<>("tabs", tabs));
    }
}

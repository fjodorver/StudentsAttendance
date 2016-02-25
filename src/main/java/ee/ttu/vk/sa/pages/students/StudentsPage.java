package ee.ttu.vk.sa.pages.students;

import com.google.common.collect.Lists;
import ee.ttu.vk.sa.CustomAuthenticatedWebSession;
import ee.ttu.vk.sa.domain.Student;
import ee.ttu.vk.sa.domain.Teacher;
import ee.ttu.vk.sa.pages.AbstractPage;
import ee.ttu.vk.sa.pages.components.BootstrapAjaxNavigationToolbar;
import ee.ttu.vk.sa.pages.panels.AbstractTablePanel;
import ee.ttu.vk.sa.pages.panels.FileUploadPanel;
import ee.ttu.vk.sa.pages.panels.SearchPanel;
import ee.ttu.vk.sa.pages.providers.StudentDataProvider;
import ee.ttu.vk.sa.service.StudentService;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackHeadersToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilteredPropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.TextFilter;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.*;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.io.InputStream;
import java.util.List;

/**
 * Created by fjodor on 6.02.16.
 */

@AuthorizeInstantiation(Roles.ADMIN)
public class StudentsPage extends AbstractPage {

    @SpringBean
    private StudentService studentService;

    private StudentsUploadPanel uploadPanel;
    private StudentDataProvider dataProvider;

    public StudentsPage() {
        dataProvider = new StudentDataProvider();
        AbstractTablePanel<Student, String> tablePanel = new AbstractTablePanel<Student, String>("studentsPanel", dataProvider) {
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
        add(tablePanel, getFileUploadPanel(), uploadPanel = new StudentsUploadPanel("uploadPanel"));
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
}
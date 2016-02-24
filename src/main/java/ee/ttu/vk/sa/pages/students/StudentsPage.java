package ee.ttu.vk.sa.pages.students;

import com.google.common.collect.Lists;
import ee.ttu.vk.sa.CustomAuthenticatedWebSession;
import ee.ttu.vk.sa.domain.Student;
import ee.ttu.vk.sa.domain.Teacher;
import ee.ttu.vk.sa.pages.AbstractPage;
import ee.ttu.vk.sa.pages.components.BootstrapAjaxNavigationToolbar;
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
    private StudentDataProvider studentDataProvider;
    private DataTable<Student, String> dataTable;

    public StudentsPage() {
        studentDataProvider = new StudentDataProvider();
        List<IColumn<Student, String>> columns = Lists.newArrayList();
        columns.add(getFilteredColumn("Code", "code", "filterState.code"));
        columns.add(getFilteredColumn("Firstname", "firstname", "filterState.firstname"));
        columns.add(getFilteredColumn("Lastname", "lastname", "filterState.lastname"));
        columns.add(getFilteredColumn("Group", "group", "filterState.group.name"));
        dataTable = new DataTable<>("table", columns, studentDataProvider, 10);
        dataTable.setOutputMarkupId(true);
        dataTable.setVersioned(false);
        FilterForm<Student> filterForm = new FilterForm<>("form", studentDataProvider);
        FilterToolbar filterToolbar = new FilterToolbar(dataTable, filterForm);
        dataTable.addTopToolbar(new AjaxFallbackHeadersToolbar<>(dataTable, studentDataProvider));
        dataTable.addTopToolbar(filterToolbar);
        dataTable.addBottomToolbar(new BootstrapAjaxNavigationToolbar(dataTable));
        dataTable.addBottomToolbar(new NoRecordsToolbar(dataTable));
        filterForm.add(dataTable);
        add(filterForm, getFileUploadPanel(), uploadPanel = new StudentsUploadPanel("uploadPanel"));
    }

    private IColumn<Student, String> getFilteredColumn(String name, String stringExpression, String stringFilter) {
        return new FilteredPropertyColumn<Student, String>(Model.of(name), stringExpression) {
            @Override
            public Component getFilter(String s, FilterForm<?> filterForm) {
                TextFilter<Student> textFilter = new TextFilter<>(s, new PropertyModel<>(studentDataProvider, stringFilter), filterForm);
                textFilter.getFilter().add(new AttributeAppender("class", " form-control"));
                return textFilter;
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
}
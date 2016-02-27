package ee.ttu.vk.sa.pages.students;

import com.google.common.collect.Lists;
import ee.ttu.vk.sa.domain.Student;
import ee.ttu.vk.sa.pages.AbstractPage;
import ee.ttu.vk.sa.pages.panels.AbstractTablePanel;
import ee.ttu.vk.sa.pages.panels.FileUploadPanel;
import ee.ttu.vk.sa.pages.providers.StudentDataProvider;
import ee.ttu.vk.sa.service.StudentService;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
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

    private FileUploadPanel getFileUploadPanel() {
        return new FileUploadPanel("docPanel", new ResourceModel("students.upload.header"), ".doc") {
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
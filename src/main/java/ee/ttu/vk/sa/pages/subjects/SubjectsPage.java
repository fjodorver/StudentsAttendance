package ee.ttu.vk.sa.pages.subjects;


import com.google.common.collect.Lists;
import ee.ttu.vk.sa.domain.Student;
import ee.ttu.vk.sa.domain.Subject;
import ee.ttu.vk.sa.pages.AbstractPage;
import ee.ttu.vk.sa.pages.panels.AbstractTablePanel;
import ee.ttu.vk.sa.pages.panels.FileUploadPanel;
import ee.ttu.vk.sa.pages.providers.SubjectDataProvider;
import ee.ttu.vk.sa.service.SubjectService;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilteredPropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.TextFilter;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.io.InputStream;
import java.util.List;

@AuthorizeInstantiation(Roles.ADMIN)
public class SubjectsPage extends AbstractPage {

    @SpringBean
    private SubjectService subjectService;

    private FileUploadPanel<Subject> panel;
    private SubjectDataProvider dataProvider;

    public SubjectsPage(){
        dataProvider = new SubjectDataProvider();
        AbstractTablePanel<Subject, String> tablePanel = new AbstractTablePanel<Subject, String>("subjectsPanel", dataProvider) {
            @Override
            protected List<IColumn<Subject, String>> getColumnList() {
                List<IColumn<Subject, String>> columns = Lists.newArrayList();
                columns.add(getFilteredColumn("Code", "code", "filterState.code"));
                columns.add(getFilteredColumn("Name", "name", "filterState.name"));
                return columns;
            }
        };
        panel = new FileUploadPanel<Subject>("xlsPanel", Model.of("Excel file"), ".xls") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, InputStream inputStream) {
                List<Subject> subjects = subjectService.parseSubjects(inputStream);
                subjectService.saveSubjects(subjects);
            }
        };
        add(panel, tablePanel);
    }

}

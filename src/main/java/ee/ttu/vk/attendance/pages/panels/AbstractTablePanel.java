package ee.ttu.vk.attendance.pages.panels;

import ee.ttu.vk.attendance.pages.components.BootstrapAjaxNavigationToolbar;
import ee.ttu.vk.attendance.pages.providers.AbstractDataProvider;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackHeadersToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilteredPropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.TextFilter;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import java.util.List;

/**
 * Created by fjodor on 25.02.16.
 */
public abstract class AbstractTablePanel<T, F> extends Panel {

    private AbstractDataProvider<T, F> dataProvider;

    public AbstractTablePanel(String id, AbstractDataProvider<T, F> dataProvider) {
        super(id);
        this.dataProvider = dataProvider;
        FilterForm<F> filterForm = new FilterForm<>("form", dataProvider);
        DataTable<T, String> dataTable = new DataTable<>("table", getColumnList(), dataProvider, 10);
        FilterToolbar filterToolbar = new FilterToolbar(dataTable, filterForm);
        dataTable.setOutputMarkupId(true);
        dataTable.setVersioned(false);
        dataTable.addTopToolbar(new AjaxFallbackHeadersToolbar<>(dataTable, dataProvider));
        dataTable.addTopToolbar(filterToolbar);
        dataTable.addBottomToolbar(new BootstrapAjaxNavigationToolbar(dataTable));
        dataTable.addBottomToolbar(new NoRecordsToolbar(dataTable));
        filterForm.add(dataTable);
        add(filterForm);
    }

    protected abstract List<IColumn<T, String>> getColumnList();

    protected IColumn<T, String> getFilteredColumn(String name, String stringExpression, String stringFilter, String cssClass) {
        return new FilteredPropertyColumn<T, String>(Model.of(name), stringExpression) {
            @Override
            public Component getFilter(String s, FilterForm<?> filterForm) {
                TextFilter<T> textFilter = new TextFilter<>(s, new PropertyModel<>(dataProvider, stringFilter), filterForm);
                textFilter.getFilter().add(new AttributeAppender("class", " form-control"));
                return textFilter;
            }

            @Override
            public String getCssClass() {
                return cssClass;
            }
        };
    }
}

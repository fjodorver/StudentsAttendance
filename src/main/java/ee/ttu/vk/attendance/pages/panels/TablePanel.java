package ee.ttu.vk.attendance.pages.panels;

import com.google.common.collect.Lists;
import ee.ttu.vk.attendance.pages.components.BootstrapAjaxNavigationToolbar;
import ee.ttu.vk.attendance.pages.components.CustomLink;
import ee.ttu.vk.attendance.pages.listeners.AjaxAction;
import ee.ttu.vk.attendance.pages.listeners.AjaxOnClick;
import ee.ttu.vk.attendance.pages.providers.AbstractDataProvider;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackHeadersToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilteredPropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.TextFilter;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import java.util.List;

/**
 * Created by fjodor on 25.02.16.
 */
public class TablePanel<T, F> extends Panel {

    private AbstractDataProvider<T, F> dataProvider;
    private List<IColumn<T, String>> columnList = Lists.newArrayList();

    FilterToolbar filterToolbar;

    public TablePanel(String id, AbstractDataProvider<T, F> dataProvider) {
        super(id);
        this.dataProvider = dataProvider;
        FilterForm<F> filterForm = new FilterForm<>("form", dataProvider);
        DataTable<T, String> dataTable = new DataTable<>("table", columnList, dataProvider, 10);
        filterToolbar = new FilterToolbar(dataTable, filterForm);
        dataTable.setOutputMarkupId(true);
        dataTable.setVersioned(false);
        dataTable.addTopToolbar(new AjaxFallbackHeadersToolbar<>(dataTable, dataProvider));
        dataTable.addTopToolbar(filterToolbar);
        dataTable.addBottomToolbar(new BootstrapAjaxNavigationToolbar(dataTable));
        dataTable.addBottomToolbar(new NoRecordsToolbar(dataTable));
        filterForm.add(dataTable);
        add(filterForm);
    }

    public void setVisibleFilterPanel(Boolean isVisible){
        filterToolbar.setVisible(false);
    };

    public void addLink(String name, String propertyExpression, AjaxAction<T> ajaxAction){
        columnList.add(new PropertyColumn<T, String>(Model.of(name), propertyExpression){
            @Override
            public void populateItem(Item<ICellPopulator<T>> item, String componentId, IModel<T> rowModel) {
                item.add(new CustomLink<>(componentId, new PropertyModel<>(rowModel, propertyExpression), target -> ajaxAction.onSubmit(target, rowModel)));
            }
        });
    }

    public void addColumn(String name, String propertyExpression, String cssClass){
        columnList.add(new PropertyColumn<>(Model.of(name), propertyExpression));
    }

    public void addFilteredColumn(String name, String propertyExpression, String propertyFilter, String cssClass){
        columnList.add(new FilteredPropertyColumn<T, String>(Model.of(name), propertyExpression) {
            @Override
            public Component getFilter(String s, FilterForm<?> filterForm) {
                TextFilter<T> textFilter = new TextFilter<>(s, PropertyModel.of(dataProvider, propertyFilter), filterForm);
                textFilter.getFilter().add(new AttributeAppender("class", " form-control"));
                return textFilter;
            }

            @Override
            public String getCssClass() {
                return cssClass;
            }
        });
    }
}

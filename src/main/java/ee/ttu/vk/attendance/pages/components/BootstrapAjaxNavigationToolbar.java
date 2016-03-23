package ee.ttu.vk.attendance.pages.components;

import de.agilecoders.wicket.core.markup.html.bootstrap.navigation.ajax.BootstrapAjaxPagingNavigator;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxNavigationToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;

public class BootstrapAjaxNavigationToolbar extends AjaxNavigationToolbar {
    public BootstrapAjaxNavigationToolbar(DataTable<?, ?> table) {
        super(table);
    }

    @Override
    protected PagingNavigator newPagingNavigator(String navigatorId, DataTable<?, ?> table) {
        return new BootstrapAjaxPagingNavigator(navigatorId, table){
            private static final long serialVersionUID = 1L;

            protected void onAjaxEvent(AjaxRequestTarget target) {
                target.add(table);
            }
        };
    }
}

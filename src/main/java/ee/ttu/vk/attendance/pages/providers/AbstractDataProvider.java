package ee.ttu.vk.attendance.pages.providers;

import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

/**
 * Created by fjodor on 25.02.16.
 */
public abstract class AbstractDataProvider<T, F> extends SortableDataProvider<T, String> implements IFilterStateLocator<F> {

    protected F filter;

    @Override
    public F getFilterState() {
        return filter;
    }

    @Override
    public void setFilterState(F filter) {
        this.filter = filter;
    }

    @Override
    public IModel<T> model(T t) {
        return new CompoundPropertyModel<T>(t);
    }
}
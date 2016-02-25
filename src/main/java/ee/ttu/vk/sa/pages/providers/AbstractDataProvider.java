package ee.ttu.vk.sa.pages.providers;

import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;

/**
 * Created by fjodor on 25.02.16.
 */
public abstract class AbstractDataProvider<T, S> extends SortableDataProvider<T, S> implements IFilterStateLocator<T> {
    public AbstractDataProvider() {
    }
}

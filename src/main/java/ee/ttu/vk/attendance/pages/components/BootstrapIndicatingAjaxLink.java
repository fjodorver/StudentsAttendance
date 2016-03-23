package ee.ttu.vk.attendance.pages.components;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapAjaxLink;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.extensions.ajax.markup.html.AjaxIndicatorAppender;

/**
 * Created by fjodor on 20.02.16.
 */
public abstract class BootstrapIndicatingAjaxLink<T> extends BootstrapAjaxLink<T> implements IAjaxIndicatorAware {
    private final AjaxIndicatorAppender indicatorAppender;

    public BootstrapIndicatingAjaxLink(String id, Buttons.Type type) {
        super(id, type);
        this.indicatorAppender = new AjaxIndicatorAppender();
        this.add(this.indicatorAppender);
    }

    @Override
    public String getAjaxIndicatorMarkupId() {
        return this.indicatorAppender.getMarkupId();
    }
}

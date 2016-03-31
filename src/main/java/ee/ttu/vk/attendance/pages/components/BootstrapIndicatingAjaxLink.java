package ee.ttu.vk.attendance.pages.components;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapAjaxLink;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import ee.ttu.vk.attendance.pages.listeners.AjaxOnClick;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.extensions.ajax.markup.html.AjaxIndicatorAppender;

public class BootstrapIndicatingAjaxLink<T> extends BootstrapAjaxLink<T> implements IAjaxIndicatorAware {
    private final AjaxIndicatorAppender indicatorAppender;
    private final AjaxOnClick ajaxOnClick;

    public BootstrapIndicatingAjaxLink(String id, Buttons.Type type, AjaxOnClick ajaxOnClick) {
        super(id, type);
        this.ajaxOnClick = ajaxOnClick;
        this.indicatorAppender = new AjaxIndicatorAppender();
        this.add(this.indicatorAppender);
    }

    @Override
    public String getAjaxIndicatorMarkupId() {
        return this.indicatorAppender.getMarkupId();
    }

    @Override
    public void onClick(AjaxRequestTarget ajaxRequestTarget) {
        ajaxOnClick.onClick(ajaxRequestTarget);
    }
}

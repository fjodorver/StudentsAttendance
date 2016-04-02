package ee.ttu.vk.attendance.pages.components;

import ee.ttu.vk.attendance.pages.listeners.AjaxOnClick;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/**
 * Created by Fjodor Vershinin on 4/2/2016.
 */
public class CustomLink<T> extends Panel {
    private AjaxOnClick ajaxOnClick;
    public CustomLink(String id, IModel<T> model, AjaxOnClick ajaxOnClick) {
        super(id, model);
        this.ajaxOnClick = ajaxOnClick;
        add(new AjaxLink<Void>("link") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                CustomLink.this.ajaxOnClick.onClick(ajaxRequestTarget);
            }
        }.add(new Label("label").setDefaultModel(model)));
    }
}

package ee.ttu.vk.sa.pages.panels;

import de.agilecoders.wicket.core.markup.html.bootstrap.form.BootstrapForm;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

/**
 * Created by fjodor on 20.02.16.
 */
public abstract class SearchPanel<T> extends Panel {
    private BootstrapForm<T> form;

    public SearchPanel(String id, IModel<T> model, String propertyExpression) {
        super(id, model);
        form = new BootstrapForm<T>("form", new CompoundPropertyModel<T>(model));
        form.add(new TextField<>("value", new PropertyModel<T>(model.getObject(), propertyExpression)).add(
                new AjaxFormComponentUpdatingBehavior("input") {
                    @Override
                    protected void onUpdate(AjaxRequestTarget ajaxRequestTarget) {
                        SearchPanel.this.onUpdate(ajaxRequestTarget, form.getModel());
                    }
                }
        ));
        add(form);
    }
    protected abstract void onUpdate(AjaxRequestTarget target, IModel<T> model);
}
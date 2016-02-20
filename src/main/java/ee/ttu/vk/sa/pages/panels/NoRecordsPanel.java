package ee.ttu.vk.sa.pages.panels;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

/**
 * Created by fjodor on 20.02.16.
 */
public class NoRecordsPanel extends Panel {
    private static final IModel<String> DEFAULT_MESSAGE_MODEL = new ResourceModel("dataview.no-records-found");

    public NoRecordsPanel(String id, IModel<?> model, int columns) {
        super(id, model);
        WebMarkupContainer td = new WebMarkupContainer("td");
        td.add(AttributeModifier.replace("colspan", new AbstractReadOnlyModel<String>() {
            @Override
            public String getObject() {
                return String.valueOf(columns);
            }
        }));
        td.add(new Label("msg", model));
        add(td);
    }

    public NoRecordsPanel(String id, int columns) {
        this(id, DEFAULT_MESSAGE_MODEL, columns);

    }
}

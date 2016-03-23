package ee.ttu.vk.attendance.pages.panels;

import ee.ttu.vk.attendance.domain.Subject;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.util.ListModel;

/**
 * Created by fjodor on 27.02.16.
 */
public class SubjectsPanel extends Panel {

    private WebMarkupContainer container = new WebMarkupContainer("body");
    private DataView<Subject> dataView;

    public SubjectsPanel(String id, ListModel<Subject> listModel) {
        super(id, listModel);
        dataView = getDataView(listModel);
        container.add(dataView);
        add(container);
    }

    private DataView<Subject> getDataView(ListModel<Subject> listModel) {
        return new DataView<Subject>("rows", getDataProvider(listModel)) {
            @Override
            protected void populateItem(Item<Subject> item) {
                item.add(new Label("code"));
                item.add(new Label("name"));
            }
        };
    }

    private ListDataProvider<Subject> getDataProvider(ListModel<Subject> listModel) {
        return new ListDataProvider<Subject>(listModel.getObject()) {
            @Override
            public IModel<Subject> model(Subject subject) {
                return new CompoundPropertyModel<>(subject);
            }
        };
    }
}
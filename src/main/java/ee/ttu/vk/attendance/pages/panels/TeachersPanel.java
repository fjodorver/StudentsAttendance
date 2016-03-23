package ee.ttu.vk.attendance.pages.panels;

import ee.ttu.vk.attendance.domain.Teacher;
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
public class TeachersPanel extends Panel {

    private WebMarkupContainer container = new WebMarkupContainer("body");
    private DataView<Teacher> dataView;

    public TeachersPanel(String id, ListModel<Teacher> listModel) {
        super(id, listModel);
        dataView = getDataView(listModel);
        container.add(dataView);
        add(container);
    }

    private DataView<Teacher> getDataView(ListModel<Teacher> listModel) {
        return new DataView<Teacher>("rows", getDataProvider(listModel)) {
            @Override
            protected void populateItem(Item<Teacher> item) {
                item.add(new Label("username"));
                item.add(new Label("fullname"));
            }
        };
    }

    private ListDataProvider<Teacher> getDataProvider(ListModel<Teacher> listModel) {
        return new ListDataProvider<Teacher>(listModel.getObject()) {
            @Override
            public IModel<Teacher> model(Teacher teacher) {
                return new CompoundPropertyModel<>(teacher);
            }
        };
    }
}
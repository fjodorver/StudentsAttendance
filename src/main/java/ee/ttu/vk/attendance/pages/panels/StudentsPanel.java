package ee.ttu.vk.attendance.pages.panels;

import ee.ttu.vk.attendance.domain.Student;
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
public class StudentsPanel extends Panel {

    private WebMarkupContainer container = new WebMarkupContainer("body");
    private DataView<Student> dataView;

    public StudentsPanel(String id, ListModel<Student> listModel) {
        super(id, listModel);
        dataView = getDataView(listModel);
        container.add(dataView);
        add(container);
    }

    private DataView<Student> getDataView(ListModel<Student> listModel) {
        return new DataView<Student>("rows", getDataProvider(listModel)) {
            @Override
            protected void populateItem(Item<Student> item) {
                item.add(new Label("code"));
                item.add(new Label("firstname"));
                item.add(new Label("lastname"));
                item.add(new Label("group"));
            }
        };
    }

    private ListDataProvider<Student> getDataProvider(ListModel<Student> listModel) {
        return new ListDataProvider<Student>(listModel.getObject()){
            @Override
            public IModel<Student> model(Student student) {
                return new CompoundPropertyModel<>(student);
            }
        };
    }
}

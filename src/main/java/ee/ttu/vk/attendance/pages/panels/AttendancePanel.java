package ee.ttu.vk.attendance.pages.panels;

import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Modal;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.BootstrapForm;
import de.agilecoders.wicket.core.markup.html.bootstrap.navigation.ajax.BootstrapAjaxPagingNavigator;
import ee.ttu.vk.attendance.domain.Attendance;
import ee.ttu.vk.attendance.domain.Timetable;
import ee.ttu.vk.attendance.pages.filters.TimetableFilter;
import ee.ttu.vk.attendance.pages.providers.AbstractDataProvider;
import ee.ttu.vk.attendance.pages.providers.AttendanceDataProvider;
import ee.ttu.vk.attendance.pages.providers.TimetableDataProvider;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.time.format.DateTimeFormatter;

/**
 * Created by strukov on 3/24/16.
 */
public class AttendancePanel extends Modal<Void> {

    private WebMarkupContainer body;
    private DataView<Attendance> rows;
    private AbstractDataProvider<Attendance, Attendance> dataProvider;


    public AttendancePanel(String markupId) {
        super(markupId);
        dataProvider = new AttendanceDataProvider();
        body = new WebMarkupContainer("body");
        body.setOutputMarkupId(true);
        rows = new DataView<Attendance>("rows", dataProvider) {
            @Override
            protected void populateItem(Item<Attendance> item) {
                item.add(new Label("student"));
                item.add(new Label("status"));
            }
        };
        body.add(rows);
        add(body);
    }

    public void changeFilter(IModel<Attendance> model){
        dataProvider.setFilterState(model.getObject());
    }
}

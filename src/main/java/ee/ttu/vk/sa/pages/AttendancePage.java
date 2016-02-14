package ee.ttu.vk.sa.pages;

import ee.ttu.vk.sa.domain.Attendance;
import ee.ttu.vk.sa.pages.providers.AttendanceDataProvider;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.util.time.Duration;

/**
 * Created by fjodor on 14.02.16.
 */
@AuthorizeInstantiation(Roles.USER)
public class AttendancePage extends AbstractPage {

    public AttendancePage() {
        AttendanceDataProvider attendanceDataProvider = new AttendanceDataProvider();
        add(getAttendanceTable(attendanceDataProvider, Duration.seconds(1)));
    }

    private WebMarkupContainer getAttendanceTable(IDataProvider<Attendance> dataProvider, Duration updateTimeout){
        WebMarkupContainer container = new WebMarkupContainer("attendanceTable");
        container.setOutputMarkupId(true);
        container.add(new AjaxSelfUpdatingTimerBehavior(updateTimeout));
        DataView<Attendance> dataView = new DataView<Attendance>("attendance", dataProvider) {
            @Override
            protected void populateItem(Item<Attendance> item) {
                item.add(new Label("student.fullname"));
                item.add(new Label("subject.name"));
                item.add(new Label("status"));
            }
        };
        container.add(dataView);
        return container;
    }
}

package ee.ttu.vk.sa.pages.statistics;

import com.google.common.collect.Lists;
import de.agilecoders.wicket.core.markup.html.bootstrap.block.LabelType;
import ee.ttu.vk.sa.domain.Attendance;
import ee.ttu.vk.sa.enums.Status;
import ee.ttu.vk.sa.pages.AbstractPage;
import ee.ttu.vk.sa.pages.attendance.AttendancePanel;
import ee.ttu.vk.sa.pages.components.ColorEnumLabel;
import ee.ttu.vk.sa.pages.providers.AttendanceDataProvider;
import ee.ttu.vk.sa.service.AttendanceService;
import ee.ttu.vk.sa.service.StudentService;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * Created by fjodor on 2.03.16.
 */
public class StatisticsPage extends AbstractPage {
    @SpringBean
    private AttendanceService attendanceService;

    @SpringBean
    private StudentService studentService;

    private AttendanceDataProvider attendanceDataProvider;

    private WebMarkupContainer container;
    private AttendancePanel filterPanel;

    public StatisticsPage() {
        attendanceDataProvider = new AttendanceDataProvider();
        filterPanel = addAttendancePanel();
        add(getAttendanceTable(attendanceDataProvider), filterPanel);
    }

    private AttendancePanel addAttendancePanel() {
        return new AttendancePanel("filterPanel") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> ajaxForm) {
                Attendance attendance = (Attendance) ajaxForm.getModelObject();
                attendance.setStatus(Status.INACTIVE);
                attendanceDataProvider.setFilterState(attendance);
                target.add(container);
            }
        };
    }

    private WebMarkupContainer getAttendanceTable(IDataProvider<Attendance> dataProvider) {
        container = new WebMarkupContainer("attendanceTable");
        container.setOutputMarkupId(true);
        DataView<Attendance> dataView = new DataView<Attendance>("attendance", dataProvider) {
            @Override
            protected void populateItem(Item<Attendance> item) {
                Attendance attendance = item.getModelObject();
                item.add(new Label("student.fullname"));
                item.add(new Label("result", Model.of(attendanceService.getAttendance(attendance.getSubject(), attendance.getGroup(), attendance.getStudent()))));
            }
        };
        container.add(dataView);
        return container;
    }
}

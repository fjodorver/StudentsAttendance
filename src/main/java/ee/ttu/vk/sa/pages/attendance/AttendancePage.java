package ee.ttu.vk.sa.pages.attendance;

import com.google.common.collect.Lists;
import de.agilecoders.wicket.core.markup.html.bootstrap.block.LabelType;
import ee.ttu.vk.sa.domain.Attendance;
import ee.ttu.vk.sa.enums.Status;
import ee.ttu.vk.sa.pages.AbstractPage;
import ee.ttu.vk.sa.pages.components.ColorEnumLabel;
import ee.ttu.vk.sa.pages.providers.AttendanceDataProvider;
import ee.ttu.vk.sa.service.AttendanceService;
import ee.ttu.vk.sa.service.StudentService;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * Created by fjodor on 14.02.16.
 */
@AuthorizeInstantiation(Roles.USER)
public class AttendancePage extends AbstractPage {

    @SpringBean
    private AttendanceService attendanceService;

    @SpringBean
    private StudentService studentService;

    private AttendanceDataProvider attendanceDataProvider;

    private WebMarkupContainer container;
    private AttendancePanel filterPanel;

    public AttendancePage() {
        attendanceDataProvider = new AttendanceDataProvider();
        filterPanel = addAttendancePanel();
        add(getAttendanceTable(attendanceDataProvider), filterPanel);
    }

    private AttendancePanel addAttendancePanel() {
        return new AttendancePanel("filterPanel") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> ajaxForm) {
                Component component = (Component) ajaxForm.findSubmittingButton();
                Attendance attendance = (Attendance) ajaxForm.getModelObject();
                attendance.setStatus(Status.INACTIVE);
                switch (component.getId()){
                    case "generate":
                        List<Attendance> attendances = Lists.newArrayList();
                        studentService.findAll(attendance.getSubject())
                                .forEachRemaining(x -> attendances.add(SerializationUtils.clone(attendance).setStudent(x)));
                        attendanceService.save(attendances);
                        break;
                    case "find":
                        attendanceDataProvider.setFilterState(attendance);
                        break;
                }
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
                item.add(new Label("student.fullname"));
                item.add(new Label("subject.name"));
                item.add(new ColorEnumLabel<Status>("status", null).addEnumLabel(Status.PRESENT, LabelType.Success)
                        .addEnumLabel(Status.ABSENT, LabelType.Danger).addEnumLabel(Status.INACTIVE, LabelType.Default));
                item.add(new DropDownChoice<>("statusChoice", new PropertyModel<>(item.getDefaultModel(), "status"), Lists.newArrayList(Status.values()))
                        .add(new AjaxFormComponentUpdatingBehavior("change") {
                            @Override
                            protected void onUpdate(AjaxRequestTarget ajaxRequestTarget) {
                                attendanceDataProvider.setFilterState(attendanceService.save(item.getModelObject()));
                                ajaxRequestTarget.add(container);
                            }
                        }));
            }
        };
        container.add(dataView);
        return container;
    }
}
package ee.ttu.vk.sa.pages;

import com.google.common.collect.Lists;
import de.agilecoders.wicket.core.markup.html.bootstrap.block.LabelType;
import ee.ttu.vk.sa.domain.Attendance;
import ee.ttu.vk.sa.enums.Status;
import ee.ttu.vk.sa.pages.components.ColorEnumLabel;
import ee.ttu.vk.sa.pages.attendance.FilterPanel;
import ee.ttu.vk.sa.pages.providers.AttendanceDataProvider;
import ee.ttu.vk.sa.service.AttendanceService;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * Created by fjodor on 14.02.16.
 */
@AuthorizeInstantiation(Roles.USER)
public class AttendancePage extends AbstractPage implements FilterPanel.IFilterPanel {

    @SpringBean
    private AttendanceService attendanceService;

    private AttendanceDataProvider attendanceDataProvider;

    private WebMarkupContainer container;
    private FilterPanel filterPanel;

    public AttendancePage() {
        attendanceDataProvider = new AttendanceDataProvider();
        filterPanel = new FilterPanel("filterPanel", this);
        add(getAttendanceTable(attendanceDataProvider), filterPanel);
    }

    private WebMarkupContainer getAttendanceTable(IDataProvider<Attendance> dataProvider) {
        container = new WebMarkupContainer("attendanceTable");
        container.setOutputMarkupId(true);
        DataView<Attendance> dataView = new DataView<Attendance>("attendance", dataProvider) {
            @Override
            protected void populateItem(Item<Attendance> item) {
                item.add(new Label("student.fullname"));
                item.add(new Label("subject.name"));
                item.add(new ColorEnumLabel<Status>("status", null).addEnumLabel(Status.PRESENT, LabelType.Success).addEnumLabel(Status.ABSENT, LabelType.Danger));
                item.add(new DropDownChoice<>("statusChoice", new PropertyModel<>(item.getDefaultModel(), "status"), Lists.newArrayList(Status.values()))
                        .add(new AjaxFormComponentUpdatingBehavior("onchange") {
                            @Override
                            protected void onUpdate(AjaxRequestTarget ajaxRequestTarget) {
                                attendanceService.save(item.getModelObject());
                                ajaxRequestTarget.add(container);
                            }
                        }));
            }
        };
        container.add(dataView);
        return container;
    }

    @Override
    public void onSubmit(AjaxRequestTarget target, IModel<Attendance> model) {
        attendanceDataProvider.setFilterState(model.getObject());
        target.add(container);
    }
}

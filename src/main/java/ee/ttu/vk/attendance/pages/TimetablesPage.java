package ee.ttu.vk.attendance.pages;

import de.agilecoders.wicket.core.markup.html.bootstrap.form.BootstrapForm;
import de.agilecoders.wicket.core.markup.html.bootstrap.navigation.ajax.BootstrapAjaxPagingNavigator;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.DateTextField;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.DateTextFieldConfig;
import ee.ttu.vk.attendance.CustomAuthenticatedWebSession;
import ee.ttu.vk.attendance.domain.Attendance;
import ee.ttu.vk.attendance.domain.Timetable;
import ee.ttu.vk.attendance.enums.Status;
import ee.ttu.vk.attendance.pages.components.ColorEnumLabel;
import ee.ttu.vk.attendance.pages.filters.TimetableFilter;
import ee.ttu.vk.attendance.pages.panels.AttendancePanel;
import ee.ttu.vk.attendance.pages.providers.AbstractDataProvider;
import ee.ttu.vk.attendance.pages.providers.TimetableDataProvider;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

public class TimetablesPage extends AbstractPage {

    private WebMarkupContainer body;
    private DataView<Timetable> rows;
    private AbstractDataProvider<Timetable, TimetableFilter> dataProvider;
    private Form<TimetableFilter> searchForm;
    private AttendancePanel panel;

    public TimetablesPage() {
        panel = new AttendancePanel("panel");
        dataProvider = new TimetableDataProvider();
        dataProvider.getFilterState().setTeacher(CustomAuthenticatedWebSession.getSession().getTeacher());
        body = new WebMarkupContainer("body");
        body.setOutputMarkupId(true);
        searchForm = new BootstrapForm<>("searchForm", new CompoundPropertyModel<>(new TimetableFilter()));
        rows = new DataView<Timetable>("rows", dataProvider) {
            @Override
            protected void populateItem(Item<Timetable> item) {
                Timetable timetable = item.getModelObject();
                item.add(new Label("start", Model.of(timetable.getStart().format(DateTimeFormatter.ofPattern("HH:mm")))));
                item.add(new Label("end", Model.of(timetable.getEnd().format(DateTimeFormatter.ofPattern("HH:mm")))));
                item.add(new Label("subject"));
                item.add(new Label("programme"));
                item.add(new ColorEnumLabel<Status>("lessonType", null));
                item.add(new AjaxLink<Timetable>("add") {
                    @Override
                    public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                        Attendance attendance = new Attendance();
                        attendance.setTimetable(timetable);
                        panel.changeFilter(Model.of(attendance));
                        ajaxRequestTarget.add(panel);
                        panel.appendShowDialogJavaScript(ajaxRequestTarget);
                    }
                });
            }
        };

        searchForm.add(new DateTextField("date", new DateTextFieldConfig()
                .showTodayButton(DateTextFieldConfig.TodayButton.TRUE).autoClose(true).highlightToday(true))
                .add(new AjaxFormComponentUpdatingBehavior("onchange") {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                TimetableFilter filter = searchForm.getModelObject();
                dataProvider.getFilterState().setDate(Optional.ofNullable(filter.getDate()).orElse(new Date(0)));
                target.add(body);
            }
        }));
        body.add(rows);
        add(new BootstrapAjaxPagingNavigator("navigator", rows), body, searchForm, panel);
    }
}
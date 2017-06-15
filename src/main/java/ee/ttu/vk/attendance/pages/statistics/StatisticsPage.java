package ee.ttu.vk.attendance.pages.statistics;

import com.google.common.collect.Sets;
import ee.ttu.vk.attendance.CustomAuthenticatedWebSession;
import ee.ttu.vk.attendance.domain.Attendance;
import ee.ttu.vk.attendance.domain.Student;
import ee.ttu.vk.attendance.domain.Timetable;
import ee.ttu.vk.attendance.pages.AbstractPage;
import ee.ttu.vk.attendance.pages.filters.TimetableFilter;
import ee.ttu.vk.attendance.pages.panels.TablePanel;
import ee.ttu.vk.attendance.pages.providers.AbstractDataProvider;
import ee.ttu.vk.attendance.pages.providers.TimetableDataProvider;
import ee.ttu.vk.attendance.service.AttendanceService;
import ee.ttu.vk.attendance.service.StudentService;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.util.MapModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class StatisticsPage extends AbstractPage {

    @SpringBean
    private StudentService studentService;

    @SpringBean
    private AttendanceService attendanceService;

    private ChartPanel chartPanel;

    private TreeMap<Student, List<Attendance>> map = new TreeMap<>();

    public StatisticsPage() {
        AbstractDataProvider<Timetable, TimetableFilter> dataProvider = new TimetableDataProvider();
        TablePanel<Timetable, TimetableFilter> tablePanel = new TablePanel<>("table", dataProvider);
        dataProvider.getFilterState().setDate(null);
        dataProvider.getFilterState().setTeacher(CustomAuthenticatedWebSession.getSession().getTeacher());
        tablePanel.addLink(new ResourceModel("table.subject").getObject(), "subject", (target, model) -> {
            Timetable timetable = model.getObject();
            Set<Attendance> attendances = Sets.newHashSet();
            for (Student student : studentService.findAll(timetable.getProgramme())) {
                attendances.addAll(attendanceService.findAll(new Attendance(student, timetable)));
            }
            map.clear();
            map.putAll(attendances.stream().collect(Collectors.groupingBy(Attendance::getStudent)));
            chartPanel.onModelChanged();
            chartPanel.setTitle(String.format("%1$s - %2$s", timetable.getSubject(), timetable.getProgramme()));
            target.add(chartPanel);
        });
        tablePanel.addColumn(new ResourceModel("table.programme").getObject(), "programme", "col-lg-6");
        tablePanel.setVisibleFilterPanel(false);
        add(tablePanel, chartPanel = new ChartPanel("chart", new MapModel<>(map)));
    }
}
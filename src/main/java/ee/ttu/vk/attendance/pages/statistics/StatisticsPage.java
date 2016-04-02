package ee.ttu.vk.attendance.pages.statistics;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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
import org.apache.wicket.model.util.MapModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Fjodor Vershinin on 4/1/2016.
 */
public class StatisticsPage extends AbstractPage {

    @SpringBean
    private StudentService studentService;

    @SpringBean
    private AttendanceService attendanceService;

    private ChartPanel chartPanel;

    private AbstractDataProvider<Timetable, TimetableFilter> dataProvider = new TimetableDataProvider();

    private Map<Student, List<Attendance>> map = Maps.newHashMap();

    public StatisticsPage() {
        TablePanel<Timetable, TimetableFilter> tablePanel = new TablePanel<>("table", dataProvider);
        dataProvider.getFilterState().setDate(null);
        dataProvider.getFilterState().setTeacher(CustomAuthenticatedWebSession.getSession().getTeacher());
        tablePanel.addLink("Subject", "subject", (target, model) -> {
            Timetable timetable = model.getObject();
            Set<Attendance> attendances = Sets.newHashSet();
            studentService.findAll(timetable.getProgramme()).forEach(x -> attendances.addAll(attendanceService.findAll(new Attendance().setStudent(x).setTimetable(timetable))));
            map.clear();
            map.putAll(attendances.stream().collect(Collectors.groupingBy(Attendance::getStudent)));
            chartPanel.onModelChanged();;
            chartPanel.setTitle(String.format("%1$s - %2$s", timetable.getSubject(), timetable.getProgramme()));
            target.add(chartPanel);
        });
        tablePanel.addColumn("Programme", "programme", "col-lg-6");
        add(tablePanel, chartPanel = new ChartPanel("chart", new MapModel<>(map)));
    }
}

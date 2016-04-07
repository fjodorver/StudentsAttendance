package ee.ttu.vk.attendance.pages.statistics;

import com.google.common.collect.Lists;
import com.googlecode.wickedcharts.highcharts.options.*;
import com.googlecode.wickedcharts.highcharts.options.Cursor;
import com.googlecode.wickedcharts.highcharts.options.color.HexColor;
import com.googlecode.wickedcharts.highcharts.options.color.HighchartsColor;
import com.googlecode.wickedcharts.highcharts.options.drilldown.DrilldownPoint;
import com.googlecode.wickedcharts.highcharts.options.series.*;
import com.googlecode.wickedcharts.highcharts.options.series.Point;
import com.googlecode.wickedcharts.wicket7.highcharts.Chart;
import ee.ttu.vk.attendance.domain.Attendance;
import ee.ttu.vk.attendance.domain.Student;
import ee.ttu.vk.attendance.domain.Timetable;
import ee.ttu.vk.attendance.enums.Status;
import ee.ttu.vk.attendance.pages.statistics.options.BaseOptions;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.util.MapModel;

import java.awt.*;
import java.text.MessageFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by fjodor on 3.03.16.
 */
public class ChartPanel extends Panel {

    private static final String INACTIVE = "Inactive";
    private static final String PRESENTS = "Presents";
    private static final String ABSENTS = "Absents";

    private Chart chart;
    private BaseOptions baseOptions = new BaseOptions();

    private MapModel<Student, List<Attendance>> mapModel;

    public ChartPanel(String id, MapModel<Student, List<Attendance>> mapModel) {
        super(id);
        this.mapModel = mapModel;
        setOutputMarkupId(true);
        add(chart = new Chart("chart", baseOptions));
        setTitle(new ResourceModel("chart.empty").getObject());
    }

    public void setTitle(String title) {
        chart.getOptions().setTitle(new Title().setText(title));
    }

    @Override
    protected void onModelChanged() {
        baseOptions = new BaseOptions();
        Map<Student, List<Attendance>> map = mapModel.getObject();
        PointSeries absents = new PointSeries();
        PointSeries presents = new PointSeries();
        PointSeries inactive = new PointSeries();
        for (Student student : map.keySet()) {
            Map<Status, List<Attendance>> statusMap = map.get(student).stream().collect(Collectors.groupingBy(Attendance::getStatus));
            inactive.addPoint(getPoint(String.format("%1$s[%2$s]:", INACTIVE, student), statusMap, Status.INACTIVE)).setColor(new HighchartsColor(Status.INACTIVE.ordinal()));
            absents.addPoint(getPoint(String.format("%1$s[%2$s]:", ABSENTS, student), statusMap, Status.ABSENT)).setColor(new HighchartsColor(Status.ABSENT.ordinal()));;
            presents.addPoint(getPoint(String.format("%1$s[%2$s]:", PRESENTS, student), statusMap, Status.PRESENT)).setColor(new HighchartsColor(Status.PRESENT.ordinal()));;
        }
        baseOptions.setxAxis(new Axis().setCategories(map.keySet().stream().map(Student::getFullname)
                .collect(Collectors.toList())));
        baseOptions.setyAxis(new Axis().setTitle(new Title("Percent")));
        baseOptions.addSeries(inactive.setName(INACTIVE));
        baseOptions.addSeries(absents.setName(ABSENTS));
        baseOptions.addSeries(presents.setName(PRESENTS));
        chart.setOptions(baseOptions);
    }

    private Point getPoint(String title, Map<Status, List<Attendance>> map, Status status){
        List<Attendance> attendances = Optional.ofNullable(map.get(status)).orElse(Lists.newArrayList());
        return new DrilldownPoint(baseOptions, new StudentDrillDownOptions(attendances).setTitle(new Title(title))).setY(attendances.size());
    }

    private class StudentDrillDownOptions extends Options {
        StudentDrillDownOptions(List<Attendance> attendances) {
            int status = attendances.stream().findFirst().orElse(new Attendance()).getStatus().ordinal();
            copyFrom(baseOptions);
            setChartOptions(new ChartOptions().setType(SeriesType.AREA));
            setTooltip(new Tooltip().setFormatter(new Function("return this.x;")));
            PointSeries pointSeries = new PointSeries();
            pointSeries.setColor(new HighchartsColor(status));
            setxAxis(new Axis().setCategories(attendances .stream()
                    .sorted((x, y) -> x.getTimetable().getStart().compareTo(y.getTimetable().getStart()))
                    .map(x -> x.getTimetable().getStart().format(DateTimeFormatter.ofPattern("dd.MM")))
                    .collect(Collectors.toList())));
            IntStream.range(0, attendances.size())
                    .forEach(x -> pointSeries.addPoint(new DrilldownPoint(this, baseOptions).setY(0)));
            setyAxis(new Axis().setTitle(new Title("")).setCategories(Lists.newArrayList("")));
            addSeries(pointSeries.setShowInLegend(false));
        }
    }
}

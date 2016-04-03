package ee.ttu.vk.attendance.pages.statistics;

import com.google.common.collect.Lists;
import com.googlecode.wickedcharts.highcharts.options.*;
import com.googlecode.wickedcharts.highcharts.options.Cursor;
import com.googlecode.wickedcharts.highcharts.options.color.HexColor;
import com.googlecode.wickedcharts.highcharts.options.color.HighchartsColor;
import com.googlecode.wickedcharts.highcharts.options.drilldown.DrilldownPoint;
import com.googlecode.wickedcharts.highcharts.options.series.PointSeries;
import com.googlecode.wickedcharts.highcharts.options.series.SimpleSeries;
import com.googlecode.wickedcharts.wicket7.highcharts.Chart;
import ee.ttu.vk.attendance.domain.Attendance;
import ee.ttu.vk.attendance.domain.Student;
import ee.ttu.vk.attendance.domain.Timetable;
import ee.ttu.vk.attendance.enums.Status;
import ee.ttu.vk.attendance.pages.statistics.options.BaseOptions;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.util.MapModel;

import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by fjodor on 3.03.16.
 */
public class ChartPanel extends Panel {

    private Chart chart;
    private BaseOptions baseOptions = new BaseOptions();

    private MapModel<Student, List<Attendance>> mapModel;

    public ChartPanel(String id, MapModel<Student, List<Attendance>> mapModel) {
        super(id);
        this.mapModel = mapModel;
        setOutputMarkupId(true);
        add(chart = new Chart("chart", baseOptions));
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
            Map<Timetable, Status> statusMap = map.get(student).stream().collect(Collectors.toMap(Attendance::getTimetable, Attendance::getStatus));
            inactive.addPoint(new DrilldownPoint(baseOptions, new StudentDrillDownOptions(statusMap))
                    .setY(map.get(student).stream().filter(x -> x.getStatus() == Status.INACTIVE).count()));
            absents.addPoint(new DrilldownPoint(baseOptions, new StudentDrillDownOptions(statusMap))
                    .setY(map.get(student).stream().filter(x -> x.getStatus() == Status.ABSENT).count()));
            presents.addPoint(new DrilldownPoint(baseOptions, new StudentDrillDownOptions(statusMap))
                    .setY(map.get(student).stream().filter(x -> x.getStatus() == Status.PRESENT).count()));
        }
        baseOptions.setPlotOptions(new PlotOptionsChoice().setSeries(new PlotOptions().setStacking(Stacking.PERCENT)).setColumn(new PlotOptions().setCursor(Cursor.POINTER)));
        baseOptions.setxAxis(new Axis().setCategories(map.keySet().stream().map(Student::getFullname).collect(Collectors.toList())));
        baseOptions.addSeries(inactive);
        baseOptions.addSeries(absents);
        baseOptions.addSeries(presents);
        chart.setOptions(baseOptions);
    }

    private class StudentDrillDownOptions extends Options {
        StudentDrillDownOptions(Map<Timetable, Status> map) {
            copyFrom(baseOptions);
            PointSeries pointSeries = new PointSeries();
            map.values().forEach(x -> {
                switch (x){
                    case PRESENT:
                        pointSeries.addPoint(new DrilldownPoint(this, baseOptions).setY((100)));
                        break;
                    case ABSENT:
                        pointSeries.addPoint(new DrilldownPoint(this, baseOptions).setY((0)));
                        break;
                    case INACTIVE:
                        pointSeries.addPoint(new DrilldownPoint(this, baseOptions).setY((50)));
                        break;
                }
            });
            setxAxis(new Axis().setCategories(map.keySet().stream().map(x -> x.getStart().format(DateTimeFormatter.ISO_DATE)).collect(Collectors.toList())));
            addSeries(pointSeries);
        }
    }
}

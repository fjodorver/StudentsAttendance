package ee.ttu.vk.attendance.pages.statistics;

import com.googlecode.wickedcharts.highcharts.options.*;
import com.googlecode.wickedcharts.highcharts.options.series.SimpleSeries;
import com.googlecode.wickedcharts.wicket7.highcharts.Chart;
import ee.ttu.vk.attendance.domain.Attendance;
import ee.ttu.vk.attendance.domain.Student;
import ee.ttu.vk.attendance.enums.Status;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.util.MapModel;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by fjodor on 3.03.16.
 */
public class ChartPanel extends Panel {

    private Chart chart;

    private MapModel<Student, List<Attendance>> mapModel;

    public ChartPanel(String id, MapModel<Student, List<Attendance>> mapModel) {
        super(id);
        this.mapModel = mapModel;
        setOutputMarkupId(true);
        add(chart = new Chart("chart", getOptions(new Options())));
    }

    public void setTitle(String title){
        chart.getOptions().setTitle(new Title().setText(title));
    }

    private Options getOptions(Options options) {
        options.setChartOptions(new ChartOptions().setType(SeriesType.BAR));
        options.getChartOptions().setHeight(600);
        options.getChartOptions().setBorderWidth(2);
        options.getChartOptions().setBorderColor(Color.gray);
        options.setExporting(new ExportingOptions().setEnabled(false));
        options.setPlotOptions(new PlotOptionsChoice().setSeries(new PlotOptions().setStacking(Stacking.PERCENT)));
        if(mapModel.getObject().size() == 0) options.setTitle(new Title().setText("Please select Subject"));
        return options;
    }

    @Override
    protected void onModelChanged() {
        Options options = new Options();
        Map<Student, List<Attendance>> map = mapModel.getObject();
        options.setxAxis(new Axis().setCategories(map.keySet().stream().map(Student::getFullname).collect(Collectors.toList())));
        options.addSeries(new SimpleSeries().setName("Inactive").setData(map.entrySet().stream().map(x -> calcResult(x.getValue(), Status.INACTIVE)).collect(Collectors.toList())));
        options.addSeries(new SimpleSeries().setName("Absents").setData(map.entrySet().stream().map(x -> calcResult(x.getValue(), Status.ABSENT)).collect(Collectors.toList())));
        options.addSeries(new SimpleSeries().setName("Presents").setData(map.entrySet().stream().map(x -> calcResult(x.getValue(), Status.PRESENT)).collect(Collectors.toList())));
        chart.setOptions(getOptions(options));
    }
    private Number calcResult(List<Attendance> attendances, Status status){
        return (double)attendances.stream().filter(x -> x.getStatus() == status).count();
    }
}

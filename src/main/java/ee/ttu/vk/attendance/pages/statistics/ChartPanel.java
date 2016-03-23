package ee.ttu.vk.attendance.pages.statistics;

import com.googlecode.wickedcharts.highcharts.options.*;
import com.googlecode.wickedcharts.wicket7.highcharts.Chart;
import ee.ttu.vk.attendance.domain.Student;
import ee.ttu.vk.attendance.domain.Subject;
import ee.ttu.vk.attendance.service.GroupService;
import ee.ttu.vk.attendance.service.SubjectService;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.awt.*;

/**
 * Created by fjodor on 3.03.16.
 */
public class ChartPanel extends Panel {


    @SpringBean
    private SubjectService subjectService;

    @SpringBean
    private GroupService groupService;

    private Chart chart;

    private ListModel<Student> listModel;
    private IModel<Subject> subjectModel;
    public ChartPanel(String id, ListModel<Student> listModel, IModel<Subject> subjectModel) {
        super(id);
        this.listModel = listModel;
        this.subjectModel = subjectModel;
        setOutputMarkupId(true);
        add(chart = new Chart("chart", getOptions(new Options())));
    }

    private Options getOptions(Options options) {
        options.setChartOptions(new ChartOptions().setType(SeriesType.BAR));
        options.getChartOptions().setHeight(600);
        options.getChartOptions().setBorderWidth(2);
        options.getChartOptions().setBorderColor(Color.gray);
        options.setExporting(new ExportingOptions().setEnabled(false));
        options.setPlotOptions(new PlotOptionsChoice().setSeries(new PlotOptions().setStacking(Stacking.PERCENT)));
        return options;
    }

    @Override
    protected void onModelChanged() {
//        List<Number> presents = listModel.getObject().stream().map(x -> attendanceService.getPresentsNumber(subjectModel.getObject(), x.getGroup(), x)).collect(Collectors.toList());
//        List<Number> absents = listModel.getObject().stream().map(x -> attendanceService.getAbsentsNumber(subjectModel.getObject(), x.getGroup(), x)).collect(Collectors.toList());
//        List<String> xCategories = listModel.getObject().stream().map(Student::getFullname).collect(Collectors.toList());
//        Options options = new Options();
//        options.setxAxis(new Axis().setCategories(xCategories));
//        options.addSeries(new SimpleSeries().setName("Presents").setData(presents));
//        options.addSeries(new SimpleSeries().setName("Absents").setData(absents));
//        chart.setOptions(getOptions(options));
    }
}

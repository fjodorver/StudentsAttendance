package ee.ttu.vk.attendance.pages.statistics.options;

import com.googlecode.wickedcharts.highcharts.options.ChartOptions;
import com.googlecode.wickedcharts.highcharts.options.ExportingOptions;
import com.googlecode.wickedcharts.highcharts.options.Options;
import com.googlecode.wickedcharts.highcharts.options.SeriesType;

import java.awt.*;

/**
 * Created by Fjodor Vershinin on 4/3/2016.
 */
public class BaseOptions extends Options {
    public BaseOptions() {
        setChartOptions(new ChartOptions().setType(SeriesType.BAR));
        getChartOptions().setHeight(800);
        getChartOptions().setBorderWidth(2);
        getChartOptions().setBorderColor(Color.gray);
        setExporting(new ExportingOptions().setEnabled(false));
    }
}

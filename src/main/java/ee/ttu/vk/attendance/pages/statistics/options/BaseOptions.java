package ee.ttu.vk.attendance.pages.statistics.options;

import com.googlecode.wickedcharts.highcharts.options.*;
import com.googlecode.wickedcharts.highcharts.options.Cursor;
import com.googlecode.wickedcharts.highcharts.options.color.HighchartsColor;

import java.awt.*;

/**
 * Created by Fjodor Vershinin on 4/3/2016.
 */
public class BaseOptions extends Options {
    public BaseOptions() {
        setChartOptions(new ChartOptions().setType(SeriesType.BAR));
        getChartOptions().setHeight(800);
        getChartOptions().setBorderColor(Color.gray);
        setExporting(new ExportingOptions().setEnabled(false));
        setCredits(new CreditOptions().setEnabled(false));
        setPlotOptions(new PlotOptionsChoice().setSeries(new PlotOptions().setStacking(Stacking.PERCENT)));

    }
}

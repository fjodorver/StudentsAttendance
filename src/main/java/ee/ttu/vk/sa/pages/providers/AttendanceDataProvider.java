package ee.ttu.vk.sa.pages.providers;

import ee.ttu.vk.sa.domain.Attendance;
import ee.ttu.vk.sa.service.AttendanceService;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.Iterator;

/**
 * Created by fjodor on 14.02.16.
 */
public class AttendanceDataProvider extends SortableDataProvider<Attendance, Attendance> implements IFilterStateLocator<Attendance> {

    @SpringBean
    private AttendanceService attendanceService;

    private Attendance attendance;

    public AttendanceDataProvider() {
        Injector.get().inject(this);
    }

    @Override
    public Iterator<? extends Attendance> iterator(long first, long count) {
        return attendanceService.findAll().iterator();
    }

    @Override
    public long size() {
        return attendanceService.findAll().size();
    }

    @Override
    public IModel<Attendance> model(Attendance attendance) {
        return new CompoundPropertyModel<>(attendance);
    }

    @Override
    public Attendance getFilterState() {
        return null;
    }

    @Override
    public void setFilterState(Attendance attendance) {

    }
}

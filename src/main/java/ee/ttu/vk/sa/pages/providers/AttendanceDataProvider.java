package ee.ttu.vk.sa.pages.providers;

import com.google.common.collect.Lists;
import ee.ttu.vk.sa.domain.Attendance;
import ee.ttu.vk.sa.service.AttendanceService;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * Created by fjodor on 14.02.16.
 */
public class AttendanceDataProvider extends SortableDataProvider<Attendance, Attendance> implements IFilterStateLocator<Attendance> {

    @SpringBean
    private AttendanceService attendanceService;

    private Attendance attendance;

    public AttendanceDataProvider() {
        attendance = new Attendance();
        Injector.get().inject(this);
    }

    @Override
    public Iterator<? extends Attendance> iterator(long first, long count) {
        List<Attendance> attendances = attendanceService.findAll(attendance.getSubject(), attendance.getGroup(), attendance.getType(), attendance.getDate());
        return Optional.of(attendances.iterator()).orElse(Lists.newArrayList(new Attendance()).iterator());
    }

    @Override
    public long size() {
        return attendanceService.getSize();
    }

    @Override
    public IModel<Attendance> model(Attendance attendance) {
        return new CompoundPropertyModel<>(attendance);
    }

    @Override
    public Attendance getFilterState() {
        return attendance;
    }

    @Override
    public void setFilterState(Attendance attendance) {
        this.attendance = attendance;
    }
}

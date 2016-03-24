package ee.ttu.vk.attendance.pages.providers;

import ee.ttu.vk.attendance.domain.Attendance;
import ee.ttu.vk.attendance.pages.filters.TimetableFilter;
import ee.ttu.vk.attendance.service.AttendanceService;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Iterator;

/**
 * Created by strukov on 3/24/16.
 */
public class AttendanceDataProvider extends AbstractDataProvider<Attendance, Attendance> {

    @SpringBean
    AttendanceService attendanceService;

    public AttendanceDataProvider(){
        Injector.get().inject(this);
        filter = new Attendance();
    }

    @Override
    public Iterator<? extends Attendance> iterator(long first, long count) {
        Pageable pageable = new PageRequest((int)(first/count), (int)count, Sort.Direction.ASC, "student.fullname");
        return attendanceService.findAll(filter, pageable).iterator();    }

    @Override
    public long size() {
        return attendanceService.size(filter);
    }
}

package ee.ttu.vk.attendance.pages.providers;

import ee.ttu.vk.attendance.domain.Attendance;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.Iterator;

/**
 * Created by strukov on 3/24/16.
 */
public class AttendanceDataProvider extends AbstractDataProvider<Attendance, Attendance> {


    @Override
    public Iterator<? extends Attendance> iterator(long l, long l1) {
        return null;
    }

    @Override
    public long size() {
        return 0;
    }
}

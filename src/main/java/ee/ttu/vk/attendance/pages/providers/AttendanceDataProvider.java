package ee.ttu.vk.attendance.pages.providers;

import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import ee.ttu.vk.attendance.domain.Attendance;
import ee.ttu.vk.attendance.service.AttendanceService;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

/**
 * Created by strukov on 3/24/16.
 */
public class AttendanceDataProvider extends AbstractDataProvider<Attendance, Attendance> {

    private Map<Long, Attendance> attendanceMap = Maps.newLinkedHashMap();

    @SpringBean
    AttendanceService attendanceService;

    public AttendanceDataProvider(){
        Injector.get().inject(this);
        filter = new Attendance();
    }

    @Override
    public Iterator<? extends Attendance> iterator(long first, long count) {
        Pageable pageable = new PageRequest((int)(first/count), (int)count, Sort.Direction.ASC, "student.lastname");
        if(filter.getId() == null){
            attendanceMap.clear();
            attendanceService.findAll(filter, pageable).forEach(x -> attendanceMap.put(x.getId(), x));
        }
        else{
            attendanceMap.put(filter.getId(), SerializationUtils.clone(filter));
            filter.setId(null);
        }
        return Optional.of(attendanceMap.values().iterator()).orElse(Iterators.forArray());
    }

    @Override
    public long size() {
        return attendanceService.size(filter);
    }
}

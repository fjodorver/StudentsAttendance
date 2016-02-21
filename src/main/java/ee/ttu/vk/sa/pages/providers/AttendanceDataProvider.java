package ee.ttu.vk.sa.pages.providers;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import ee.ttu.vk.sa.domain.Attendance;
import ee.ttu.vk.sa.service.AttendanceService;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.*;

/**
 * Created by fjodor on 14.02.16.
 */
public class AttendanceDataProvider extends SortableDataProvider<Attendance, String> implements IFilterStateLocator<Attendance>{

    private Map<Long, Attendance> attendanceMap;

    @SpringBean
    private AttendanceService attendanceService;

    private Attendance attendance;

    public AttendanceDataProvider() {
        attendanceMap = Maps.newLinkedHashMap();
        attendance = new Attendance();
        setSort("lastname", SortOrder.ASCENDING);
        Injector.get().inject(this);
    }

    @Override
    public Iterator<? extends Attendance> iterator(long first, long count) {
        Pageable pageable = new PageRequest((int)(first/count), (int)(count), Sort.Direction.ASC, "student.lastname");
        if(attendance.getId() == null){
            attendanceMap.clear();
            List<Attendance> attendances = attendanceService.findAll(attendance, pageable);
            attendances.forEach(x -> attendanceMap.put(x.getId(), x));
        }
        else {
            Attendance attendance = SerializationUtils.clone(this.attendance);
            attendanceMap.put(attendance.getId(), SerializationUtils.clone(attendance));
            this.attendance.setId(null);
        }
        return Optional.of(attendanceMap.values().iterator()).orElse(Lists.newArrayList(new Attendance()).iterator());
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

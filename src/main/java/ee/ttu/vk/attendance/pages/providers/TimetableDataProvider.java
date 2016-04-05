package ee.ttu.vk.attendance.pages.providers;

import ee.ttu.vk.attendance.domain.Timetable;
import ee.ttu.vk.attendance.pages.filters.TimetableFilter;
import ee.ttu.vk.attendance.service.TimetableService;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Iterator;

/**
 * Created by fjodor on 3/23/16.
 */
public class TimetableDataProvider extends AbstractDataProvider<Timetable, TimetableFilter> {

    @SpringBean
    private TimetableService timetableService;

    public TimetableDataProvider() {
        Injector.get().inject(this);
        filter = new TimetableFilter();
    }

    @Override
    public Iterator<? extends Timetable> iterator(long first, long count) {
        Pageable pageable = new PageRequest((int)(first/count), (int)count, Sort.Direction.ASC, "start");
        return timetableService.findAll(filter, pageable).iterator();
    }

    @Override
    public long size() {
        return timetableService.size(filter);
    }

    @Override
    public IModel<Timetable> model(Timetable timetable) {
        return new CompoundPropertyModel<>(timetable);
    }
}

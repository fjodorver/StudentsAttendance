package ee.ttu.vk.attendance.service;


import ee.ttu.vk.attendance.domain.Timetable;
import ee.ttu.vk.attendance.pages.filters.TimetableFilter;

import java.util.List;

/**
 * Created by fjodor on 5.03.16.
 */
public interface TimetableService extends DataProviderService<Timetable, TimetableFilter> {
    void save(List<Timetable> timetables);
}
package ee.ttu.vk.sa.service;


import ee.ttu.vk.sa.domain.Timetable;

import java.util.List;

/**
 * Created by fjodor on 5.03.16.
 */
public interface TimetableService {
    List<Timetable> save(List<Timetable> timetables);
}

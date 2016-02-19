package ee.ttu.vk.sa.service;

import ee.ttu.vk.sa.domain.Attendance;
import ee.ttu.vk.sa.domain.Group;
import ee.ttu.vk.sa.domain.Subject;
import ee.ttu.vk.sa.enums.Type;

import java.util.Date;
import java.util.List;

/**
 * Created by fjodor on 14.02.16.
 */
public interface AttendanceService {
    List<Attendance> findAll(Subject subject, Group group, Type type, Date date);
    void save(Attendance attendance);
    void save(List<Attendance> attendances);
    int getSize();
}

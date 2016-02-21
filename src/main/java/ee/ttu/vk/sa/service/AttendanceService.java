package ee.ttu.vk.sa.service;

import ee.ttu.vk.sa.domain.Attendance;
import ee.ttu.vk.sa.domain.Group;
import ee.ttu.vk.sa.domain.Subject;
import ee.ttu.vk.sa.enums.Type;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

/**
 * Created by fjodor on 14.02.16.
 */
public interface AttendanceService {
    List<Attendance> findAll(Attendance attendance, Pageable pageable);
    Attendance save(Attendance attendance);
    void save(List<Attendance> attendances);
    int getSize();
}

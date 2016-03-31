package ee.ttu.vk.attendance.service;

import ee.ttu.vk.attendance.domain.Attendance;
import ee.ttu.vk.attendance.domain.Group;
import ee.ttu.vk.attendance.domain.Student;
import ee.ttu.vk.attendance.domain.Timetable;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by strukov on 3/24/16.
 */
public interface AttendanceService extends IReadService<Attendance>, ISaveService<Attendance>, IProviderService<Attendance, Attendance> {
    void GenerateAndSaveAttendances(Group group);
    int getPresentsNumber(Student student);
    int getAbsentsNumber(Student student);
}

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
public interface AttendanceService {
    void generateAttendance(Group group, List<Timetable> timetables);
    List<Attendance> findAll(Attendance attendance, Pageable pageable);
    Attendance findByTimetable(Timetable timetable);
    Attendance save(Attendance attendance);
    List<Attendance> save(List<Attendance> attendances);
    long size(Attendance attendance);
    int getPresentsNumber(Student student);
    int getAbsentsNumber(Student student);
}

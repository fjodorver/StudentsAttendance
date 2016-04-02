package ee.ttu.vk.attendance.service;

import ee.ttu.vk.attendance.domain.Attendance;
import ee.ttu.vk.attendance.domain.Programme;
import ee.ttu.vk.attendance.domain.Student;

import java.util.List;

/**
 * Created by strukov on 3/24/16.
 */
public interface AttendanceService extends ISaveService<Attendance>, IProviderService<Attendance, Attendance>, IFilterService<Attendance, Attendance> {
    void generateAndSaveAttendances(Programme programme);
}

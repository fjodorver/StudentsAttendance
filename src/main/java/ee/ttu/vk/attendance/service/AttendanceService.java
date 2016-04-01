package ee.ttu.vk.attendance.service;

import ee.ttu.vk.attendance.domain.Attendance;
import ee.ttu.vk.attendance.domain.Programme;
import ee.ttu.vk.attendance.domain.Student;

/**
 * Created by strukov on 3/24/16.
 */
public interface AttendanceService extends ISaveService<Attendance>, IProviderService<Attendance, Attendance> {
    void GenerateAndSaveAttendances(Programme programme);
    int getPresentsNumber(Student student);
    int getAbsentsNumber(Student student);
}

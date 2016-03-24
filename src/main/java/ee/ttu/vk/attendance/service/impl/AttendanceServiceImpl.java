package ee.ttu.vk.attendance.service.impl;

import ee.ttu.vk.attendance.domain.Attendance;
import ee.ttu.vk.attendance.domain.Student;
import ee.ttu.vk.attendance.domain.Timetable;
import ee.ttu.vk.attendance.enums.Status;
import ee.ttu.vk.attendance.repository.AttendanceRepository;
import ee.ttu.vk.attendance.service.AttendanceService;
import org.springframework.data.domain.Pageable;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Created by strukov on 3/24/16.
 */
public class AttendanceServiceImpl implements AttendanceService {

    @Inject
    private AttendanceRepository attendanceRepository;

    @Override
    public List<Attendance> findAll(Attendance attendance, Pageable pageable) {
        return attendanceRepository.findAll(attendance.getTimetable(), attendance.getTimetable().getSubject(), attendance.getTimetable().getGroup(),  pageable).getContent();
    }

    @Override
    public Attendance findByTimetable(Timetable timetable) {
        return attendanceRepository.find(timetable);
    }

    @Override
    public Attendance save(Attendance attendance) {
        
        return attendanceRepository.save(attendance);
    }

    @Override
    public List<Attendance> save(List<Attendance> attendances) {
        return null;
    }

    @Override
    public long size(Attendance attendance) {
        return attendanceRepository.size(attendance.getTimetable(),  attendance.getTimetable().getGroup(), attendance.getTimetable().getSubject());
    }

    @Override
    public int getPresentsNumber(Student student) {
        return Optional.ofNullable(attendanceRepository.countByStatus(student,  Status.PRESENT)).orElse(0);
    }

    @Override
    public int getAbsentsNumber(Student student) {
        return Optional.ofNullable(attendanceRepository.countByStatus(student, Status.ABSENT)).orElse(0);
    }
}

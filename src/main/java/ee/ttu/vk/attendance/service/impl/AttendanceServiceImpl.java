package ee.ttu.vk.attendance.service.impl;

import com.google.common.collect.Lists;
import ee.ttu.vk.attendance.domain.Attendance;
import ee.ttu.vk.attendance.domain.Group;
import ee.ttu.vk.attendance.domain.Student;
import ee.ttu.vk.attendance.domain.Timetable;
import ee.ttu.vk.attendance.enums.Status;
import ee.ttu.vk.attendance.repository.AttendanceRepository;
import ee.ttu.vk.attendance.service.AttendanceService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by strukov on 3/24/16.
 */
@Service
@Transactional
public class AttendanceServiceImpl implements AttendanceService {

    @Inject
    private AttendanceRepository attendanceRepository;

    @Override
    public void generateAttendance(Group group, List<Timetable> timetables) {
        List<Attendance> attendanceList = Lists.newArrayList();

        for (Student student : group.getStudents()) {
            attendanceList.addAll(timetables.stream().map(timetable -> new Attendance().setStudent(student).setTimetable(timetable)).collect(Collectors.toList()));
        }
        attendanceRepository.save(attendanceList);
    }

    @Override
    public List<Attendance> findAll(Attendance attendance, Pageable pageable) {
        return attendanceRepository.findAll();
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
        Long size = attendanceRepository.size(attendance.getTimetable());
        return Optional.ofNullable(size).orElse(0L);
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

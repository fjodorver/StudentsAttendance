package ee.ttu.vk.attendance.service.impl;

import com.google.common.collect.Lists;
import ee.ttu.vk.attendance.domain.Attendance;
import ee.ttu.vk.attendance.domain.Group;
import ee.ttu.vk.attendance.domain.Student;
import ee.ttu.vk.attendance.domain.Timetable;
import ee.ttu.vk.attendance.enums.Status;
import ee.ttu.vk.attendance.repository.AttendanceRepository;
import ee.ttu.vk.attendance.repository.GroupRepository;
import ee.ttu.vk.attendance.repository.StudentRepository;
import ee.ttu.vk.attendance.repository.TimetableRepository;
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

    @Inject
    private StudentRepository studentRepository;

    @Inject
    private TimetableRepository timetableRepository;


    @Override
    public Attendance save(Attendance attendance) {
        return attendanceRepository.save(attendance);
    }

    @Override
    public List<Attendance> save(List<Attendance> attendances) {
        return null;
    }

    public List<Attendance> findAll(Attendance filter, Pageable pageable) {
        return attendanceRepository.findByTimetable(filter.getTimetable());
    }

    @Override
    public long size(Attendance attendance) {
        return Optional.ofNullable(attendanceRepository.size(attendance.getTimetable())).orElse(0L);
    }

    @Override
    public void GenerateAndSaveAttendances(Group group) {
        List<Attendance> attendanceList = Lists.newArrayList();
        studentRepository.findByGroup(group)
                .forEach(x -> attendanceList.addAll(timetableRepository.findByGroup(group).stream()
                        .map(y -> new Attendance().setStudent(x).setTimetable(y))
                        .collect(Collectors.toList())));
        attendanceRepository.save(attendanceList);
    }

    @Override
    public int getPresentsNumber(Student student) {
        return Optional.ofNullable(attendanceRepository.countByStatus(student,  Status.PRESENT)).orElse(0);
    }

    @Override
    public int getAbsentsNumber(Student student) {
        return Optional.ofNullable(attendanceRepository.countByStatus(student, Status.ABSENT)).orElse(0);
    }

    @Override
    public List<Attendance> findAll() {
        return attendanceRepository.findAll();
    }

    @Override
    public List<Attendance> findAll(Pageable pageable) {
        return attendanceRepository.findAll(pageable).getContent();
    }
}

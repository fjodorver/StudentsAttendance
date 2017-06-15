package ee.ttu.vk.attendance.service.impl;

import com.google.common.collect.Lists;
import ee.ttu.vk.attendance.domain.Attendance;
import ee.ttu.vk.attendance.domain.Programme;
import ee.ttu.vk.attendance.domain.Student;
import ee.ttu.vk.attendance.domain.Timetable;
import ee.ttu.vk.attendance.enums.Status;
import ee.ttu.vk.attendance.repository.AttendanceRepository;
import ee.ttu.vk.attendance.repository.StudentRepository;
import ee.ttu.vk.attendance.repository.TimetableRepository;
import ee.ttu.vk.attendance.service.AttendanceService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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
        return attendanceRepository.findByTimetable(filter.getTimetable(), pageable);
    }

    @Override
    public long size(Attendance attendance) {
        return Optional.ofNullable(attendanceRepository.size(attendance.getTimetable())).orElse(0L);
    }

    @Override
    public void generateAndSaveAttendances(Programme programme) {
        List<Student> students = studentRepository.findAllByProgramme(programme);
        Map<Student, List<Attendance>> attendanceMap = students.stream()
                .map(x -> attendanceRepository.findByStudent(x))
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(Attendance::getStudent));
        for (Student student : students) {
            List<Attendance> attendances = timetableRepository.findByProgramme(programme).stream()
                    .map(timetable -> new Attendance(student, timetable))
                    .collect(Collectors.toList());
            attendanceMap.putIfAbsent(student, attendances);
        }
        attendanceRepository.save(attendanceMap.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList()));
    }

    @Override
    public void clearAll() {
        attendanceRepository.deleteAllInBatch();
    }

    @Override
    public List<Attendance> findAll(Attendance attendance) {
        return attendanceRepository.findAll(attendance.getStudent(), attendance.getTimetable().getSubject());
    }
}

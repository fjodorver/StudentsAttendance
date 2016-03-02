package ee.ttu.vk.sa.service.impl;

import ee.ttu.vk.sa.domain.Attendance;
import ee.ttu.vk.sa.domain.Group;
import ee.ttu.vk.sa.domain.Student;
import ee.ttu.vk.sa.domain.Subject;
import ee.ttu.vk.sa.enums.Status;
import ee.ttu.vk.sa.repository.AttendanceRepository;
import ee.ttu.vk.sa.service.AttendanceService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by fjodor on 14.02.16.
 */
@Service
@Transactional
public class AttendanceServiceImpl implements AttendanceService {

    @Inject
    private AttendanceRepository attendanceRepository;

    @Override
    public List<Attendance> findAll(Attendance attendance, Pageable pageable) {
        return attendanceRepository.findAllBySubjectAndGroupAndTypeAndDate(attendance.getSubject(), attendance.getGroup(), attendance.getType(), attendance.getDate(), pageable).getContent();
    }

    @Override
    public Attendance save(Attendance attendance) {
        return attendanceRepository.save(attendance);
    }

    @Override
    public void save(List<Attendance> attendances) {
        attendances = attendances.stream()
                .map(x -> Optional.ofNullable(attendanceRepository.findByStudentAndDateAndType(x.getStudent(), x.getDate(), x.getType())).orElse(x))
                .collect(Collectors.toList());
        attendanceRepository.save(attendances);
    }

    @Override
    public int getSize() {
        return (int) attendanceRepository.count();
    }

    @Override
    public double getAttendance(Subject subject, Group group, Student student) {
        return Optional.ofNullable(attendanceRepository.findResult(subject, group, student, Status.PRESENT)).orElse(0);
    }
}

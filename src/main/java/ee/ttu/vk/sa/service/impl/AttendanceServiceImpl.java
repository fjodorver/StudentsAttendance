package ee.ttu.vk.sa.service.impl;

import ee.ttu.vk.sa.domain.Attendance;
import ee.ttu.vk.sa.domain.Group;
import ee.ttu.vk.sa.domain.Subject;
import ee.ttu.vk.sa.enums.Type;
import ee.ttu.vk.sa.repository.AttendanceRepository;
import ee.ttu.vk.sa.service.AttendanceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Date;
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

    public List<Attendance> findAll(Subject subject, Group group, Type type, Date date, Pageable pageable) {
        Page<Attendance> attendancePage = attendanceRepository.findAllBySubjectAndGroupAndTypeAndDate(subject, group, type, date, pageable);
        return attendancePage.getContent();
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
}

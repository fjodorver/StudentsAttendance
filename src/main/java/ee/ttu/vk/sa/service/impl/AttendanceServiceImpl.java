package ee.ttu.vk.sa.service.impl;

import ee.ttu.vk.sa.domain.Attendance;
import ee.ttu.vk.sa.domain.Group;
import ee.ttu.vk.sa.domain.Subject;
import ee.ttu.vk.sa.repository.AttendanceRepository;
import ee.ttu.vk.sa.service.AttendanceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * Created by fjodor on 14.02.16.
 */
@Service
@Transactional
public class AttendanceServiceImpl implements AttendanceService {

    @Inject
    private AttendanceRepository attendanceRepository;

    public List<Attendance> findAll(Subject subject, Group group, Date date) {
        List<Attendance> attendances = attendanceRepository.findAllBySubjectAndGroupAndDate(subject, group, date);
        for (Attendance attendance : attendances) {
            attendance.getStudent().getId();
            attendance.getSubject().getId();
            attendance.getGroup().getId();
        }
        return attendances;
    }

    @Override
    public void save(Attendance attendance) {
        attendanceRepository.save(attendance);
    }

    @Override
    public int getSize() {
        return (int) attendanceRepository.count();
    }
}

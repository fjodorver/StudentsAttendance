package ee.ttu.vk.sa.service.impl;

import com.google.common.collect.Lists;
import ee.ttu.vk.sa.domain.Attendance;
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

    public List<Attendance> findAll(Subject subject, Date date) {
        return Lists.newArrayList(attendanceRepository.findAllBySubjectAndDate(subject, date));
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

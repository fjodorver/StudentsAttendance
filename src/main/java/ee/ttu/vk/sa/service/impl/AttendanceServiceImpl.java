package ee.ttu.vk.sa.service.impl;

import com.google.common.collect.Lists;
import ee.ttu.vk.sa.domain.Attendance;
import ee.ttu.vk.sa.repository.AttendanceRepository;
import ee.ttu.vk.sa.service.AttendanceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by fjodor on 14.02.16.
 */
@Service
@Transactional
public class AttendanceServiceImpl implements AttendanceService {

    @Inject
    private AttendanceRepository attendanceRepository;

    @Override
    public List<Attendance> findAll() {
        return Lists.newArrayList(attendanceRepository.findAll());
    }
}

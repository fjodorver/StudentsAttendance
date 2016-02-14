package ee.ttu.vk.sa.service;

import ee.ttu.vk.sa.domain.Attendance;

import java.util.List;

/**
 * Created by fjodor on 14.02.16.
 */
public interface AttendanceService {
    public List<Attendance> findAll();
}

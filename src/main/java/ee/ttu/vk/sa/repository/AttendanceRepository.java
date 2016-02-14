package ee.ttu.vk.sa.repository;

import ee.ttu.vk.sa.domain.Attendance;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by fjodor on 14.02.16.
 */
public interface AttendanceRepository extends CrudRepository<Attendance, Long> {
}

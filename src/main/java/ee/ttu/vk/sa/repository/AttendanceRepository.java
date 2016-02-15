package ee.ttu.vk.sa.repository;

import ee.ttu.vk.sa.domain.Attendance;
import ee.ttu.vk.sa.domain.Subject;
import ee.ttu.vk.sa.domain.Teacher;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

/**
 * Created by fjodor on 14.02.16.
 */
public interface AttendanceRepository extends CrudRepository<Attendance, Long> {
    List<Attendance> findAllBySubjectAndDate(Subject subject, @Temporal(TemporalType.DATE) Date date);
}

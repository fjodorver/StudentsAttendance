package ee.ttu.vk.sa.repository;

import ee.ttu.vk.sa.domain.*;
import ee.ttu.vk.sa.enums.Type;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

/**
 * Created by fjodor on 14.02.16.
 */
public interface AttendanceRepository extends CrudRepository<Attendance, Long> {
    List<Attendance> findAllBySubjectAndGroupAndTypeAndDate(Subject subject, Group group,Type type, @Temporal(TemporalType.DATE) Date date);
    Attendance findByStudentAndDateAndType(Student student, @Temporal(TemporalType.DATE) Date date, Type type);
}

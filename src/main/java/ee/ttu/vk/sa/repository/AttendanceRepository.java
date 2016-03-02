package ee.ttu.vk.sa.repository;

import ee.ttu.vk.sa.domain.*;
import ee.ttu.vk.sa.enums.Status;
import ee.ttu.vk.sa.enums.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

/**
 * Created by fjodor on 14.02.16.
 */
public interface AttendanceRepository extends CrudRepository<Attendance, Long> {

    @EntityGraph(value = "attendance.detail", type = EntityGraph.EntityGraphType.LOAD)
    Page<Attendance> findAllBySubjectAndGroupAndTypeAndDate(Subject subject, Group group, Type type, @Temporal(TemporalType.DATE) Date date, Pageable pageable);
    Attendance findByStudentAndDateAndType(Student student, @Temporal(TemporalType.DATE) Date date, Type type);

    @Query("select count(a) from Attendance a where a.subject = ?1 and a.group = ?2 and a.student = ?3 and a.status = ?4 group by a.status")
    Integer findResult(Subject subject, Group group, Student student, Status status);
}

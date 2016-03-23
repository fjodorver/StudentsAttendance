package ee.ttu.vk.sa.repository;

import ee.ttu.vk.sa.domain.Attendance;
import ee.ttu.vk.sa.domain.Group;
import ee.ttu.vk.sa.domain.Student;
import ee.ttu.vk.sa.domain.Subject;
import ee.ttu.vk.sa.enums.Status;
import ee.ttu.vk.sa.enums.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;

import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created by fjodor on 14.02.16.
 */
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    @EntityGraph(value = "attendance.detail", type = EntityGraph.EntityGraphType.LOAD)
    @Query("select a from Attendance a where a.subject = ?1 and a.group = ?2 and a.type = ?3")
    Page<Attendance> findAll(Subject subject, Group group, Type type, Pageable pageable);

    @Query("select a from Attendance a where a.student = ?1 and a.type = ?2 ")
    Attendance findAll(Student student, Type type);


    @Query("select count(a) from Attendance a where a.subject = ?1 and a.group = ?2 and a.student = ?3 and a.status = ?4 group by a.status")
    Integer countByStatus(Subject subject, Group group, Student student, Status status);

    @Query("select count(a) from Attendance a where a.subject = ?1 and a.group = ?2 and a.type = ?3")
    Long size(Subject subject, Group group, Type type);
}

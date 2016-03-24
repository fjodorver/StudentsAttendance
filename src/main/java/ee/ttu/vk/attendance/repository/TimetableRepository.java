package ee.ttu.vk.attendance.repository;


import ee.ttu.vk.attendance.domain.Group;
import ee.ttu.vk.attendance.domain.Subject;
import ee.ttu.vk.attendance.domain.Teacher;
import ee.ttu.vk.attendance.domain.Timetable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface TimetableRepository extends JpaRepository<Timetable, Long> {

    @EntityGraph(value = "timetable.detail", type = EntityGraph.EntityGraphType.LOAD)
    @Query("select t from Timetable t where t.group = ?1 and t.subject = ?2 and t.teacher = ?3 and t.start = ?4 and t.end = ?5")
    Timetable find(Group group, Subject subject, Teacher teacher, ZonedDateTime start, ZonedDateTime end);

    List<Timetable> findByGroup(Group group);

    @EntityGraph(value = "timetable.detail", type = EntityGraph.EntityGraphType.LOAD)
    @Query("select t from Timetable t where t.start between ?1 and ?2")
    List<Timetable> find(ZonedDateTime start, ZonedDateTime end, Pageable pageable);

    @Query("select count(t.id) from Timetable t where t.start between ?1 and ?2")
    Long count(ZonedDateTime start, ZonedDateTime end);
}

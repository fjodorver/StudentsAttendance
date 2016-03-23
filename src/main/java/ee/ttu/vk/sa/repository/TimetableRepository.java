package ee.ttu.vk.sa.repository;


import ee.ttu.vk.sa.domain.Group;
import ee.ttu.vk.sa.domain.Subject;
import ee.ttu.vk.sa.domain.Teacher;
import ee.ttu.vk.sa.domain.Timetable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;

@Repository
public interface TimetableRepository extends JpaRepository<Timetable, Long> {

    @EntityGraph(value = "timetable.detail", type = EntityGraph.EntityGraphType.LOAD)
    @Query("select t from Timetable t where t.group = ?1 and t.subject = ?2 and t.teacher = ?3 and t.start = ?4 and t.end = ?5")
    Timetable find(Group group, Subject subject, Teacher teacher, ZonedDateTime start, ZonedDateTime end);
}

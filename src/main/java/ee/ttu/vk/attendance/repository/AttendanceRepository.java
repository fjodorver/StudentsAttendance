package ee.ttu.vk.attendance.repository;

import ee.ttu.vk.attendance.domain.*;
import ee.ttu.vk.attendance.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by strukov on 3/24/16.
 */
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    @EntityGraph(value = "attendance.detail", type = EntityGraph.EntityGraphType.LOAD)
    @Query("select a from Attendance a where a.timetable = ?1 and a.timetable.subject =?2 and a.timetable.group =?3")
    Page<Attendance> findAll(Timetable timetable, Subject subject, Group group, Pageable pageable);

    @Query("select count(a) from Attendance a where a.timetable =?1")
    Attendance find(Timetable timetable);

    @Query("select count(a) from Attendance a where a.student =?1 and a.attendanceType = ?2  group by a.attendanceType")
    Integer countByStatus(Student student, Status status);

    @Query("select count(a) from Attendance a where a.timetable = ?1 and a.timetable.group =?2 and a.timetable.subject =?3")
    Long size(Timetable timetable, Group group, Subject subject);
}

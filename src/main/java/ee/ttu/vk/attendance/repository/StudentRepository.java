package ee.ttu.vk.attendance.repository;

import ee.ttu.vk.attendance.domain.Programme;
import ee.ttu.vk.attendance.domain.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

/**
 * Created by strukov on 3/13/16.
 */
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findAllByProgrammeIn(Collection<Programme> programme);

    @Override
    @EntityGraph(value = "student.all", type = EntityGraph.EntityGraphType.LOAD)
    Page<Student> findAll(Pageable pageable);

    @Override
    @EntityGraph(value = "student.all", type = EntityGraph.EntityGraphType.LOAD)
    List<Student> findAll();

    @Query("select s from Student s where s.code like concat('%',?1,'%') and s.fullname like concat('%',?2,'%') and s.programme.name like concat('%',?3,'%')")
    @EntityGraph(value = "student.all", type = EntityGraph.EntityGraphType.LOAD)
    Page<Student> findAll(String code, String fullname, String programme, Pageable pageable);

    Student findByCode(String code);

    @Query("select count(s) from Student s where s.code like concat('%',?1,'%') and s.fullname like concat('%',?2,'%') and s.programme.name like concat('%',?3,'%')")
    long count(String code, String fullname, String programme);
}

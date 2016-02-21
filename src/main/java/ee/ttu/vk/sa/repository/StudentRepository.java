package ee.ttu.vk.sa.repository;

import ee.ttu.vk.sa.domain.Group;
import ee.ttu.vk.sa.domain.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @EntityGraph(value = "student.group", type = EntityGraph.EntityGraphType.LOAD)
    Page<Student> findAll(Pageable pageable);
    @EntityGraph(value = "student.group", type = EntityGraph.EntityGraphType.LOAD)
    Page<Student> findAllByLastnameContainingIgnoreCase(Pageable pageable, String lastname);
    List<Student> findAllByGroup(Group group);
    Student findByCode(String code);
}

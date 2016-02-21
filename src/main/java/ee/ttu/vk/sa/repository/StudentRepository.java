package ee.ttu.vk.sa.repository;

import ee.ttu.vk.sa.domain.Group;
import ee.ttu.vk.sa.domain.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findAllByGroupIn(Collection<Group> group);

    @Override
    @EntityGraph(value = "student.group", type = EntityGraph.EntityGraphType.LOAD)
    Page<Student> findAll(Pageable pageable);

    @Query("select s from Student s where concat(s.firstname, s.lastname) like ?1")
    @EntityGraph(value = "student.group", type = EntityGraph.EntityGraphType.LOAD)
    Page<Student> findAll(String fullname, Pageable pageable);

    Student findByCode(String code);
}

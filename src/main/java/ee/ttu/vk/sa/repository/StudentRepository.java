package ee.ttu.vk.sa.repository;

import ee.ttu.vk.sa.domain.Group;
import ee.ttu.vk.sa.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findAllByGroup(Group group);
    Student findByCode(String code);
}

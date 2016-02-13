package ee.ttu.vk.sa.repository;


import ee.ttu.vk.sa.domain.Subject;
import ee.ttu.vk.sa.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
        Subject findByCode(String code);
        List<Subject> findAllByTeacher(Teacher teacher);
}

package ee.ttu.vk.sa.repository;


import ee.ttu.vk.sa.domain.Subject;
import ee.ttu.vk.sa.domain.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
        Page<Subject> findAllByCode(Pageable pageable, String code);
        Subject findByCode(String code);
        List<Subject> findAllByTeacher(Teacher teacher);
}

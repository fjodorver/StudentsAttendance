package ee.ttu.vk.sa.repository;


import ee.ttu.vk.sa.domain.Subject;
import ee.ttu.vk.sa.domain.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
        Page<Subject> findAllByCode(Pageable pageable, String code);
        Subject findByCode(String code);
        List<Subject> findAllByTeacher(Teacher teacher);

        @Query("select s from Subject s where s.code like concat('%',?1,'%') and s.name like concat('%',?2,'%')")
        Page<Subject> findAll(String code, String name, Pageable pageable);

        @Query("select count(s.id) from Subject s where s.code like concat('%',?1,'%') and s.name like concat('%',?2,'%')")
        long count(String code, String name);
}

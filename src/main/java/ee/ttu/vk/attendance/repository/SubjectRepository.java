package ee.ttu.vk.attendance.repository;

import ee.ttu.vk.attendance.domain.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by fjodor on 4.03.16.
 */
@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Subject findByCode(String code);

    @Query("select s from Subject s where s.code like concat('%',?1,'%') and s.name like concat('%',?2,'%')")
    Page<Subject> findAll(String code, String name, Pageable pageable);

    @Query("select count(s.id) from Subject s where s.code like concat('%',?1,'%') and s.name like concat('%',?2,'%')")
    long count(String code, String name);
}

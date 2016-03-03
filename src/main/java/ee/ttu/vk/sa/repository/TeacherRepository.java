package ee.ttu.vk.sa.repository;

import ee.ttu.vk.sa.domain.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by vadimstrukov on 2/13/16.
 */
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Teacher findByEmail(String email);

    @Query("select t from Teacher t where t.email like concat('%',?1,'%') and t.name like concat('%',?2,'%')")
    Page<Teacher> findAll(String email, String name, Pageable pageable);

    @Query("select count(t) from Teacher t where t.email like concat('%',?1,'%') and t.name like concat('%',?2,'%')")
    long count(String email, String name);
}

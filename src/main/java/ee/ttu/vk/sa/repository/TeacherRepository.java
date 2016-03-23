package ee.ttu.vk.sa.repository;


import ee.ttu.vk.sa.domain.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Teacher findByUsername(String username);

    @Query("select t from Teacher t where t.username like concat('%',?1,'%') and t.fullname like concat('%',?2,'%')")
    Page<Teacher> findAll(String username, String fullname, Pageable pageable);

    @Query("select count(t) from Teacher t where t.username like concat('%',?1,'%') and t.fullname like concat('%',?2,'%')")
    long count(String username, String fullname);
}

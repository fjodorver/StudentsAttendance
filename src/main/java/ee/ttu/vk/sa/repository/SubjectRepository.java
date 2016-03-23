package ee.ttu.vk.sa.repository;

import ee.ttu.vk.sa.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by fjodor on 4.03.16.
 */
@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Subject findByCode(String code);
}

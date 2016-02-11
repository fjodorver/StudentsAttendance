package ee.ttu.vk.sa.repository;


import ee.ttu.vk.sa.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
        Subject findByCode(String code);
}

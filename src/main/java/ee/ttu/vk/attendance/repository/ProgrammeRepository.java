package ee.ttu.vk.attendance.repository;

import ee.ttu.vk.attendance.domain.Programme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgrammeRepository extends JpaRepository<Programme, Long> {
    Programme findByName(String name);
}

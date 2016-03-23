package ee.ttu.vk.attendance.repository;

import ee.ttu.vk.attendance.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    Group findByName(String name);
}

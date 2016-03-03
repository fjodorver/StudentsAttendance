package ee.ttu.vk.sa.service;

import ee.ttu.vk.sa.domain.Group;
import ee.ttu.vk.sa.domain.Student;
import ee.ttu.vk.sa.domain.Subject;

import java.util.List;

/**
 * Created by fjodor on 6.02.16.
 */
public interface GroupService {
    List<Group> findAll();
    List<Group> findAll(List<Subject> subjects);
    void save(List<Group> groups);
}

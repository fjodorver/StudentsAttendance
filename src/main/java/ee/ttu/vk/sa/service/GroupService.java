package ee.ttu.vk.sa.service;

import ee.ttu.vk.sa.domain.Group;
import ee.ttu.vk.sa.domain.Student;

import java.util.List;

/**
 * Created by fjodor on 6.02.16.
 */
public interface GroupService {
    void addGroups(List<Group> groups);
}

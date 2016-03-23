package ee.ttu.vk.sa.service;


import ee.ttu.vk.sa.domain.Group;

import java.util.List;

/**
 * Created by fjodor on 4.03.16.
 */
public interface GroupService {
    List<Group> save(List<Group> groups);
    List<Group> findAll();
}
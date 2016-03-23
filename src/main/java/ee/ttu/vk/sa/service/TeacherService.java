package ee.ttu.vk.sa.service;


import ee.ttu.vk.sa.domain.Teacher;

import java.util.List;

/**
 * Created by fjodor on 5.03.16.
 */
public interface TeacherService {
    List<Teacher> findAll();
    Teacher findByFullname(String fullname);
    List<Teacher> saveAll(List<Teacher> teachers);
}

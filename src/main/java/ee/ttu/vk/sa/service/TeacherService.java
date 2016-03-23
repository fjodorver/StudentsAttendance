package ee.ttu.vk.sa.service;


import ee.ttu.vk.sa.domain.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by fjodor on 5.03.16.
 */
public interface TeacherService {
    Teacher save(Teacher teacher);
    Teacher find(String username, String password);
    long size(Teacher teacher);
    List<Teacher> findAll(Teacher teacher, Pageable pageable);
    List<Teacher> findAll();
    Teacher findByFullname(String fullname);
    List<Teacher> saveAll(List<Teacher> teachers);
}

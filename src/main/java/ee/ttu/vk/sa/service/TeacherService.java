package ee.ttu.vk.sa.service;

import ee.ttu.vk.sa.domain.Teacher;
import org.springframework.data.domain.Pageable;

import java.io.InputStream;
import java.util.List;

/**
 * Created by vadimstrukov on 2/13/16.
 */
public interface TeacherService  {
    List<Teacher> findAll(Teacher teacher, Pageable pageable);
    Teacher find(String email, String password);
    Teacher save(Teacher teacher);
    List<Teacher> save(List<Teacher> teachers);
    List<Teacher> parse(InputStream inputStream);
    long getSize(Teacher teacher);
}

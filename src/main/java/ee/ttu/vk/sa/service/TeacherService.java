package ee.ttu.vk.sa.service;

import ee.ttu.vk.sa.domain.Student;
import ee.ttu.vk.sa.domain.Subject;
import ee.ttu.vk.sa.domain.Teacher;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by vadimstrukov on 2/13/16.
 */
public interface TeacherService  {

    void addTeacher(Teacher teacher);
    void deleteTeacher(Teacher teacher);
    List<Subject> setSubjects(Teacher teacher);
    List<Teacher> findAll();
    Page<Teacher> findAll(int page, int size, String name);
    Page<Teacher> findAllTeachers(Integer page, Integer size);
    int getSize();
}

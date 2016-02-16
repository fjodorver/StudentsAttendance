package ee.ttu.vk.sa.service;

import ee.ttu.vk.sa.domain.Group;
import ee.ttu.vk.sa.domain.Student;

import java.util.List;

/**
 * Created by fjodor on 6.02.16.
 */
public interface StudentService {
    void addStudent(Student student);
    void deleteStudent(Student student);
    List<Student> findAll();
    List<Student> findAllStudents(Group group);
    List<Student> saveStudents(List<Student> students);
}

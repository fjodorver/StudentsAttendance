package ee.ttu.vk.sa.service;

import ee.ttu.vk.sa.domain.Group;
import ee.ttu.vk.sa.domain.Student;
import org.springframework.data.domain.Page;

import java.io.InputStream;
import java.util.List;

/**
 * Created by fjodor on 6.02.16.
 */
public interface StudentService {
    void addStudent(Student student);
    void deleteStudent(Student student);
    Page<Student> findAll(int page, int size, String lastname);
    Page<Student> findAllStudents(Integer page, Integer size);
    int getSize();
	List<Student> parseStudents(InputStream stream);
    List<Student> findAllStudents(Group group);
    List<Student> saveStudents(List<Student> students);
}

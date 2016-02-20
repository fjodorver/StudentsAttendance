package ee.ttu.vk.sa.service;

import ee.ttu.vk.sa.domain.Group;
import ee.ttu.vk.sa.domain.Student;
import ee.ttu.vk.sa.domain.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.InputStream;
import java.util.List;

/**
 * Created by fjodor on 6.02.16.
 */
public interface StudentService {
    Page<Student> findAllByLastname(Pageable pageable, String lastname);
    Page<Student> findAll(Pageable pageable);
    void addStudent(Student student);
    void deleteStudent(Student student);
    int getSize();
	List<Student> parseStudents(InputStream stream);
    void saveStudents(List<Student> students);

    List<Student> findAllBySubject(Subject subject);
}

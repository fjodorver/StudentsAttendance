package ee.ttu.vk.sa.service;

import java.io.InputStream;
import java.util.List;

import ee.ttu.vk.sa.domain.Group;
import ee.ttu.vk.sa.domain.Student;

/**
 * Created by fjodor on 6.02.16.
 */
public interface StudentService {
	List<Student> parseStudents(InputStream stream);
    List<Student> findAllStudents(Group group);
    List<Student> saveStudents(List<Student> students);
}

package ee.ttu.vk.sa.service;

import ee.ttu.vk.sa.domain.Group;
import ee.ttu.vk.sa.domain.Student;
import ee.ttu.vk.sa.domain.Subject;
import org.springframework.data.domain.Pageable;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

/**
 * Created by fjodor on 6.02.16.
 */
public interface StudentService {
    List<Student> parse(InputStream inputStream);
    Iterator<Student> findAll(Subject subject);
    Iterator<Student> findAll(List<Group> groups, Pageable pageable);
    Iterator<Student> findAll(Student student, Pageable pageable);
    Iterator<Student> save(List<Student> students);
    Student save(Student student);
    long size(Student student);
}
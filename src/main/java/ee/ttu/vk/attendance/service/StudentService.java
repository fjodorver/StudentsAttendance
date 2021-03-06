package ee.ttu.vk.attendance.service;

import ee.ttu.vk.attendance.domain.Programme;
import ee.ttu.vk.attendance.domain.Student;
import ee.ttu.vk.attendance.domain.Subject;
import org.springframework.data.domain.Pageable;

import java.io.InputStream;
import java.util.List;

/**
 * Created by fjodor on 6.02.16.
 */
public interface StudentService extends IReadService<Student>, ISaveService<Student>, IProviderService<Student, Student> {
    List<Student> parse(InputStream inputStream);
    List<Student> findAll(Subject subject);
    List<Student> findAll(Programme programme);
    List<Student> findAll(List<Programme> programmes, Pageable pageable);
    void clearAll();
}
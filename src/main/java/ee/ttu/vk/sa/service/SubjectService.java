package ee.ttu.vk.sa.service;


import ee.ttu.vk.sa.domain.Subject;
import ee.ttu.vk.sa.domain.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.InputStream;
import java.util.List;

public interface SubjectService {
    List<Subject> findAll(Subject subject, Pageable pageable);
    List<Subject> findAll(Teacher teacher);
    List<Subject> save(List<Subject> subjects);
	List<Subject> parse(InputStream stream);
    long getSize(Subject subject);
}

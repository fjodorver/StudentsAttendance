package ee.ttu.vk.sa.service;


import ee.ttu.vk.sa.domain.Subject;
import ee.ttu.vk.sa.domain.Teacher;
import org.springframework.data.domain.Page;

import java.io.InputStream;
import java.util.List;

public interface SubjectService {
    void deleteSubject(Subject subject);
    void addSubject(Subject subject);
    List<Subject> findAll();
    List<Subject> saveSubjects(List<Subject> subjects);

    List<Subject> findAllByTeacher(Teacher teacher);
	List<Subject> parseSubjects(InputStream stream);
    Page<Subject> findAll(int page, int size, String code);
    Page<Subject> findAllSubjects(Integer page, Integer size);
    int getSize();
}

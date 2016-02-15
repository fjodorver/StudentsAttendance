package ee.ttu.vk.sa.service;


import java.io.InputStream;
import ee.ttu.vk.sa.domain.Teacher;
import java.util.List;

import ee.ttu.vk.sa.domain.Subject;

public interface SubjectService {
    List<Subject> saveSubjects(List<Subject> subjects);

    List<Subject> findAllByTeacher(Teacher teacher);
	List<Subject> parseSubjects(InputStream stream);
}

package ee.ttu.vk.sa.service;


import ee.ttu.vk.sa.domain.Subject;

import java.util.List;

public interface SubjectService {
    List<Subject> saveSubjects(List<Subject> subjects);
}

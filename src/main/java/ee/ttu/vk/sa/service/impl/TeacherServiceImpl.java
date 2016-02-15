package ee.ttu.vk.sa.service.impl;

import com.google.common.collect.Lists;
import ee.ttu.vk.sa.domain.Subject;
import ee.ttu.vk.sa.domain.Teacher;
import ee.ttu.vk.sa.repository.SubjectRepository;
import ee.ttu.vk.sa.repository.TeacherRepository;
import ee.ttu.vk.sa.service.TeacherService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by vadimstrukov on 2/13/16.
 */
@Service
@Transactional
public class TeacherServiceImpl implements TeacherService {

    @Inject
    private TeacherRepository teacherRepository;

    @Inject
    private SubjectRepository subjectRepository;

    @Override
    public void addTeacher(Teacher teacher) {
        teacherRepository.save(teacher);
    }

    @Override
    public void deleteTeacher(Teacher teacher) {
        teacherRepository.delete(teacher);
    }

    @Override
    public List<Subject> setSubjects(Teacher teacher) {
        return subjectRepository.findAllByTeacher(teacher);
    }

    @Override
    public List<Teacher> findAll() {
        return Lists.newArrayList(teacherRepository.findAll());
    }
}

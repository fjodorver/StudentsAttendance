package ee.ttu.vk.sa.service.impl;

import com.google.common.collect.Lists;
import ee.ttu.vk.sa.domain.Student;
import ee.ttu.vk.sa.domain.Subject;
import ee.ttu.vk.sa.domain.Teacher;
import ee.ttu.vk.sa.repository.SubjectRepository;
import ee.ttu.vk.sa.repository.TeacherRepository;
import ee.ttu.vk.sa.service.TeacherService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Override
    public Page<Teacher> findAll(int page, int size, String name) {
        Pageable pageable = new PageRequest(page, size, new Sort(Sort.Direction.ASC, "name"));
        return teacherRepository.findAllByName(pageable, name);
    }

    @Override
    public Page<Teacher> findAllTeachers(Integer page, Integer size) {
        return teacherRepository.findAll(new PageRequest(page, size, new Sort(Sort.Direction.ASC, "name")));
    }

    @Override
    public int getSize() {
        return (int)teacherRepository.count();
    }
}

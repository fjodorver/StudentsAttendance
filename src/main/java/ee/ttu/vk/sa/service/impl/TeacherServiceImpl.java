package ee.ttu.vk.sa.service.impl;


import ee.ttu.vk.sa.domain.Teacher;
import ee.ttu.vk.sa.repository.TeacherRepository;
import ee.ttu.vk.sa.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by fjodor on 7.03.16.
 */
@Service
@Transactional
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    @Override
    public Teacher findByFullname(String fullname) {
        return null;
    }

    @Override
    public List<Teacher> saveAll(List<Teacher> teachers) {
        Map<String, Teacher> teacherMap = teacherRepository.findAll().stream().collect(Collectors.toMap(Teacher::getUsername, x -> x, (x, y) -> x));
        teachers.stream().forEach(x -> x.setId(Optional.ofNullable(teacherMap.get(x.getUsername())).orElse(x).getId()));
        return teacherRepository.save(teachers);
    }
}

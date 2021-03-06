package ee.ttu.vk.attendance.service.impl;


import ee.ttu.vk.attendance.domain.Teacher;
import ee.ttu.vk.attendance.repository.TeacherRepository;
import ee.ttu.vk.attendance.service.TeacherService;
import ee.ttu.vk.attendance.utils.PasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
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

    @Inject
    private PasswordEncryptor encryptor;

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public Teacher save(Teacher teacher) {
        teacher.setPassword(encryptor.encryptPassword(teacher.getPassword()));
        return teacherRepository.save(teacher);
    }

    @Override
    public List<Teacher> save(List<Teacher> teachers) {
        Map<String, Teacher> teacherMap = teacherRepository.findAll().stream().collect(Collectors.toMap(Teacher::getUsername, x -> x, (x, y) -> x));
        teachers.stream().forEach(x -> {
            x.setId(Optional.ofNullable(teacherMap.get(x.getUsername())).orElse(x).getId());
            x.setPassword(encryptor.encryptPassword(x.getPassword()));
        });
        return teacherRepository.save(teachers);
    }

    public Teacher find(String username, String password) {
        Teacher teacher = teacherRepository.findByUsername(username);
        if(teacher == null) return null;
        if(encryptor.decryptPassword(teacher.getPassword()).equals(password)) return teacher;
        return null;
    }

    public List<Teacher> findAll(Teacher filter, Pageable pageable) {
        String username = Optional.ofNullable(filter.getUsername()).orElse("");
        String fullname = Optional.ofNullable(filter.getFullname()).orElse("");
        return teacherRepository.findAll(username, fullname, pageable).getContent();
    }

    @Override
    public long size(Teacher teacher) {
        String username = Optional.ofNullable(teacher.getUsername()).orElse("");
        String fullname = Optional.ofNullable(teacher.getFullname()).orElse("");
        return teacherRepository.count(username, fullname);
    }

    @Override
    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    @Override
    public List<Teacher> findAll(Pageable pageable) {
        return teacherRepository.findAll(pageable).getContent();
    }
}

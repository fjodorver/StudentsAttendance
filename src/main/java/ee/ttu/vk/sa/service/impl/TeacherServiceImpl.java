package ee.ttu.vk.sa.service.impl;

import com.google.common.collect.Lists;
import ee.ttu.vk.sa.domain.Teacher;
import ee.ttu.vk.sa.repository.SubjectRepository;
import ee.ttu.vk.sa.repository.TeacherRepository;
import ee.ttu.vk.sa.service.TeacherService;
import ee.ttu.vk.sa.utils.IParser;
import ee.ttu.vk.sa.utils.PasswordEncryptor;
import ee.ttu.vk.sa.utils.TeacherXlsParser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

/**
 * Created by vadimstrukov on 2/13/16.
 */
@Service
@Transactional
public class TeacherServiceImpl implements TeacherService {

    @Inject
    private PasswordEncryptor encryptor;

    @Inject
    private TeacherRepository teacherRepository;

    @Override
    public List<Teacher> findAll(Teacher teacher, Pageable pageable) {
        String email = Optional.ofNullable(teacher.getEmail()).orElse("");
        String name = Optional.ofNullable(teacher.getName()).orElse("");
        return teacherRepository.findAll(email, name, pageable).getContent();
    }

    @Override
    public Teacher find(String email, String password) {
        Teacher teacher = teacherRepository.findByEmail(email);
        if(teacher != null && encryptor.decryptPassword(teacher.getPassword()).equals(password))
            return teacher;
        return null;
    }

    @Override
    public Teacher save(Teacher teacher) {
        teacher.setPassword(encryptor.encryptPassword(teacher.getPassword()));
        return teacherRepository.save(teacher);
    }

    @Override
    public List<Teacher> save(List<Teacher> teachers) {
        for (Teacher teacher : teachers) {
            Optional.ofNullable(teacherRepository.findByEmail(teacher.getEmail())).ifPresent(x -> teacher.setId(x.getId()));
            teacher.setPassword(encryptor.encryptPassword(teacher.getPassword()));
        }
        return teacherRepository.save(teachers);
    }

    @Override
    public List<Teacher> parse(InputStream inputStream) {
        IParser<Teacher> parser = new TeacherXlsParser();
        parser.parse(inputStream);
        return parser.getElements();
    }

    @Override
    public long getSize(Teacher teacher) {
        String email = Optional.ofNullable(teacher.getEmail()).orElse("");
        String name = Optional.ofNullable(teacher.getName()).orElse("");
        return teacherRepository.count(email, name);
    }
}

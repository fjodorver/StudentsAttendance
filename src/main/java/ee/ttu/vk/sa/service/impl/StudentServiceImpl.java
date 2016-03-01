package ee.ttu.vk.sa.service.impl;

import com.google.common.collect.Lists;
import ee.ttu.vk.sa.domain.Student;
import ee.ttu.vk.sa.domain.Subject;
import ee.ttu.vk.sa.repository.GroupRepository;
import ee.ttu.vk.sa.repository.StudentRepository;
import ee.ttu.vk.sa.service.StudentService;
import ee.ttu.vk.sa.utils.DocParser;
import ee.ttu.vk.sa.utils.IParser;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

	@Inject
	private StudentRepository studentRepository;

    @Inject
    private GroupRepository groupRepository;

    @Override
    public List<Student> parse(InputStream inputStream) {
        IParser<Student> parser = new DocParser();
        parser.parse(inputStream);
        return parser.getElements();
    }

    @Override
    public Iterator<Student> findAll(Subject subject) {
        return studentRepository.findAllByGroupIn(subject.getGroups()).iterator();
    }

    @Override
    public Iterator<Student> findAll(Pageable pageable) {
        return studentRepository.findAll(pageable).iterator();
    }

    @Override
    public Iterator<Student> findAll(Student student, Pageable pageable) {
        String code = MessageFormat.format("%{0}%",Optional.ofNullable(student.getCode()).orElse(""));
        String firstname = MessageFormat.format("%{0}%",Optional.ofNullable(student.getFirstname()).orElse(""));
        String lastname = MessageFormat.format("%{0}%",Optional.ofNullable(student.getLastname()).orElse(""));
        String group = MessageFormat.format("%{0}%",Optional.ofNullable(student.getGroup().getName()).orElse(""));
        return studentRepository.findAll(code, firstname, lastname, group, pageable).iterator();
    }

    @Override
    public Iterator<Student> save(List<Student> students) {
        List<Student> studentList = Lists.newArrayList();
        for (Student student : students) {
            Optional.ofNullable(groupRepository.findByName(student.getGroup().getName())).ifPresent(student::setGroup);
            Optional.ofNullable(studentRepository.findByCode(student.getCode())).ifPresent(x -> student.setId(x.getId()));
            studentList.add(studentRepository.save(student));
        }
        return studentList.iterator();
    }


    @Override
    public Student save(Student student) {
        return save(Lists.newArrayList(student)).next();
    }

    @Override
    public void delete(Long id) {
        studentRepository.delete(id);
    }

    @Override
    public long size(Student student) {
        String code = Optional.ofNullable(student.getCode()).orElse("");
        String firstname = Optional.ofNullable(student.getFirstname()).orElse("");
        String lastname = Optional.ofNullable(student.getLastname()).orElse("");
        String group = Optional.ofNullable(student.getGroup().getName()).orElse("");
        return studentRepository.count(code, firstname, lastname, group);
    }

}

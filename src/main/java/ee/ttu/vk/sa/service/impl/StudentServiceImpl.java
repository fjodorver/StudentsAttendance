package ee.ttu.vk.sa.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
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
import java.util.*;

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
    public Iterator<Student> findAll(String fullname, Pageable pageable) {
        return studentRepository.findAll(MessageFormat.format("%{0}%", fullname), pageable).iterator();
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
    public long size() {
        return studentRepository.count();
    }

}

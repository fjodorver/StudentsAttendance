package ee.ttu.vk.sa.service.impl;

import com.google.common.collect.Lists;
import ee.ttu.vk.sa.domain.Group;
import ee.ttu.vk.sa.domain.Student;
import ee.ttu.vk.sa.domain.Subject;
import ee.ttu.vk.sa.domain.Timetable;
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
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
        List<Group> groups = Lists.newArrayList();
        groups.addAll(subject.getTimetables().stream().map(Timetable::getGroup).collect(Collectors.toList()));
        return studentRepository.findAllByGroupIn(groups).iterator();
    }

    @Override
    public Iterator<Student> findAll(List<Group> groups, Pageable pageable) {
        return studentRepository.findAllByGroupIn(groups).iterator();
    }

    @Override
    public Iterator<Student> findAll(Student student, Pageable pageable) {
        String code = Optional.ofNullable(student.getCode()).orElse("");
        String firstname = Optional.ofNullable(student.getFirstname()).orElse("");
        String lastname = Optional.ofNullable(student.getLastname()).orElse("");
        String group = Optional.ofNullable(student.getGroup().getName()).orElse("");
        return studentRepository.findAll(code, firstname, lastname, group, pageable).iterator();
    }

    @Override
    public Iterator<Student> save(List<Student> students) {
        List<Student> studentList = Lists.newArrayList();
        Map<String, Group> groupMap = groupRepository.findAll().stream().collect(Collectors.toMap(Group::getName, x -> x));

        for (Student student : students) {
                Optional.ofNullable(groupMap.get(student.getGroup().getName())).ifPresent(student::setGroup);
                Optional.ofNullable(studentRepository.findByCode(student.getCode())).ifPresent(x -> student.setId(x.getId()));
            if(student.getGroup().getId()!=null) {
                studentList.add(studentRepository.save(student));
            }
        }
        studentList.stream().filter(x->x.getGroup().getId()!=null).collect(Collectors.toList());
        return studentList.iterator();
    }


    @Override
    public Student save(Student student) {
        return save(Lists.newArrayList(student)).next();
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

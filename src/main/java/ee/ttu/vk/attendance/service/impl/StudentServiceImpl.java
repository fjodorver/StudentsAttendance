package ee.ttu.vk.attendance.service.impl;

import com.google.common.collect.Lists;
import ee.ttu.vk.attendance.domain.*;
import ee.ttu.vk.attendance.repository.ProgrammeRepository;
import ee.ttu.vk.attendance.repository.StudentRepository;
import ee.ttu.vk.attendance.service.StudentService;
import ee.ttu.vk.attendance.utils.DocParser;
import ee.ttu.vk.attendance.utils.IParser;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.InputStream;
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
    private ProgrammeRepository programmeRepository;

    @Override
    public List<Student> parse(InputStream inputStream) {
        IParser<Student> parser = new DocParser();
        parser.parse(inputStream);
        return parser.getElements();
    }

    @Override
    public List<Student> findAll(Subject subject) {
        List<Programme> programmes = Lists.newArrayList();
        programmes.addAll(subject.getTimetables().stream().map(Timetable::getProgramme).collect(Collectors.toList()));
        return studentRepository.findAllByProgrammeIn(programmes);
    }

    @Override
    public List<Student> findAll(Programme programme) {
        return studentRepository.findAllByProgramme(programme);
    }

    @Override
    public List<Student> findAll(List<Programme> programmes, Pageable pageable) {
        return studentRepository.findAllByProgrammeIn(programmes);
    }

    public List<Student> findAll(Student student, Pageable pageable) {
        String code = Optional.ofNullable(student.getCode()).orElse("");
        String fullname = Optional.ofNullable(student.getFullname()).orElse("");
        String program = Optional.ofNullable(student.getProgramme().getName()).orElse("");
        return studentRepository.findAll(code, fullname, program, pageable).getContent();
    }

    @Override
    public List<Student> save(List<Student> students) {
        Map<String, Student> studentMap = studentRepository.findAll().stream().collect(Collectors.toMap(Student::getCode, x -> x));
        Map<String, Programme> groupMap = programmeRepository.findAll().stream().collect(Collectors.toMap(Programme::getName, x -> x));
        for (Student student : students) {
            Optional.ofNullable(groupMap.get(student.getProgramme().getName())).ifPresent(student::setProgramme);
            Optional.ofNullable(studentMap.get(student.getCode())).ifPresent(x -> student.setId(x.getId()));
        }
        return studentRepository.save(students.stream().filter(x->x.getProgramme().getId()!=null).collect(Collectors.toList()));
    }


    @Override
    public Student save(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public long size(Student student) {
        String code = Optional.ofNullable(student.getCode()).orElse("");
        String fullname = Optional.ofNullable(student.getFullname()).orElse("");
        String program = Optional.ofNullable(student.getProgramme().getName()).orElse("");
        return studentRepository.count(code, fullname, program);
    }


    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public List<Student> findAll(Pageable pageable) {
        return studentRepository.findAll(pageable).getContent();
    }
}

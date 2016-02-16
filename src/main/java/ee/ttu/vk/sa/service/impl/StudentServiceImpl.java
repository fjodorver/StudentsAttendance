package ee.ttu.vk.sa.service.impl;

import ee.ttu.vk.sa.domain.Group;
import ee.ttu.vk.sa.domain.Student;
import ee.ttu.vk.sa.repository.GroupRepository;
import ee.ttu.vk.sa.repository.StudentRepository;
import ee.ttu.vk.sa.service.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {
    @Inject
    private GroupRepository groupRepository;

    @Inject
    private StudentRepository studentRepository;

    @Override
    public void addStudent(Student student) {
        studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Student student) {
        studentRepository.delete(student);
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public List<Student> findAllStudents(Group group) {
        return studentRepository.findAllByGroup(group);
    }

    @Override
    public List<Student> saveStudents(List<Student> students) {
        for (Student student : students) {
            Group group = groupRepository.findByName(student.getGroup().getName());
            if(group != null)
                student.setGroup(group);

            if(!student.equals(studentRepository.findByCode(student.getCode())))
                studentRepository.save(student);
        }
        return studentRepository.findAll();
    }

}

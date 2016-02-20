package ee.ttu.vk.sa.service.impl;

import com.google.common.collect.Lists;
import ee.ttu.vk.sa.domain.Group;
import ee.ttu.vk.sa.domain.Student;
import ee.ttu.vk.sa.domain.Subject;
import ee.ttu.vk.sa.repository.GroupRepository;
import ee.ttu.vk.sa.repository.StudentRepository;
import ee.ttu.vk.sa.service.StudentService;
import ee.ttu.vk.sa.utils.DocParser;
import ee.ttu.vk.sa.utils.IParser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {
	@Inject
	private GroupRepository groupRepository;

	@Inject
	private StudentRepository studentRepository;

    @Override
    public List<Student> findAllBySubject(Subject subject) {
        List<Student> students = Lists.newArrayList();
        for (Group group : subject.getGroups()) {
            students.addAll(studentRepository.findAllByGroup(group));
        }
        return students;
    }

	@Override
	public List<Student> parseStudents(InputStream stream) {
		IParser<Student> parser = new DocParser();
		parser.parse(stream);
		return parser.getElements();
	}

    @Override
    public Page<Student> findAllByLastname(Pageable pageable, String lastname) {
        return studentRepository.findAllByLastnameContainingIgnoreCase(pageable, lastname);
    }

    @Override
    public Page<Student> findAll(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    @Override
    public void addStudent(Student student) {
        studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Student student) {
        studentRepository.delete(student);
    }

    @Override
    public int getSize() {
       return (int) studentRepository.count();
    }


	@Override
	public void saveStudents(List<Student> students) {
        for (Student student : students) {
            Optional.ofNullable(groupRepository.findByName(student.getGroup().getName())).ifPresent(student::setGroup);
            Optional.ofNullable(studentRepository.findByCode(student.getCode())).ifPresent(x -> student.setId(x.getId()));
            studentRepository.save(student);
        }
	}

    private void getAllObjects(Page<Student> studentPage){
        for(Student student : studentPage){
            student.getGroup().getId();
        }
    }

}

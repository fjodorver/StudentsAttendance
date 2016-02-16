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
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {
	@Inject
	private GroupRepository groupRepository;

	@Inject
	private StudentRepository studentRepository;

	@Override
	public List<Student> parseStudents(InputStream stream) {
		IParser<Student> parser = new DocParser();
		parser.parse(stream);
		return parser.getElements();
	}

	@Override
	public List<Student> findAllStudents(Group group) {
		return studentRepository.findAllByGroup(group);
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
    public Page<Student> findAll(int page, int size, String lastname){
        Pageable pageable = new PageRequest(page, size, new Sort(Sort.Direction.ASC, "lastname"));
        Page<Student> students = studentRepository.findAllByLastname(pageable, lastname);
        getAllObjects(students);
        return students;
    }

    @Override
    public Page<Student> findAllStudents(Integer page, Integer size) {
        Page<Student> students = studentRepository.findAll(new PageRequest(page, size, new Sort(Sort.Direction.ASC, "lastname")));
        getAllObjects(students);
        return students;
    }

    @Override
    public int getSize() {
       return (int) studentRepository.count();
    }


    @Override
    public List<Student> findAllStudents(Group group) {
        return studentRepository.findAllByGroup(group);
    }

	@Override
	public List<Student> saveStudents(List<Student> students) {
		students.forEach(x -> Optional.ofNullable(groupRepository.findByName(x.getGroup().getName()))
				.ifPresent(x::setGroup));
		students.forEach(x -> Optional.ofNullable(studentRepository.findByCode(x.getCode()))
				.ifPresent(y -> x.setId(y.getId())));
		return studentRepository.save(students);
	}

    private void getAllObjects(Page<Student> studentPage){
        for(Student student : studentPage){
            student.getGroup().getId();
        }
    }

}

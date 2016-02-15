package ee.ttu.vk.sa.service.impl;

import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ee.ttu.vk.sa.domain.Group;
import ee.ttu.vk.sa.domain.Student;
import ee.ttu.vk.sa.repository.GroupRepository;
import ee.ttu.vk.sa.repository.StudentRepository;
import ee.ttu.vk.sa.service.StudentService;
import ee.ttu.vk.sa.utils.DocParser;
import ee.ttu.vk.sa.utils.IParser;

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
	public List<Student> saveStudents(List<Student> students) {
		for (Student student : students) {
			Group group = groupRepository.findByName(student.getGroup().getName());
			if (group != null)
				student.setGroup(group);

			if (!student.equals(studentRepository.findByCode(student.getCode())))
				studentRepository.save(student);
		}
		return studentRepository.findAll();
	}

}

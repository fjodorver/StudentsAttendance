package ee.ttu.vk.sa.service.impl;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import com.google.common.collect.Maps;
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
		students.forEach(x -> Optional.ofNullable(groupRepository.findByName(x.getGroup().getName()))
				.ifPresent(x::setGroup));
		students.forEach(x -> Optional.ofNullable(studentRepository.findByCode(x.getCode()))
				.ifPresent(y -> x.setId(y.getId())));
		return studentRepository.save(students);
	}

}

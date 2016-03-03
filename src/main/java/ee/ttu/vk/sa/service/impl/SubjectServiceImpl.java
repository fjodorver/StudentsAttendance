package ee.ttu.vk.sa.service.impl;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import ee.ttu.vk.sa.domain.Group;
import ee.ttu.vk.sa.domain.Subject;
import ee.ttu.vk.sa.domain.Teacher;
import ee.ttu.vk.sa.repository.GroupRepository;
import ee.ttu.vk.sa.repository.SubjectRepository;
import ee.ttu.vk.sa.repository.TeacherRepository;
import ee.ttu.vk.sa.service.SubjectService;
import ee.ttu.vk.sa.utils.IParser;
import ee.ttu.vk.sa.utils.SubjectXlsParser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class SubjectServiceImpl implements SubjectService {

	@Inject
	private SubjectRepository subjectRepository;

	@Inject
	private GroupRepository groupRepository;

	@Inject
	private TeacherRepository teacherRepository;

	@Override
	public List<Subject> parse(InputStream stream) {
		IParser<Subject> parser = new SubjectXlsParser();
		parser.parse(stream);
		return parser.getElements();
	}

	@Override
	public List<Subject> save(List<Subject> subjects) {
		Map<String, Group> groupByName = Maps.newHashMap();
		for (Subject subject : subjects) {
			for (Group group : subject.getGroups()) {
				Group dbGroup = groupRepository.findByName(group.getName());
				groupByName.put(group.getName(), Optional.ofNullable(dbGroup).orElse(group));
			}
		}
		for (Subject subject : subjects) {
			Optional.ofNullable(subjectRepository.findByCode(subject.getCode())).ifPresent(x -> subject.setId(x.getId()));
			Optional.ofNullable(teacherRepository.findByName(subject.getTeacher().getName())).ifPresent(subject::setTeacher);
			subject.setGroups(getGroups(subject, groupByName));
		}
		return subjectRepository.save(subjects);
	}

	@Override
	public List<Subject> findAll(Subject subject, Pageable pageable) {
		String code = Optional.ofNullable(subject.getCode()).orElse("");
		String name = Optional.ofNullable(subject.getName()).orElse("");
		return subjectRepository.findAll(code, name, pageable).getContent();
	}


	@Override
	public List<Subject> findAll(Teacher teacher) {
		return subjectRepository.findAllByTeacher(teacher);
	}

    @Override
    public long getSize(Subject subject) {
		String code = Optional.ofNullable(subject.getCode()).orElse("");
		String name = Optional.ofNullable(subject.getName()).orElse("");
        return subjectRepository.count(code, name);
    }

	private List<Group> getGroups(Subject subject, Map<String, Group> groupByName) {
		List<Group> dbGroups = Lists.newArrayList();
		for (Group group : subject.getGroups()) {
			Group dbGroup = groupByName.get(group.getName());
			dbGroups.add(dbGroup);
		}
		return dbGroups;
	}
}
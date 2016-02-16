package ee.ttu.vk.sa.service.impl;


import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import ee.ttu.vk.sa.domain.Group;
import ee.ttu.vk.sa.domain.Subject;
import ee.ttu.vk.sa.domain.Teacher;
import ee.ttu.vk.sa.repository.GroupRepository;
import ee.ttu.vk.sa.repository.SubjectRepository;
import ee.ttu.vk.sa.repository.TeacherRepository;
import ee.ttu.vk.sa.service.SubjectService;
import ee.ttu.vk.sa.utils.IParser;
import ee.ttu.vk.sa.utils.XlsParser;
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
import java.util.Set;

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
	public List<Subject> parseSubjects(InputStream stream) {
		IParser<Subject> parser = new XlsParser();
		parser.parse(stream);
		return parser.getElements();
	}

	@Override
	public List<Subject> saveSubjects(List<Subject> subjects) {
		Map<String, Group> groupByName = Maps.newHashMap();
		for (Subject subject : subjects) {
			for (Group group : subject.getGroups()) {
				Group dbGroup = groupRepository.findByName(group.getName());
				groupByName.put(group.getName(), Optional.ofNullable(dbGroup).orElse(group));
			}
		}

		for (Subject subject : subjects) {
			Subject dbSubject = subjectRepository.findByCode(subject.getCode());
			Optional.of(dbSubject).ifPresent(x -> subject.setId(x.getId()));
			subject.setGroups(getGroups(subject, groupByName));
		}
		return subjectRepository.save(subjects);
	}
    @Override
    public void deleteSubject(Subject subject) {
        subjectRepository.delete(subject);
    }

    @Override
    public void addSubject(Subject subject) {
        subjectRepository.save(subject);
    }

    @Override
    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }


	@Override
	public List<Subject> findAllByTeacher(Teacher teacher) {
		return subjectRepository.findAllByTeacher(teacher);
	}

	private Set<Group> getGroups(Subject subject, Map<String, Group> groupByName) {
		Set<Group> dbGroups = Sets.newHashSet();
		for (Group group : subject.getGroups()) {
			Group dbGroup = groupByName.get(group.getName());
			dbGroups.add(dbGroup);
		}
		return dbGroups;
	}

    @Override
    public Page<Subject> findAll(int page, int size, String code) {
        Pageable pageable = new PageRequest(page, size, new Sort(Sort.Direction.ASC, "code"));
        Page<Subject> subjects = subjectRepository.findAllByCode(pageable, code);
        getAllObjects(subjects);
        return subjects;
    }

    @Override
    public Page<Subject> findAllSubjects(Integer page, Integer size) {
        Page<Subject> subjects = subjectRepository.findAll(new PageRequest(page, size, new Sort(Sort.Direction.ASC, "code")));
        getAllObjects(subjects);
        return subjects;
    }

    @Override
    public int getSize() {
        return (int)subjectRepository.count();
    }

    private void getAllObjects(Page<Subject> subjectPage){
        for(Subject subject : subjectPage){
            if(subject.getTeacher() != null)
                subject.getTeacher().getId();
        }
    }
}

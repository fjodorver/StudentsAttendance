package ee.ttu.vk.sa.service.impl;


import ee.ttu.vk.sa.domain.Group;
import ee.ttu.vk.sa.domain.Subject;
import ee.ttu.vk.sa.repository.GroupRepository;
import ee.ttu.vk.sa.repository.SubjectRepository;
import ee.ttu.vk.sa.service.SubjectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service
@Transactional
public class SubjectServiceImpl implements SubjectService {

    @Inject
    SubjectRepository subjectRepository;

    @Inject
    private GroupRepository groupRepository;

    @Override
    public List<Subject> saveSubjects(List<Subject> subjects) {
        for(Subject subject : subjects){
            List<Group> groups = groupRepository.findAllBySubjects(subject.getGroups());
            if(groups != null)
                subject.setGroups(groups);

            if(!subject.equals(subjectRepository.findByCode(subject.getCode())))
                subjectRepository.save(subject);
        }
        return subjectRepository.findAll();
    }
}

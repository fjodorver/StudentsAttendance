package ee.ttu.vk.sa.service.impl;

import ee.ttu.vk.sa.domain.Group;
import ee.ttu.vk.sa.domain.Subject;
import ee.ttu.vk.sa.repository.GroupRepository;
import ee.ttu.vk.sa.service.GroupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {

    @Inject
    private GroupRepository groupRepository;

    @Override
    public void save(List<Group> groups) {
        groupRepository.save(groups);
    }

    @Override
    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    @Override
    public List<Group> findAll(List<Subject> subjects) {
        return groupRepository.findAllBySubjects(subjects);
    }
}

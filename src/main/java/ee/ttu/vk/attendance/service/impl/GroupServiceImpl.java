package ee.ttu.vk.attendance.service.impl;


import ee.ttu.vk.attendance.domain.Group;
import ee.ttu.vk.attendance.repository.GroupRepository;
import ee.ttu.vk.attendance.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public Group save(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public List<Group> save(List<Group> groups) {
        Map<String, Group> groupMap = groupRepository.findAll().stream().collect(Collectors.toMap(Group::getName, x -> x));
        List<Group> z = groups.stream().map(x -> Optional.ofNullable(groupMap.get(x.getName())).orElse(x)).collect(Collectors.toList());
        return groupRepository.save(z);
    }

    @Override
    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    @Override
    public List<Group> findAll(Pageable pageable) {
        return groupRepository.findAll(pageable).getContent();
    }
}

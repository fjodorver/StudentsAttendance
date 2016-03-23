package ee.ttu.vk.sa.service.impl;


import ee.ttu.vk.sa.domain.Group;
import ee.ttu.vk.sa.repository.GroupRepository;
import ee.ttu.vk.sa.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Group> save(List<Group> groups) {
        Map<String, Group> groupMap = groupRepository.findAll().stream().collect(Collectors.toMap(Group::getName, x -> x));
        List<Group> groupList = groups.stream().map(x -> Optional.ofNullable(groupMap.get(x.getName())).orElse(x)).collect(Collectors.toList());
        return groupRepository.save(groups.stream().map(x -> Optional.ofNullable(groupMap.get(x.getName())).orElse(x)).collect(Collectors.toList()));
    }

    @Override
    public List<Group> findAll() {
        return groupRepository.findAll();
    }


}

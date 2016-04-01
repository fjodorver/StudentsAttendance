package ee.ttu.vk.attendance.service.impl;


import ee.ttu.vk.attendance.domain.Programme;
import ee.ttu.vk.attendance.repository.ProgrammeRepository;
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
    private ProgrammeRepository programmeRepository;

    @Override
    public Programme save(Programme programme) {
        return programmeRepository.save(programme);
    }

    @Override
    public List<Programme> save(List<Programme> programmes) {
        Map<String, Programme> groupMap = programmeRepository.findAll().stream().collect(Collectors.toMap(Programme::getName, x -> x));
        List<Programme> z = programmes.stream().map(x -> Optional.ofNullable(groupMap.get(x.getName())).orElse(x)).collect(Collectors.toList());
        return programmeRepository.save(z);
    }

    @Override
    public List<Programme> findAll() {
        return programmeRepository.findAll();
    }

    @Override
    public List<Programme> findAll(Pageable pageable) {
        return programmeRepository.findAll(pageable).getContent();
    }
}

package ee.ttu.vk.sa.service.impl;


import ee.ttu.vk.sa.domain.Subject;
import ee.ttu.vk.sa.repository.SubjectRepository;
import ee.ttu.vk.sa.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    public List<Subject> findAll(Subject subject, Pageable pageable) {
        return null;
    }

    @Override
    public List<Subject> saveAll(List<Subject> subjects) {
        for (Subject subject : subjects) {
            subject.setId(Optional.ofNullable(subjectRepository.findByCode(subject.getCode())).orElse(subject).getId());
        }
        return subjectRepository.save(subjects);
//        return subjectRepository.save(subjects.stream()
//                .map(x -> Optional.ofNullable(subjectRepository.findByCode(x.getCode())).orElse(x))
//                .collect(Collectors.toList()));
    }

    @Override
    public long size(Subject subject) {
        return 0;
    }
}

package ee.ttu.vk.attendance.service.impl;


import ee.ttu.vk.attendance.domain.Subject;
import ee.ttu.vk.attendance.repository.SubjectRepository;
import ee.ttu.vk.attendance.service.SubjectService;
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

    public List<Subject> findAll(Subject filter, Pageable pageable) {
        String code = Optional.ofNullable(filter.getCode()).orElse("");
        String name = Optional.ofNullable(filter.getName()).orElse("");
        return subjectRepository.findAll(code, name, pageable).getContent();    }

    @Override
    public long size(Subject subject) {
        String code = Optional.ofNullable(subject.getCode()).orElse("");
        String name = Optional.ofNullable(subject.getName()).orElse("");
        return subjectRepository.count(code, name);
    }

    @Override
    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }

    @Override
    public List<Subject> findAll(Pageable pageable) {
        return subjectRepository.findAll(pageable).getContent();
    }

    @Override
    public Subject save(Subject subject) {
        return subjectRepository.save(subject);
    }

    @Override
    public List<Subject> save(List<Subject> subjects) {
        for (Subject subject : subjects) {
            subject.setId(Optional.ofNullable(subjectRepository.findByCode(subject.getCode())).orElse(subject).getId());
        }
        return subjectRepository.save(subjects);    }
}

package ee.ttu.vk.attendance.service;


import ee.ttu.vk.attendance.domain.Subject;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by fjodor on 4.03.16.
 */
public interface SubjectService {

    List<Subject> findAll(Subject subject, Pageable pageable);
    List<Subject> saveAll(List<Subject> subjects);
    long size(Subject subject);
}
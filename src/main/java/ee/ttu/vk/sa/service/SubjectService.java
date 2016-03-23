package ee.ttu.vk.sa.service;


import ee.ttu.vk.sa.domain.Subject;

import java.util.List;

/**
 * Created by fjodor on 4.03.16.
 */
public interface SubjectService {
    List<Subject> saveAll(List<Subject> subjects);
}

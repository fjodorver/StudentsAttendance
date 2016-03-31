package ee.ttu.vk.attendance.service;


import ee.ttu.vk.attendance.domain.Teacher;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by fjodor on 5.03.16.
 */
public interface TeacherService extends IReadService<Teacher>, ISaveService<Teacher>, IProviderService<Teacher, Teacher> {
    Teacher find(String username, String password);
}


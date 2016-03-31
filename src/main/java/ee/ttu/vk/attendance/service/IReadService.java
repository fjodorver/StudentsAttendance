package ee.ttu.vk.attendance.service;

import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by fjodor on 3/24/16.
 */
public interface IReadService<T> {
    List<T> findAll();
    List<T> findAll(Pageable pageable);
}

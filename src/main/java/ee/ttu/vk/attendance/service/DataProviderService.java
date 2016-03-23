package ee.ttu.vk.attendance.service;

import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by fjodor on 3/23/16.
 */
public interface DataProviderService<T, F> {
    List<T> find(F filter, Pageable pageable);
    long size(F filter);
}

package ee.ttu.vk.attendance.service;

import java.util.List;

/**
 * Created by fjodor on 3/24/16.
 */
public interface ISaveService<T> {
    T save(T t);
    List<T> save(List<T> ts);
}
package ee.ttu.vk.attendance.service;

import java.util.List;

/**
 * Created by Fjodor Vershinin on 4/2/2016.
 */
public interface IFilterService<T, F> {
    List<T> findAll(F f);
}

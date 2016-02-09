package ee.ttu.vk.sa.pages.panels;

import java.util.List;

/**
 * Created by fjodor on 8.02.16.
 */
public interface IAction<T> {
    void save(List<T> objects);
}

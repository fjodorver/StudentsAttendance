package ee.ttu.vk.sa.utils;

import java.io.InputStream;
import java.util.List;

/**
 * Created by fjodor on 6.02.16.
 */
public interface IParser<T> {
    void parse(InputStream io);
    List<T> getElements();
}

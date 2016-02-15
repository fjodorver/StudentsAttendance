package ee.ttu.vk.sa.pages.panels;

import java.io.InputStream;

/**
 * Created by fjodor on 8.02.16.
 */
public interface IAction<T> {
	void save(InputStream inputStream);
}

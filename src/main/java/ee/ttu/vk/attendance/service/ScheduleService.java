package ee.ttu.vk.attendance.service;

import net.fortuna.ical4j.data.ParserException;

import java.io.IOException;

/**
 * Created by fjodor on 4.03.16.
 */
public interface ScheduleService {
    void updateGroups() throws IOException;
    void update() throws IOException, ParserException;
}


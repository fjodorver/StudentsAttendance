package ee.ttu.vk.attendance.pages.filters;

import ee.ttu.vk.attendance.domain.Teacher;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by fjodor on 3/23/16.
 */
public class TimetableFilter implements Serializable {
    private Date date = new Date();
    private Teacher teacher;

    public Date getDate() {
        return date;
    }

    public TimetableFilter setDate(Date date) {
        this.date = date;
        return this;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public TimetableFilter setTeacher(Teacher teacher) {
        this.teacher = teacher;
        return this;
    }
}

package ee.ttu.vk.attendance.pages.providers;

import ee.ttu.vk.attendance.domain.Teacher;
import ee.ttu.vk.attendance.service.TeacherService;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Iterator;

/**
 * Created by vadimstrukov on 2/15/16.
 */
public class TeacherDataProvider extends AbstractDataProvider<Teacher, Teacher> {

    @SpringBean
    private TeacherService teacherService;

    public TeacherDataProvider(){
        Injector.get().inject(this);
        filter = new Teacher();
    }

    @Override
    public Iterator<? extends Teacher> iterator(long first, long count) {
        Pageable pageable = new PageRequest((int)(first/count), (int)count, Sort.Direction.ASC, "fullname");
        return teacherService.findAll(filter, pageable).iterator();
    }

    @Override
    public long size() {
        return teacherService.size(filter);
    }

    @Override
    public IModel<Teacher> model(Teacher teacher) {
        return new CompoundPropertyModel<>(teacher);
    }
}

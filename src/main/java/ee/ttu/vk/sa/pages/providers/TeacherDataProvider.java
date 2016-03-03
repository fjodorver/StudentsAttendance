package ee.ttu.vk.sa.pages.providers;

import ee.ttu.vk.sa.domain.Teacher;
import ee.ttu.vk.sa.service.TeacherService;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
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
public class TeacherDataProvider extends AbstractDataProvider<Teacher, String> {

    @SpringBean
    private TeacherService teacherService;

    private Teacher teacher;

    public TeacherDataProvider(){
        Injector.get().inject(this);
        teacher = new Teacher();
    }

    @Override
    public Iterator<? extends Teacher> iterator(long first, long count) {
        Pageable pageable = new PageRequest((int)(first/count), (int)count, Sort.Direction.ASC, "name");
        return teacherService.findAll(teacher, pageable).iterator();
    }

    @Override
    public long size() {
        return teacherService.getSize(teacher);
    }

    @Override
    public IModel<Teacher> model(Teacher teacher) {
        return new CompoundPropertyModel<>(teacher);
    }

    @Override
    public Teacher getFilterState() {
        return teacher;
    }

    @Override
    public void setFilterState(Teacher teacher) {
        this.teacher = teacher;
    }
}

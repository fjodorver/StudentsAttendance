package ee.ttu.vk.sa.pages.providers;

import ee.ttu.vk.sa.domain.Teacher;
import ee.ttu.vk.sa.service.TeacherService;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.Iterator;

/**
 * Created by vadimstrukov on 2/15/16.
 */
public class TeacherDataProvider extends SortableDataProvider<Teacher, Teacher> implements IFilterStateLocator<Teacher> {

    @SpringBean
    private TeacherService teacherService;
    private Teacher teacher;

    public TeacherDataProvider(){
        Injector.get().inject(this);
        teacher = new Teacher();
    }

    @Override
    public Iterator<? extends Teacher> iterator(long l, long l1) {
        return teacherService.findAll().iterator();
    }

    @Override
    public long size() {
        return teacherService.findAll().size();
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

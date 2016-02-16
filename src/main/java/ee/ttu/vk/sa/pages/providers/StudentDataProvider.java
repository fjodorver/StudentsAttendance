package ee.ttu.vk.sa.pages.providers;

import ee.ttu.vk.sa.domain.Student;
import ee.ttu.vk.sa.service.StudentService;
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
public class StudentDataProvider extends SortableDataProvider<Student, Student> implements IFilterStateLocator<Student> {

    @SpringBean
    private StudentService studentService;
    private Student student;

    public StudentDataProvider(){
        Injector.get().inject(this);
        student = new Student();
    }

    @Override
    public Iterator<? extends Student> iterator(long l, long l1) {
        return studentService.findAll().iterator();
    }

    @Override
    public long size() {
        return studentService.findAll().size();
    }

    @Override
    public IModel<Student> model(Student student) {
        return new CompoundPropertyModel<>(student);
    }

    @Override
    public Student getFilterState() {
        return student;
    }

    @Override
    public void setFilterState(Student student) {
        this.student = student;
    }
}

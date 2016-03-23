package ee.ttu.vk.attendance.pages.providers;

import ee.ttu.vk.attendance.domain.Student;
import ee.ttu.vk.attendance.service.StudentService;
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
public class StudentDataProvider extends AbstractDataProvider<Student, Student> {

    @SpringBean
    private StudentService studentService;

    public StudentDataProvider(){
        Injector.get().inject(this);
        filter = new Student();
    }

    @Override
    public Iterator<? extends Student> iterator(long first, long count) {
        Pageable pageable = new PageRequest((int)(first/count), (int)count, Sort.Direction.ASC, "lastname");
        return studentService.findAll(filter, pageable);
    }

    @Override
    public long size() {
        return studentService.size(filter);
    }

    @Override
    public IModel<Student> model(Student student) {
        return new CompoundPropertyModel<>(student);
    }
}

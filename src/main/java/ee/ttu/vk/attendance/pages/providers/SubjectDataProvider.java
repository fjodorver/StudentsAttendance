package ee.ttu.vk.attendance.pages.providers;

import ee.ttu.vk.attendance.domain.Subject;
import ee.ttu.vk.attendance.service.SubjectService;
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
public class SubjectDataProvider extends AbstractDataProvider<Subject, Subject> {

    @SpringBean
    private SubjectService subjectService;

    public SubjectDataProvider(){
        Injector.get().inject(this);
        filter = new Subject();
    }

    @Override
    public Iterator<? extends Subject> iterator(long first, long count) {
        Pageable pageable = new PageRequest((int)(first/count), (int)count, Sort.Direction.ASC, "name");
        return subjectService.findAll(filter, pageable).iterator();
    }

    @Override
    public long size() {
        return subjectService.size(filter);
    }

    @Override
    public IModel<Subject> model(Subject subject) {
        return new CompoundPropertyModel<>(subject);
    }
}

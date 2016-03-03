package ee.ttu.vk.sa.pages.providers;

import ee.ttu.vk.sa.domain.Subject;
import ee.ttu.vk.sa.service.SubjectService;
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
public class SubjectDataProvider extends AbstractDataProvider<Subject, String> {

    @SpringBean
    private SubjectService subjectService;

    private Subject subject;

    public SubjectDataProvider(){
        subject = new Subject();
        Injector.get().inject(this);
    }

    @Override
    public Iterator<? extends Subject> iterator(long first, long count) {
        Pageable pageable = new PageRequest((int)(first/count), (int)count, Sort.Direction.ASC, "name");
        return subjectService.findAll(subject, pageable).iterator();
    }

    @Override
    public long size() {
        return subjectService.getSize(subject);
    }

    @Override
    public IModel<Subject> model(Subject subject) {
        return new CompoundPropertyModel<>(subject);
    }

    @Override
    public Subject getFilterState() {
        return subject;
    }

    @Override
    public void setFilterState(Subject subject) {
        this.subject = subject;
    }
}

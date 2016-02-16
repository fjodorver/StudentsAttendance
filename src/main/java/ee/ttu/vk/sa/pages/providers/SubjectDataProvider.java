package ee.ttu.vk.sa.pages.providers;

import ee.ttu.vk.sa.domain.Subject;
import ee.ttu.vk.sa.service.SubjectService;
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
public class SubjectDataProvider extends SortableDataProvider<Subject, Subject> implements IFilterStateLocator<Subject> {

    @SpringBean
    private SubjectService subjectService;
    private Subject subject;

    public SubjectDataProvider(){
        subject = new Subject();
        Injector.get().inject(this);
    }

    @Override
    public Iterator<? extends Subject> iterator(long first, long count) {
        if(subject.getCode() != null)
            return subjectService.findAll((int)(first/count), (int)count, subject.getCode()).iterator();
        return subjectService.findAllSubjects((int)(first/count), (int)count).iterator();
    }

    @Override
    public long size() {
        return subjectService.getSize();
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

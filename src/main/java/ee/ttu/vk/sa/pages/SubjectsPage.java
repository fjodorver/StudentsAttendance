package ee.ttu.vk.sa.pages;


import ee.ttu.vk.sa.domain.Subject;
import ee.ttu.vk.sa.pages.panels.FileUploadPanel;
import ee.ttu.vk.sa.pages.panels.IAction;
import ee.ttu.vk.sa.service.SubjectService;
import ee.ttu.vk.sa.utils.XlsParser;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

@AuthorizeInstantiation(Roles.ADMIN)
public class SubjectsPage extends AbstractPage implements IAction<Subject> {

    @SpringBean
    private SubjectService subjectService;

    private FileUploadPanel<Subject> panel;

    public SubjectsPage(){
        panel = new FileUploadPanel<>("panel", new XlsParser(), this);
        add(panel);
    }

    @Override
    public void save(List<Subject> objects) {
        subjectService.saveSubjects(objects);
    }
}

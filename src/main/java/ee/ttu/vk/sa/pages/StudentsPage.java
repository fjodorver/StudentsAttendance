package ee.ttu.vk.sa.pages;

import ee.ttu.vk.sa.domain.Attendance;
import ee.ttu.vk.sa.domain.Student;
import ee.ttu.vk.sa.pages.panels.FileUploadPanel;
import ee.ttu.vk.sa.pages.panels.IAction;
import ee.ttu.vk.sa.pages.providers.AttendanceDataProvider;
import ee.ttu.vk.sa.service.StudentService;
import ee.ttu.vk.sa.utils.DocParser;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * Created by fjodor on 6.02.16.
 */

@AuthorizeInstantiation(Roles.ADMIN)
public class StudentsPage extends AbstractPage implements IAction<Student> {

    @SpringBean
    private StudentService studentService;

    private FileUploadPanel<Student> panel;

    public StudentsPage() {
        panel = new FileUploadPanel<>("panel", new DocParser(), this);
        add(panel);
    }

    @Override
    public void save(List<Student> objects) {
        studentService.saveStudents(objects);
    }
}

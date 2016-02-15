package ee.ttu.vk.sa.pages;

import java.io.InputStream;
import java.util.List;

import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ee.ttu.vk.sa.domain.Student;
import ee.ttu.vk.sa.pages.panels.FileUploadPanel;
import ee.ttu.vk.sa.pages.panels.IAction;
import ee.ttu.vk.sa.service.StudentService;

/**
 * Created by fjodor on 6.02.16.
 */

@AuthorizeInstantiation(Roles.ADMIN)
public class StudentsPage extends AbstractPage implements IAction<Student> {

	@SpringBean
	private StudentService studentService;

	private FileUploadPanel<Student> panel;

	public StudentsPage() {
		panel = new FileUploadPanel<>("panel", ".doc", this);
		add(panel);
	}

	@Override
	public void save(InputStream inputStream) {
		List<Student> students = studentService.parseStudents(inputStream);
		studentService.saveStudents(students);
	}
}

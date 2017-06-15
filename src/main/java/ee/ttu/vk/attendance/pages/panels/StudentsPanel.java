package ee.ttu.vk.attendance.pages.panels;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Modal;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeIconType;
import ee.ttu.vk.attendance.domain.Student;
import ee.ttu.vk.attendance.pages.components.BootstrapIndicatingAjaxLink;
import ee.ttu.vk.attendance.pages.providers.StudentDataProvider;
import ee.ttu.vk.attendance.service.AttendanceService;
import ee.ttu.vk.attendance.service.GroupService;
import ee.ttu.vk.attendance.service.StudentService;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.Iterator;
import java.util.List;

/**
 * Created by fjodor on 27.02.16.
 */
public class StudentsPanel extends Modal<List<Student>> {
    @SpringBean
    private StudentService studentService;

    @SpringBean
    private GroupService groupService;

    @SpringBean
    private AttendanceService attendanceService;

    private NotificationPanel notificationPanel;

    public StudentsPanel(String id, IModel<List<Student>> model) {
        super(id, model);
        TablePanel<Student, Student> studentsPanel = new TablePanel<>("students", new StudentDataProvider(){
            @Override
            public Iterator<? extends Student> iterator(long first, long count) {
                return model.getObject().subList((int) first, (int)(first + count)).iterator();
            }

            @Override
            public long size() {
                return model.getObject().size();
            }
        });
        studentsPanel.addColumn("Code", "code", "col-lg-2");
        studentsPanel.addColumn("Fullname", "fullname", "col-lg-8");
        studentsPanel.addColumn("Programme", "programme.name", "col-lg-2");
        add(studentsPanel, notificationPanel = new NotificationPanel("notificationPanel"));
        notificationPanel.setOutputMarkupId(true);
        studentsPanel.setVisibleFilterPanel(false);
        addButton(new BootstrapIndicatingAjaxLink<>("button", Buttons.Type.Primary, (x) -> {
            try {
                studentService.save(model.getObject());
                studentService.findAll().forEach(y->attendanceService.generateAndSaveAttendances(y.getProgramme()));
                close(x);
            }
            catch (Exception e){
                error("Something wrong!");
                x.add(notificationPanel);
            }
        }).setIconType(FontAwesomeIconType.save));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        header(new ResourceModel("upload-panel.header"));
    }
}

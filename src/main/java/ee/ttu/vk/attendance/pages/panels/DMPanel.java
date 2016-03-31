package ee.ttu.vk.attendance.pages.panels;

import com.google.common.collect.Lists;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Modal;
import de.agilecoders.wicket.core.markup.html.bootstrap.tabs.AjaxBootstrapTabbedPanel;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeIconType;
import ee.ttu.vk.attendance.domain.Student;
import ee.ttu.vk.attendance.domain.Subject;
import ee.ttu.vk.attendance.domain.Teacher;
import ee.ttu.vk.attendance.pages.components.BootstrapIndicatingAjaxLink;
import ee.ttu.vk.attendance.service.*;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by fjodor on 27.02.16.
 */
public class DMPanel extends Modal<Void> {

    @SpringBean
    private StudentService studentService;

    @SpringBean
    private GroupService groupService;

    @SpringBean
    private AttendanceService attendanceService;

    private ListModel<Student> studentsModel = new ListModel<>(Lists.newArrayList());

    public DMPanel(String markupId) {
        super(markupId);
        setOutputMarkupId(true);
        List<AbstractTab> tabs = Lists.newArrayList();
        tabs.add(new AbstractTab(new ResourceModel("data-management.tabs.students")) {
            @Override
            public WebMarkupContainer getPanel(String s) {
                return new StudentsPanel(s, studentsModel);
            }
        });
        addButton(new BootstrapIndicatingAjaxLink<Void>("button", Buttons.Type.Primary) {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                studentService.save(studentsModel.getObject());
                groupService.findAll().forEach(x->attendanceService.GenerateAndSaveAttendances(x));
                appendCloseDialogJavaScript(ajaxRequestTarget);
            }
        }.setLabel(new ResourceModel("upload-panel.buttons.save")).setIconType(FontAwesomeIconType.save));
        add(new AjaxBootstrapTabbedPanel<>("tabs", tabs));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        header(new ResourceModel("upload-panel.header"));
    }

    public DMPanel setStudentsModel(ListModel<Student> studentsModel) {
        this.studentsModel.getObject().clear();
        this.studentsModel.getObject().addAll(studentsModel.getObject());
        return this;
    }

}

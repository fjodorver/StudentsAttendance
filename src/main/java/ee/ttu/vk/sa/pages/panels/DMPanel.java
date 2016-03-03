package ee.ttu.vk.sa.pages.panels;

import com.google.common.collect.Lists;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Modal;
import de.agilecoders.wicket.core.markup.html.bootstrap.tabs.AjaxBootstrapTabbedPanel;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeIconType;
import ee.ttu.vk.sa.domain.Student;
import ee.ttu.vk.sa.domain.Subject;
import ee.ttu.vk.sa.domain.Teacher;
import ee.ttu.vk.sa.pages.components.BootstrapIndicatingAjaxLink;
import ee.ttu.vk.sa.service.StudentService;
import ee.ttu.vk.sa.service.SubjectService;
import ee.ttu.vk.sa.service.TeacherService;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * Created by fjodor on 27.02.16.
 */
public class DMPanel extends Modal<Void> {

    @SpringBean
    private StudentService studentService;

    @SpringBean
    private SubjectService subjectService;

    @SpringBean
    private TeacherService teacherService;

    private ListModel<Student> studentsModel = new ListModel<>(Lists.newArrayList());
    private ListModel<Subject> subjectsModel = new ListModel<>(Lists.newArrayList());;
    private ListModel<Teacher> teachersModel = new ListModel<>(Lists.newArrayList());;

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
        tabs.add(new AbstractTab(new ResourceModel("data-management.tabs.subjects")) {
            @Override
            public WebMarkupContainer getPanel(String s) {
                return new SubjectsPanel(s, subjectsModel);
            }
        });
        tabs.add(new AbstractTab(new ResourceModel("data-management.tabs.teachers")) {
            @Override
            public WebMarkupContainer getPanel(String s) {
                return new TeachersPanel(s, teachersModel);
            }
        });
        addButton(new BootstrapIndicatingAjaxLink<Void>("button", Buttons.Type.Primary) {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                studentService.save(studentsModel.getObject());
                teacherService.save(teachersModel.getObject());
                subjectService.save(subjectsModel.getObject());
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
        this.studentsModel.getObject().addAll(studentsModel.getObject());
        return this;
    }

    public DMPanel setSubjectsModel(ListModel<Subject> subjectsModel) {
        this.subjectsModel.getObject().addAll(subjectsModel.getObject());
        return this;
    }

    public DMPanel setTeachersModel(ListModel<Teacher> teachersModel) {
        this.teachersModel.getObject().addAll(teachersModel.getObject());
        return this;
    }
}

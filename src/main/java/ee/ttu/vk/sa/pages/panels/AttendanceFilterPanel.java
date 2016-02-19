package ee.ttu.vk.sa.pages.panels;

import com.google.common.collect.Lists;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.BootstrapForm;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.datetime.DatetimePickerConfig;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.datetime.DatetimePickerWithIcon;
import ee.ttu.vk.sa.CustomAuthenticatedWebSession;
import ee.ttu.vk.sa.domain.*;
import ee.ttu.vk.sa.enums.Status;
import ee.ttu.vk.sa.enums.Type;
import ee.ttu.vk.sa.service.AttendanceService;
import ee.ttu.vk.sa.service.StudentService;
import ee.ttu.vk.sa.service.SubjectService;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;
import java.util.stream.Collectors;

public class AttendanceFilterPanel extends Panel {
    @SpringBean
    private SubjectService subjectService;

    @SpringBean
    private StudentService studentService;

    @SpringBean
    private AttendanceService attendanceService;

    private BootstrapForm<Attendance> form;
    public AttendanceFilterPanel(String id, IFilterCallback<Attendance> callback) {
        super(id);
        Teacher teacher = CustomAuthenticatedWebSession.getSession().getTeacher();
        List<Subject> subjects = subjectService.findAllByTeacher(teacher);
        List<Group> groups = Lists.newArrayList();
        subjects.forEach(x -> groups.addAll(x.getGroups()));
        form = new BootstrapForm<>("form", new CompoundPropertyModel<>(new Attendance()));
        form.add(new DropDownChoice<>("subject", subjectService.findAllByTeacher(teacher)));
        form.add(new DropDownChoice<>("type", Lists.newArrayList(Type.values())));
        form.add(new DropDownChoice<>("group", groups));
        form.add(new DatetimePickerWithIcon("date", new DatetimePickerConfig()));
        form.add(new AjaxSubmitLink("submit", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> ajaxForm) {
                callback.onSubmit(target, form.getModel());
            }
        });
        form.add(new AjaxSubmitLink("gen", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> ajaxForm) {
                Attendance attendance = form.getModelObject().setStatus(Status.ABSENT);
                List<Attendance> attendances = Lists.newArrayList();
                List<Student> students = studentService.findAllBySubject(form.getModelObject().getSubject());
                students.forEach(x -> attendances.add(SerializationUtils.clone(attendance).setStudent(x)));
                attendanceService.save(attendances);
            }
        });
        add(form);
    }
    public interface IFilterCallback<T>{
        void onSubmit(AjaxRequestTarget target, IModel<T> model);
    }
}

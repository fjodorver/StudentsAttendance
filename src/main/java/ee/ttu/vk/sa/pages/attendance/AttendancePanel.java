package ee.ttu.vk.sa.pages.attendance;

import com.google.common.collect.Lists;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.BootstrapForm;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.datetime.DatetimePickerConfig;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.datetime.DatetimePickerWithIcon;
import ee.ttu.vk.sa.CustomAuthenticatedWebSession;
import ee.ttu.vk.sa.domain.*;
import ee.ttu.vk.sa.enums.Type;
import ee.ttu.vk.sa.service.SubjectService;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AttendancePanel extends Panel {
    @SpringBean
    private SubjectService subjectService;

    private BootstrapForm<Attendance> form;
    public AttendancePanel(String id) {
        super(id);
        Teacher teacher = CustomAuthenticatedWebSession.getSession().getTeacher();
        List<Subject> subjects = subjectService.findAll(teacher);
        List<Group> groups = Lists.newArrayList();
        subjects.forEach(x ->groups.addAll(x.getTimetables().stream().map(Timetable::getGroup).collect(Collectors.toList())));

        form = new BootstrapForm<>("form", new CompoundPropertyModel<>(new Attendance()));
        form.add(new DropDownChoice<>("subject", subjectService.findAll(teacher)));
        form.add(new DropDownChoice<>("type", Lists.newArrayList(Type.values())));
        form.add(new DropDownChoice<>("group", groups));
        form.add(new DatetimePickerWithIcon("date", new DatetimePickerConfig()));
        form.add(new AjaxSubmitLink("find", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> ajaxForm) {
                AttendancePanel.this.onSubmit(target, ajaxForm);
            }
        });
        form.add(new AjaxSubmitLink("generate", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> ajaxForm) {
                AttendancePanel.this.onSubmit(target, ajaxForm);
            }
        });
        add(form);
    }
    protected abstract void onSubmit(AjaxRequestTarget target, Form<?> ajaxForm);
}

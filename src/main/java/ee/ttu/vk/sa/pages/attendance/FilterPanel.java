package ee.ttu.vk.sa.pages.attendance;

import de.agilecoders.wicket.core.markup.html.bootstrap.form.BootstrapForm;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.datetime.DatetimePickerConfig;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.datetime.DatetimePickerWithIcon;
import ee.ttu.vk.sa.CustomAuthenticatedWebSession;
import ee.ttu.vk.sa.domain.Attendance;
import ee.ttu.vk.sa.domain.Teacher;
import ee.ttu.vk.sa.service.GroupService;
import ee.ttu.vk.sa.service.SubjectService;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * Created by fjodor on 15.02.16.
 */
public class FilterPanel extends Panel {

    @SpringBean
    private SubjectService subjectService;

    @SpringBean
    private GroupService groupService;

    private BootstrapForm<Attendance> form;

    public FilterPanel(String id, IFilterPanel filterPanel) {
        super(id);
        Teacher teacher = CustomAuthenticatedWebSession.getSession().getTeacher();
        form = new BootstrapForm<>("form", new CompoundPropertyModel<>(new Attendance()));
        form.add(new DropDownChoice<>("subject", subjectService.findAllByTeacher(teacher)));
        form.add(new DatetimePickerWithIcon("date", new DatetimePickerConfig().withFormat("MM/DD/yyyy")));
        form.add(new AjaxSubmitLink("submit", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> ajaxForm) {
                filterPanel.onSubmit(target, form.getModel());
            }
        }.setBody(Model.of("Search")));
        add(form);
    }

    public interface IFilterPanel{
        void onSubmit(AjaxRequestTarget target, IModel<Attendance> model);
    }
}
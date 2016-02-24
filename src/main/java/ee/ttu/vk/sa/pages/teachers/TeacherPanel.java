package ee.ttu.vk.sa.pages.teachers;

import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Modal;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.BootstrapForm;
import ee.ttu.vk.sa.domain.Teacher;
import ee.ttu.vk.sa.service.TeacherService;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import javax.inject.Inject;

/**
 * Created by vadimstrukov on 2/15/16.
 */
@AuthorizeInstantiation(Roles.ADMIN)
public class TeacherPanel extends Modal<Teacher> {

    @SpringBean
    private TeacherService teacherService;
    private BootstrapForm<Teacher> teacherForm;

    public TeacherPanel(String id, IModel<Teacher> model) {
        super(id, model);
        teacherForm  = new BootstrapForm<>("teacherForm", getModel());
        teacherForm.add(new TextField<String>("name"));
        teacherForm.add(new EmailTextField("email"));
        teacherForm.add(new PasswordTextField("password"));
        add(teacherForm);
        addButton(new AjaxSubmitLink("button", teacherForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                teacherService.addTeacher(getModelObject().setRole(Roles.USER));
                target.add(this.getPage());
            }
        }.setBody(Model.of("Save")));
    }

    @Override
    protected void onModelChanged() {
        this.teacherForm.setModel(getModel());
    }

}

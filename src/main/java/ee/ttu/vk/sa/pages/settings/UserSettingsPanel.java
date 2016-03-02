package ee.ttu.vk.sa.pages.settings;

import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationMessage;
import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.BootstrapForm;
import ee.ttu.vk.sa.CustomAuthenticatedWebSession;
import ee.ttu.vk.sa.domain.Attendance;
import ee.ttu.vk.sa.domain.Teacher;
import ee.ttu.vk.sa.service.TeacherService;
import ee.ttu.vk.sa.utils.PasswordEncryptor;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.time.Duration;
import org.apache.wicket.validation.validator.PatternValidator;

/**
 * Created by vadimstrukov on 2/27/16.
 */
public  class UserSettingsPanel extends Panel {

    @SpringBean
    private TeacherService teacherService;
    private BootstrapForm<Teacher> form;
    private PasswordEncryptor encryptor;


    public UserSettingsPanel(String id) {
        super(id);
        encryptor = new PasswordEncryptor();
        Teacher authTeacher = CustomAuthenticatedWebSession.getSession().getTeacher();

        final PasswordTextField password = new PasswordTextField("password",
                Model.of(""));
        password.setLabel(Model.of("Password"));

        final PasswordTextField cpassword = new PasswordTextField("cpassword",
                Model.of(""));
        cpassword.setLabel(Model.of("Confirm Password"));

        form = new BootstrapForm<>("form", new CompoundPropertyModel<>(authTeacher));
        form.add(new RequiredTextField<>("name"));
        form.add(new EmailTextField("email").setRequired(true));
        form.add(password);
        form.add(cpassword);
        form.add(new EqualPasswordInputValidator(password, cpassword));
        form.add(new AjaxSubmitLink("save", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> ajaxForm) {
                Teacher teacher = (Teacher) ajaxForm.getModelObject();
                teacher.setPassword(encryptor.encryptPassword(teacher.getPassword()));
                teacherService.saveTeacher(teacher);
            }
        });
        add(new FeedbackPanel("feedback"));
        add(form);
    }


}

package ee.ttu.vk.attendance.pages.settings;

import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationMessage;
import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.BootstrapForm;
import ee.ttu.vk.attendance.CustomAuthenticatedWebSession;
import ee.ttu.vk.attendance.domain.Teacher;
import ee.ttu.vk.attendance.pages.listeners.AjaxOnClick;
import ee.ttu.vk.attendance.service.TeacherService;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.*;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * Created by vadimstrukov on 2/27/16.
 */
public  class UserSettingsPanel extends Panel {

    @SpringBean
    private TeacherService teacherService;

    private BootstrapForm<Teacher> form;
    private NotificationPanel notificationPanel;


    public UserSettingsPanel(String id, AjaxOnClick ajaxOnClick) {
        super(id);
        FormComponent password, cpassword;
        Teacher authTeacher = CustomAuthenticatedWebSession.getSession().getTeacher();
        notificationPanel = new NotificationPanel("feedback");
        notificationPanel.setOutputMarkupId(true);
        form = new BootstrapForm<>("form", new CompoundPropertyModel<>(authTeacher));
        form.add(new RequiredTextField<>("fullname").setRequired(true));
        form.add(new TextField("username").setEnabled(false));
        form.add(password = getPasswordTextField("password", null, "settings.fields.password"));
        form.add(cpassword = getPasswordTextField("cpassword", new PropertyModel<>(form.getModelObject(), "password"), "settings.fields.cpassword"));
        form.add(new EqualPasswordInputValidator(password, cpassword));
        form.add(new AjaxSubmitLink("save", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> ajaxForm) {
                teacherService.save((Teacher) ajaxForm.getModelObject());
                success(new NotificationMessage(Model.of(new ResourceModel("settings.success"))));
                target.add(notificationPanel);
                ajaxOnClick.onClick(target);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                target.add(notificationPanel);
            }

        });
        form.setDefaultButton((IFormSubmittingComponent) form.get("save"));
        add(notificationPanel);
        add(form);
    }

    private FormComponent<String> getPasswordTextField(String id, IModel<String> model, String label) {
        return new PasswordTextField(id, model).setLabel(new ResourceModel(label)).setRequired(true);
    }
}
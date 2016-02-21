package ee.ttu.vk.sa.pages.students;

import com.google.common.collect.Lists;
import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Modal;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.BootstrapForm;
import ee.ttu.vk.sa.domain.Student;
import ee.ttu.vk.sa.service.GroupService;
import ee.ttu.vk.sa.service.StudentService;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * Created by vadimstrukov on 2/16/16.
 */
@AuthorizeInstantiation(Roles.ADMIN)
public class StudentPanel extends Modal<Student>{

    @SpringBean
    private StudentService studentService;
    @SpringBean
    private GroupService groupService;

    private BootstrapForm<Student> studentForm;

    public StudentPanel(String id, IModel<Student> model) {
        super(id, model);
        studentForm  = new BootstrapForm<>("studentForm", getModel());
        studentForm.add(new TextField<String>("code"));
        studentForm.add(new TextField<String>("firstname"));
        studentForm.add(new TextField<String>("lastname"));
        studentForm.add(new DropDownChoice<>("group", Lists.newArrayList(groupService.findAll())));
        add(studentForm);
        addButton(new AjaxSubmitLink("button", studentForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                studentService.save(getModelObject());
                target.add(this.getPage());
            }
        }.setBody(Model.of("Save")));
    }

    @Override
    protected void onModelChanged() {
        this.studentForm.setModel(getModel());
    }
}

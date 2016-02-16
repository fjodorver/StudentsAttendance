package ee.ttu.vk.sa.pages.panels;

import com.google.common.collect.Lists;
import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Modal;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.BootstrapForm;
import ee.ttu.vk.sa.domain.Subject;
import ee.ttu.vk.sa.service.SubjectService;
import ee.ttu.vk.sa.service.TeacherService;
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
public class SubjectsPanel extends Modal<Subject> {

    @SpringBean
    private SubjectService subjectService;
    @SpringBean
    private TeacherService teacherService;

    private BootstrapForm<Subject> subjectForm;


    public SubjectsPanel(String id, IModel<Subject> model) {
        super(id, model);
        subjectForm  = new BootstrapForm<>("subjectForm", getModel());
        subjectForm.add(new TextField<String>("code"));
        subjectForm.add(new TextField<String>("name"));
        subjectForm.add(new DropDownChoice<>("teacher", Lists.newArrayList(teacherService.findAll())));
        add(subjectForm);
        addButton(new AjaxSubmitLink("button", subjectForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                subjectService.addSubject(getModelObject());
                target.add(this.getPage());
            }
        }.setBody(Model.of("Save")));
    }


    @Override
    protected void onModelChanged() {
        this.subjectForm.setModel(getModel());
    }
}

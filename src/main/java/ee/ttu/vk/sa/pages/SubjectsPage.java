package ee.ttu.vk.sa.pages;


import de.agilecoders.wicket.core.markup.html.bootstrap.form.BootstrapForm;
import de.agilecoders.wicket.core.markup.html.bootstrap.navigation.ajax.BootstrapAjaxPagingNavigator;
import ee.ttu.vk.sa.CustomAuthenticatedWebSession;
import ee.ttu.vk.sa.domain.Student;
import ee.ttu.vk.sa.domain.Subject;
import ee.ttu.vk.sa.domain.Teacher;
import ee.ttu.vk.sa.pages.panels.FileUploadPanel;
import ee.ttu.vk.sa.pages.panels.SubjectsPanel;
import ee.ttu.vk.sa.pages.providers.SubjectDataProvider;
import ee.ttu.vk.sa.service.SubjectService;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.io.InputStream;
import java.util.List;

@AuthorizeInstantiation(Roles.ADMIN)
public class SubjectsPage extends AbstractPage {

    @SpringBean
    private SubjectService subjectService;
    private DataView<Subject> subjects;
    private SubjectDataProvider subjectDataProvider;
    private WebMarkupContainer subjectTable;
    private SubjectsPanel subjectPanel;
    private FileUploadPanel<Subject> panel;

    public SubjectsPage(){
        subjectDataProvider = new SubjectDataProvider();
        subjectTable = new WebMarkupContainer("subjectTable");
        subjectTable.setOutputMarkupId(true);
        subjectPanel = new SubjectsPanel("subjectPanel", new CompoundPropertyModel<>(new Subject()));
        subjects = getSubjects();
        subjects.setItemsPerPage(10);
        subjectTable.add(subjects);
        panel = new FileUploadPanel<Subject>("xlsPanel", Model.of("Excel file"), ".xls") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, InputStream inputStream) {
                List<Subject> subjects = subjectService.parseSubjects(inputStream);
                subjectService.saveSubjects(subjects);
            }
        };
        add(subjectTable, panel, new BootstrapAjaxPagingNavigator("navigator", subjects), subjectPanel, getButtonAddSubject(), getSearchForm());
    }

    private DataView<Subject> getSubjects(){
        return new DataView<Subject>("subjects", subjectDataProvider) {
            @Override
            protected void populateItem(Item<Subject> item) {
                item.add(new AjaxLink<Subject>("edit") {
                    @Override
                    public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                        subjectPanel.setModel(item.getModel());
                        ajaxRequestTarget.add(subjectPanel);
                        subjectPanel.appendShowDialogJavaScript(ajaxRequestTarget);
                    }
                }.add(new Label("code")));
                item.add(new Label("name"));
                item.add(new Label("teacher.name"));
                item.add(new AjaxLink<Teacher>("delete") {
                    @Override
                    public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                        subjectService.deleteSubject(item.getModelObject());
                        ajaxRequestTarget.add(subjectTable);
                    }

                    @Override
                    public boolean isVisible() {
                        return CustomAuthenticatedWebSession.getSession().isSignedIn();
                    }
                });
            }
        };
    }

    private BootstrapForm<Subject> getSearchForm(){
        BootstrapForm<Subject> form = new BootstrapForm<>("searchForm", new CompoundPropertyModel<>(new Subject()));
        form.add(new TextField<String>("code").add(new AjaxFormComponentUpdatingBehavior("input") {
            @Override
            protected void onUpdate(AjaxRequestTarget ajaxRequestTarget) {
                subjectDataProvider.setFilterState(form.getModelObject());
                ajaxRequestTarget.add(subjectTable);
            }
        }));
        return form;
    }

    private AjaxLink<Student> getButtonAddSubject(){
        return new AjaxLink<Student>("addSubject") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                subjectPanel.setModel(new CompoundPropertyModel<>(new Subject()));
                ajaxRequestTarget.add(subjectPanel);
                subjectPanel.appendShowDialogJavaScript(ajaxRequestTarget);
            }
            @Override
            public boolean isEnabled() {
                return CustomAuthenticatedWebSession.getSession().isSignedIn();
            }
        };
    }
}

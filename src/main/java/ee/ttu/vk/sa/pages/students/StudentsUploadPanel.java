package ee.ttu.vk.sa.pages.students;

import com.google.common.collect.Lists;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Modal;
import de.agilecoders.wicket.core.markup.html.bootstrap.navigation.ajax.BootstrapAjaxPagingNavigator;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeIconType;
import ee.ttu.vk.sa.domain.Student;
import ee.ttu.vk.sa.pages.components.BootstrapIndicatingAjaxLink;
import ee.ttu.vk.sa.service.StudentService;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * Created by fjodor on 19.02.16.
 */
public class StudentsUploadPanel extends Modal<List<Student>> {

    @SpringBean
    private StudentService studentService;

    private WebMarkupContainer container;

    private DataView<Student> dataView;

    private List<Student> students;

    public StudentsUploadPanel(String id) {
        super(id);
        students = Lists.newArrayList();
        container = new WebMarkupContainer("studentTable");
        container.setOutputMarkupId(true);
        dataView = getDataView();
        dataView.setItemsPerPage(10L);
        container.add(dataView);
        add(container);
        add(new BootstrapAjaxPagingNavigator("navigator", dataView));
        addButton(new BootstrapIndicatingAjaxLink<Void>("button", Buttons.Type.Primary) {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                studentService.saveStudents(students);
                appendCloseDialogJavaScript(ajaxRequestTarget);
            }
        }.setLabel(Model.of("Save")).setIconType(FontAwesomeIconType.save));
    }

    private DataView<Student> getDataView() {
        return new DataView<Student>("students", getDataProvider(students)) {
            @Override
            protected void populateItem(Item<Student> item) {
                item.add(new Label("code"));
                item.add(new Label("fullname"));
                item.add(new Label("group"));
                item.add(new AjaxLink<Student>("remove") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        students.remove(item.getIndex());
                        target.add(container);
                    }
                });
            }
        };
    }


    private ListDataProvider<Student> getDataProvider(final List<Student> students) {
        return new ListDataProvider<Student>(students){
            @Override
            public IModel<Student> model(Student student) {
                return new CompoundPropertyModel<>(student);
            }
        };
    }

    @Override
    protected void onModelChanged() {
        students.clear();
        students.addAll(getModelObject());
    }
}

package ee.ttu.vk.attendance.pages.panels;

import de.agilecoders.wicket.core.markup.html.bootstrap.form.BootstrapForm;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.fileinput.BootstrapFileInputField;
import ee.ttu.vk.attendance.pages.listeners.AjaxAction;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

import java.io.IOException;
import java.util.Optional;

public class FileUploadPanel extends Panel {

	public FileUploadPanel(String id, AjaxAction<FileUpload> action) {
        super(id);
        BootstrapForm<?> form = new BootstrapForm<Void>("form");
        BootstrapFileInputField docUploadField = getFileInputField("docField");
        form.add(docUploadField);
        form.add(new AjaxSubmitLink("upload", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                Optional.ofNullable(docUploadField.getFileUpload()).ifPresent(x -> {
                    try {
                        action.onSubmit(target, new Model<>(x));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
        add(form, new Label("header", new ResourceModel("upload-panel.header")));
    }

    private BootstrapFileInputField getFileInputField(final String id) {
        BootstrapFileInputField fileInputField = new BootstrapFileInputField(id);
        fileInputField.getConfig().showPreview(false).showUpload(false);
        return fileInputField;
    }
}
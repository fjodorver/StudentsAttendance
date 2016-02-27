package ee.ttu.vk.sa.pages.panels;

import com.google.common.collect.Lists;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.BootstrapForm;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.fileinput.BootstrapFileInput;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.fileinput.BootstrapFileInputField;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public abstract class FileUploadPanel extends Panel {

	public FileUploadPanel(String id) {
        super(id);
        BootstrapForm<?> form = new BootstrapForm<Void>("form");
        BootstrapFileInputField docUploadField = getFileInputField("docField");
        BootstrapFileInputField xlsUploadField = getFileInputField("xlsField");
        form.add(docUploadField, xlsUploadField);
        form.add(new AjaxSubmitLink("upload", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                List<FileUpload> fileUploads = Lists.newArrayList();
                Optional.ofNullable(docUploadField.getFileUpload()).ifPresent(fileUploads::add);
                Optional.ofNullable(xlsUploadField.getFileUpload()).ifPresent(fileUploads::add);
                try {
                    FileUploadPanel.this.onSubmit(target, fileUploads);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        add(form, new Label("header", new ResourceModel("upload-panel.header")));
    }

    private BootstrapFileInputField getFileInputField(final String id) {
        BootstrapFileInputField fileInputField = new BootstrapFileInputField(id);
        fileInputField.getConfig().showPreview(false).showUpload(false);
        return fileInputField;
    }

    protected abstract void onSubmit(AjaxRequestTarget target, List<FileUpload> fileUploads) throws IOException;
}
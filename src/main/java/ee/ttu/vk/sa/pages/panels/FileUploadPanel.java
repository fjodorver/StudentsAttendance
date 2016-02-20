package ee.ttu.vk.sa.pages.panels;

import java.io.IOException;
import java.io.InputStream;

import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.fileinput.BootstrapFileInputField;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.fileinput.FileInputConfig;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.Panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.form.BootstrapForm;
import org.apache.wicket.model.IModel;

public abstract class FileUploadPanel<T> extends Panel {

	public FileUploadPanel(String id, IModel<String> header, String extension) {
        super(id);
        BootstrapForm<?> form = new BootstrapForm<Void>("form");
        BootstrapFileInputField fileUploadField = new BootstrapFileInputField("fileUploadField"){
            @Override
            protected void onComponentTag(ComponentTag tag) {
                super.onComponentTag(tag);
				tag.put("accept", extension);
            }

            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                try {
                    FileUploadPanel.this.onSubmit(target, getFileUpload().getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        fileUploadField.getConfig().showUpload(true).showPreview(false);
        form.add(fileUploadField);
        add(form, new Label("header", header));
    }
    protected abstract void onSubmit(AjaxRequestTarget target, InputStream inputStream);
}
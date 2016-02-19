package ee.ttu.vk.sa.pages.panels;

import java.io.IOException;
import java.io.InputStream;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.Panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.form.BootstrapForm;

public abstract class FileUploadPanel<T> extends Panel {

	public FileUploadPanel(String id, String extension) {
        super(id);
        BootstrapForm<?> form = new BootstrapForm<Void>("form");
        FileUploadField fileUploadField = new FileUploadField("fileUploadField"){
            @Override
            protected void onComponentTag(ComponentTag tag) {
                super.onComponentTag(tag);
				tag.put("accept", extension);

            }
        };
        form.add(fileUploadField);
        form.add(new AjaxSubmitLink("uploadButton", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                try {
                    FileUploadPanel.this.onSubmit(target, fileUploadField.getFileUpload().getInputStream());
                } catch (IOException e) {
					throw new IllegalArgumentException(e);
                }
            }
        });
        add(form);
    }
    protected abstract void onSubmit(AjaxRequestTarget target, InputStream inputStream);
}
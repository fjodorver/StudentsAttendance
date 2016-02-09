package ee.ttu.vk.sa.pages.panels;

import de.agilecoders.wicket.core.markup.html.bootstrap.form.BootstrapForm;
import ee.ttu.vk.sa.utils.IParser;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.Panel;

import java.io.IOException;

public class FileUploadPanel<T> extends Panel {

    public FileUploadPanel(String id, IParser<T> parser, IAction<T> action) {
        super(id);
        BootstrapForm<?> form = new BootstrapForm<Void>("form");
        FileUploadField fileUploadField = new FileUploadField("fileUploadField");
        form.add(fileUploadField);
        form.add(new AjaxSubmitLink("uploadButton", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                try {
                    parser.parse(fileUploadField.getFileUpload().getInputStream());
                    action.save(parser.getElements());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        add(form);
    }
}
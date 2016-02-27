package ee.ttu.vk.sa.pages.settings;

import de.agilecoders.wicket.core.markup.html.bootstrap.form.BootstrapForm;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Created by vadimstrukov on 2/27/16.
 */
public class LocalizationSettings extends Panel {

    private BootstrapForm<LocalizationSettings> form;
    private Locale locale;

    public LocalizationSettings(String id) {
        super(id);
        locale = this.getSession().getLocale();
        List<Locale> locales = Arrays.asList(new Locale("en"), new Locale("ru"));
        form = new BootstrapForm<>("form", new CompoundPropertyModel<>(this));
        form.add(new DropDownChoice<>("locale", locales));
        form.add(new AjaxSubmitLink("save", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> ajaxForm) {
                this.getSession().setLocale(locale);
                setResponsePage(Settings.class);
            }
        });
        add(form);
    }

}

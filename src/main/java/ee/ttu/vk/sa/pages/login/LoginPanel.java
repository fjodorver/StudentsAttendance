package ee.ttu.vk.sa.pages.login;

import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import org.apache.wicket.authroles.authentication.panel.SignInPanel;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;

/**
 * Created by vadimstrukov on 2/13/16.
 */
public class LoginPanel extends SignInPanel {

    public LoginPanel(String id) {
        super(id);
        getForm().get("username").replaceWith(new EmailTextField("username").setRequired(true));
        getForm().get("password").replaceWith(new PasswordTextField("password").setRequired(true));
        get("feedback").replaceWith(new NotificationPanel("feedback"));
    }
}

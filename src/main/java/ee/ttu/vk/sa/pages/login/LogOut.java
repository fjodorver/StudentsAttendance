package ee.ttu.vk.sa.pages.login;

import ee.ttu.vk.sa.CustomAuthenticatedWebSession;
import ee.ttu.vk.sa.pages.AbstractPage;
import ee.ttu.vk.sa.pages.login.LoginPage;

/**
 * Created by vadimstrukov on 2/13/16.
 */
public class LogOut extends AbstractPage {
    public LogOut(){
        CustomAuthenticatedWebSession.getSession().invalidateNow();
        setResponsePage(LoginPage.class);
    }
}

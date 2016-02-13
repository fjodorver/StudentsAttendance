package ee.ttu.vk.sa.pages;

import ee.ttu.vk.sa.CustomAuthenticatedWebSession;

/**
 * Created by vadimstrukov on 2/13/16.
 */
public class LogOut extends AbstractPage {
    public LogOut(){
        CustomAuthenticatedWebSession.getSession().invalidateNow();
        setResponsePage(LoginPage.class);
    }
}

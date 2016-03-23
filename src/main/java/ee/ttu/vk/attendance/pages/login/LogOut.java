package ee.ttu.vk.attendance.pages.login;

import ee.ttu.vk.attendance.CustomAuthenticatedWebSession;
import ee.ttu.vk.attendance.pages.AbstractPage;

/**
 * Created by vadimstrukov on 2/13/16.
 */
public class LogOut extends AbstractPage {
    public LogOut(){
        CustomAuthenticatedWebSession.getSession().invalidateNow();
        setResponsePage(LoginPage.class);
    }
}

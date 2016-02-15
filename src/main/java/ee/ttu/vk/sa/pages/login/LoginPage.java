package ee.ttu.vk.sa.pages.login;

import ee.ttu.vk.sa.pages.AbstractPage;

/**
 * Created by fjodor on 6.02.16.
 */
public class LoginPage extends AbstractPage {
    public LoginPage(){
        this.add(new LoginPanel("loginPanel"));
    }
}

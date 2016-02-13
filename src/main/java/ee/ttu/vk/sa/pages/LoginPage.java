package ee.ttu.vk.sa.pages;

import ee.ttu.vk.sa.pages.panels.LoginPanel;

/**
 * Created by fjodor on 6.02.16.
 */
public class LoginPage extends AbstractPage {
    public LoginPage(){
        this.add(new LoginPanel("loginPanel"));
    }
}

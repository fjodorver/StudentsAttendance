package ee.ttu.vk.attendance.pages.login;


import org.apache.wicket.markup.html.WebPage;

/**
 * Created by fjodor on 6.02.16.
 */
public class LoginPage extends WebPage {
    public LoginPage(){
        this.add(new LoginPanel("loginPanel"));
    }
}

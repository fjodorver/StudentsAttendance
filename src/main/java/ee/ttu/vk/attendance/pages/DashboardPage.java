package ee.ttu.vk.attendance.pages;

import ee.ttu.vk.attendance.CustomAuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;

/**
 * Created by fjodor on 14.02.16.
 */
@AuthorizeInstantiation({Roles.USER, Roles.ADMIN})
public class DashboardPage extends AbstractPage {
    public DashboardPage() {
        Roles roles = CustomAuthenticatedWebSession.getSession().getRoles();
        if(roles.hasRole(Roles.USER)) setResponsePage(TimetablesPage.class);
        else if(roles.hasRole(Roles.ADMIN)) setResponsePage(DataManagementPage.class);
    }
}

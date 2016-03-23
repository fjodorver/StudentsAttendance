package ee.ttu.vk.attendance.pages;

import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;

/**
 * Created by fjodor on 14.02.16.
 */
@AuthorizeInstantiation({Roles.USER, Roles.ADMIN})
public class DashboardPage extends AbstractPage {

}

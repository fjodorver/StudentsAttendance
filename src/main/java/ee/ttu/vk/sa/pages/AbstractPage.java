package ee.ttu.vk.sa.pages;

import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.Navbar;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarButton;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarComponents;
import ee.ttu.vk.sa.CustomAuthenticatedWebSession;
import org.apache.wicket.Component;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.Model;

/**
 * Created by fjodor on 6.02.16.
 */
public class AbstractPage extends WebPage {
    private Navbar navbar;
    public AbstractPage() {
        navbar = new Navbar("header");
        navbar.setBrandName(Model.of("Hello"));
            addMenuItem(LoginPage.class, "Login", Navbar.ComponentPosition.LEFT, !CustomAuthenticatedWebSession.get().isSignedIn());
            addMenuItem(LogOut.class, "Logout", Navbar.ComponentPosition.RIGHT, CustomAuthenticatedWebSession.get().isSignedIn());
            addMenuItem(StudentsPage.class, "Students", Navbar.ComponentPosition.LEFT, CustomAuthenticatedWebSession.getSession().getRoles().hasRole(Roles.ADMIN));
            addMenuItem(SubjectsPage.class, "Subjects", Navbar.ComponentPosition.LEFT, CustomAuthenticatedWebSession.getSession().getRoles().hasRole(Roles.ADMIN));
        add(navbar);
    }

    private <P extends AbstractPage> void addMenuItem(Class<P> page, String label, Navbar.ComponentPosition position, boolean visible) {
        Component button = new NavbarButton<Void>(page, Model.of(label)){
            @Override
            public boolean isVisible() {
                return visible;
            }
        };
        navbar.addComponents(NavbarComponents.transform(position, button));
    }

}

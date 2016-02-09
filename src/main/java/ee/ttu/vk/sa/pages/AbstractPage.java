package ee.ttu.vk.sa.pages;

import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.Navbar;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarButton;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarComponents;
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
        addMenuItem(StudentsPage.class, "Students", Navbar.ComponentPosition.LEFT);
        add(navbar);
    }

    private <P extends AbstractPage> void addMenuItem(Class<P> page, String label, Navbar.ComponentPosition position) {
        NavbarButton<Void> button = new NavbarButton<Void>(page, Model.of(label));
        navbar.addComponents(NavbarComponents.transform(position, button));
    }

}

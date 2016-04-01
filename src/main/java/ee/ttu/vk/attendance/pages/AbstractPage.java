package ee.ttu.vk.attendance.pages;

import com.google.common.collect.Lists;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.dropdown.MenuBookmarkablePageLink;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.Navbar;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarButton;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarComponents;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarDropDownButton;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeIconType;
import ee.ttu.vk.attendance.CustomAuthenticatedWebSession;
import ee.ttu.vk.attendance.domain.Teacher;
import ee.ttu.vk.attendance.pages.login.LogOut;
import ee.ttu.vk.attendance.pages.menu.VerticalMenu;
import ee.ttu.vk.attendance.pages.settings.Settings;
import ee.ttu.vk.attendance.pages.statistics.test.StatisticsPage;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;

import java.util.List;
import java.util.Optional;

/**
 * Created by fjodor on 6.02.16.
 */
public class AbstractPage extends WebPage {
    private VerticalMenu navbar;

    public AbstractPage() {
        navbar = new VerticalMenu("navigation");
        navbar.setBrandName(new StringResourceModel("navbar.title"));
        navbar.setInverted(true);
        navbar.setPosition(Navbar.Position.TOP);
        navbar.fluid();

        Roles roles = CustomAuthenticatedWebSession.getSession().getRoles();

        addMenuItem(TimetablesPage.class, "navbar.menu.dashboard", FontAwesomeIconType.table, CustomAuthenticatedWebSession.get().isSignedIn());
        addMenuItem(DashboardPage.class, "navbar.menu.dashboard", FontAwesomeIconType.dashboard, CustomAuthenticatedWebSession.get().isSignedIn());
        addMenuItem(StatisticsPage.class, "navbar.menu.statistics", FontAwesomeIconType.bar_chart_o, roles.hasRole(Roles.USER));
        addMenuItem(DataManagementPage.class, "navbar.menu.data-management", FontAwesomeIconType.database, roles.hasRole(Roles.ADMIN));
        navbar.addComponents(NavbarComponents.transform(Navbar.ComponentPosition.RIGHT, addUserMenu()));

        add(navbar);

    }

    private <P extends Page> void addMenuItem(Class<P> page, String label, IconType icon, boolean visible) {
        Component button = new NavbarButton<Void>(page, new StringResourceModel(label)).setIconType(icon).setVisible(visible);
        navbar.addComponents(NavbarComponents.transform(Navbar.ComponentPosition.LEFT, button));
    }

    private Component addUserMenu(){
        String fullname = Optional.ofNullable(CustomAuthenticatedWebSession.getSession().getTeacher()).orElse(new Teacher()).getFullname();
        return new NavbarDropDownButton(Model.of(fullname)){

            @Override
            protected List<AbstractLink> newSubMenuButtons(String s) {
                List<AbstractLink> subMenu = Lists.newArrayList();
                subMenu.add(new MenuBookmarkablePageLink<Void>(Settings.class, new StringResourceModel("navbar.menu.settings")).setIconType(FontAwesomeIconType.gears));
                subMenu.add(new MenuBookmarkablePageLink<Void>(LogOut.class, new StringResourceModel("navbar.userMenu.logout")).setIconType(FontAwesomeIconType.sign_out));
                return subMenu;
            }

            @Override
            public boolean isVisible() {
                return CustomAuthenticatedWebSession.get().isSignedIn();
            }
        }.setIconType(FontAwesomeIconType.user);
    }
}

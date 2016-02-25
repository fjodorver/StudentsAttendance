package ee.ttu.vk.sa;

import de.agilecoders.wicket.core.Bootstrap;
import de.agilecoders.wicket.core.markup.html.themes.bootstrap.BootstrapCssReference;
import de.agilecoders.wicket.core.settings.BootstrapSettings;
import de.agilecoders.wicket.core.settings.SingleThemeProvider;
import de.agilecoders.wicket.core.settings.Theme;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeCssReference;
import ee.ttu.vk.sa.pages.DashboardPage;
import ee.ttu.vk.sa.pages.assets.NutchUiCssReference;
import ee.ttu.vk.sa.pages.login.LoginPage;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

public class WicketApplication extends AuthenticatedWebApplication
{
	@Override
	public Class<? extends WebPage> getHomePage()
	{
		return DashboardPage.class;
	}

	@Override
	public void init()
	{
		super.init();
		BootstrapSettings settings = new BootstrapSettings();
		Bootstrap.install(this, settings);
		Theme theme = new Theme("bootstrap", BootstrapCssReference.instance(), FontAwesomeCssReference.instance(), NutchUiCssReference.instance());
		settings.setThemeProvider(new SingleThemeProvider(theme));
		getComponentInstantiationListeners().add(new SpringComponentInjector(this));
	}

	@Override
	protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
		return CustomAuthenticatedWebSession.class;
	}

	@Override
	protected Class<? extends WebPage> getSignInPageClass() {
		return LoginPage.class;
	}
}

package ee.ttu.vk.sa;

import de.agilecoders.wicket.core.Bootstrap;
import ee.ttu.vk.sa.pages.LoginPage;
import ee.ttu.vk.sa.pages.StudentsPage;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

public class WicketApplication extends AuthenticatedWebApplication
{
	@Override
	public Class<? extends WebPage> getHomePage()
	{
		return StudentsPage.class;
	}

	@Override
	public void init()
	{
		super.init();
		Bootstrap.install(this);
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

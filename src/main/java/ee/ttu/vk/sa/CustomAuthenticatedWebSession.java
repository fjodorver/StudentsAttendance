package ee.ttu.vk.sa;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

/**
 * Created by fjodor on 6.02.16.
 */
public class CustomAuthenticatedWebSession extends AuthenticatedWebSession {
    public CustomAuthenticatedWebSession(Request request) {
        super(request);
    }

    @Override
    protected boolean authenticate(String s, String s1) {
        return false;
    }

    @Override
    public Roles getRoles() {
        return null;
    }
}

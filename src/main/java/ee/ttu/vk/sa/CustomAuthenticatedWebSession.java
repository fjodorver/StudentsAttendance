package ee.ttu.vk.sa;

import ee.ttu.vk.sa.domain.Teacher;
import ee.ttu.vk.sa.repository.TeacherRepository;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.Request;

import javax.inject.Inject;
import java.util.Optional;

/**
 * Created by fjodor on 6.02.16.
 */
public class CustomAuthenticatedWebSession extends AuthenticatedWebSession {

    @Inject
    private TeacherRepository teacherRepository;

    private Teacher teacher;


    public CustomAuthenticatedWebSession(Request request) {
        super(request);
        Injector.get().inject(this);
    }

    @Override
    protected boolean authenticate(String username, String password) {
        teacher = teacherRepository.findByEmailAndPassword(username, password);
        return teacher != null;
    }

    public static CustomAuthenticatedWebSession getSession(){
        return (CustomAuthenticatedWebSession) get();
    }

    @Override
    public Roles getRoles() {
        Roles roles = new Roles();
        Optional.ofNullable(teacher).ifPresent(x -> roles.add(x.getRole()));
        return roles;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    @Override
    public void signOut() {
        super.signOut();
        teacher = null;
    }
}

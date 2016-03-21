package ee.ttu.vk.sa.domain;

import com.google.common.base.Objects;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "teacher")
public class Teacher implements Serializable{
    @Id
    @SequenceGenerator(name="teacher_id_seq",sequenceName="teacher_id_seq", allocationSize=1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator="teacher_id_seq")
    private Long id;
    private String fullname;
    private String username;
    private String password;
    private String role = Roles.USER;

    public Long getId() {
        return id;
    }

    public Teacher setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFullname() {
        return fullname;
    }

    public Teacher setFullname(String fullname) {
        this.fullname = fullname;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public Teacher setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Teacher setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getRole() {
        return role;
    }

    public Teacher setRole(String role) {
        this.role = role;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equal(id, teacher.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

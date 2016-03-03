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
    private String name;
    private String email;
    private String password;
    private String role = Roles.USER;

    public Long getId() {
        return id;
    }

    public Teacher setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Teacher setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Teacher setEmail(String email) {
        this.email = email;
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

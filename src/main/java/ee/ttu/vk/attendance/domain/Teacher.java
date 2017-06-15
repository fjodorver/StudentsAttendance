package ee.ttu.vk.attendance.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Data
@EqualsAndHashCode(of = "id")
@Entity
public class Teacher implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String fullname;

    private String username;

    private String password;

    private String role = Roles.USER;

    @OneToMany(mappedBy = "teacher")
    private List<Timetable> timetables;

    @Override
    public String toString() {
        return fullname;
    }
}
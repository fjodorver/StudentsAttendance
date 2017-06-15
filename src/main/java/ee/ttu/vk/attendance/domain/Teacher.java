package ee.ttu.vk.attendance.domain;

import lombok.Data;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Data
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
}
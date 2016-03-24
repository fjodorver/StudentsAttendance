package ee.ttu.vk.attendance.domain;

import com.google.common.collect.Lists;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@NamedEntityGraph(name = "student.group", attributeNodes = {@NamedAttributeNode("group")})
@Table(name = "student")
public class Student implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String firstname;

    private String lastname;

    private String code;

    public String getFullname(){
        return firstname + " " + lastname;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Group group = new Group();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "student")
    private List<Attendance> attendances = Lists.newArrayList();


    public Long getId() {
        return id;
    }

    public Student setId(Long id) {
        this.id = id;
        return this;
    }


    public String getCode() {
        return code;
    }

    public Student setCode(String code) {
        this.code = code;
        return this;
    }

    public String getFirstname() {
        return firstname;
    }

    public Student setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public String getLastname() {
        return lastname;
    }

    public Student setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public Group getGroup() {
        return group;
    }

    public Student setGroup(Group group) {
        this.group = group;
        return this;
    }

    public List<Attendance> getAttendances() {
        return attendances;
    }

    public Student setAttendances(List<Attendance> attendances) {
        this.attendances = attendances;
        return this;
    }
}
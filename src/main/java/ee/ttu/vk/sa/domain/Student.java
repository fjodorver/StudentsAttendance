package ee.ttu.vk.sa.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "student")
public class Student implements Serializable {

    @Id
    @SequenceGenerator(name="student_id_seq",sequenceName="student_id_seq", allocationSize=1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator="student_id_seq")
    private Long id;

    private String code;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Group group;

    private String firstname;

    private String lastname;

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

    public Group getGroup() {
        return group;
    }

    public Student setGroup(Group group) {
        this.group = group;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        return code != null ? code.equals(student.code) : student.code == null;

    }

    @Override
    public int hashCode() {
        return code != null ? code.hashCode() : 0;
    }
}

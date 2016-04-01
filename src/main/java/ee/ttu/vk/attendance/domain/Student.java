package ee.ttu.vk.attendance.domain;

import com.google.common.collect.Lists;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@NamedEntityGraph(name = "student.all", attributeNodes = {@NamedAttributeNode("programme")})
@Table(name = "student")
public class Student implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String fullname;

    private String code;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Programme programme = new Programme();

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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Programme getProgramme() {
        return programme;
    }

    public Student setProgramme(Programme programme) {
        this.programme = programme;
        return this;
    }

    public List<Attendance> getAttendances() {
        return attendances;
    }

    public Student setAttendances(List<Attendance> attendances) {
        this.attendances = attendances;
        return this;
    }

    @Override
    public String toString() {
        return fullname;
    }
}

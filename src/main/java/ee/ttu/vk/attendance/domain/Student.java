package ee.ttu.vk.attendance.domain;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(of = "id")
@Entity
@NamedEntityGraph(name = "student.all", attributeNodes = {@NamedAttributeNode("programme")})
public class Student implements Serializable, Comparable<Student> {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String fullname;

    private String code;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Programme programme = new Programme();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "student")
    private List<Attendance> attendances = Lists.newArrayList();

    @Override
    public String toString() {
        return fullname;
    }

    @Override
    public int compareTo(Student student) {
        return fullname.compareTo(student.fullname);
    }
}
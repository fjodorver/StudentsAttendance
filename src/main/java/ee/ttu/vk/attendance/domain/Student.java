package ee.ttu.vk.attendance.domain;

import com.google.common.collect.Lists;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@NamedEntityGraph(name = "student.all", attributeNodes = {@NamedAttributeNode("programme")})
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
}
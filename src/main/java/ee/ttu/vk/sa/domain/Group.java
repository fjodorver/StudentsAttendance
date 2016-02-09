package ee.ttu.vk.sa.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "group")
public class Group implements Serializable {

    @Id
    @SequenceGenerator(name="group_id_seq",sequenceName="group_id_seq", allocationSize=1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator="group_id_seq")
    private Long id;

    private String name;

    @OneToMany
    private List<Student> students;

    public Long getId() {
        return id;
    }

    public Group setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Group setName(String name) {
        this.name = name;
        return this;
    }
}

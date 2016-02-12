package ee.ttu.vk.sa.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "group")
public class Group implements Serializable {

    @Id
    @SequenceGenerator(name="group_id_seq",sequenceName="group_id_seq", allocationSize=1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator="group_id_seq")
    private Long id;
    private String name;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "groups")
    private List<Subject> subjects;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Group obj2 = (Group) obj;
        return this.name.equals(obj2.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

}

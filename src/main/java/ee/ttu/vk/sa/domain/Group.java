package ee.ttu.vk.sa.domain;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "group")
public class Group implements Serializable {

    @Id
    @SequenceGenerator(name="group_id_seq",sequenceName="group_id_seq", allocationSize=1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator="group_id_seq")
    private Long id;
    private String name;
    private String language;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "groups")
    @Fetch(FetchMode.SUBSELECT)
    private Set<Subject> subjects = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY)
    private List<Student> students = Lists.newArrayList();

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

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }

    public String getLanguage() {
        return language;
    }

    public Group setLanguage(String language) {
        this.language = language;
        return this;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return Objects.equal(id, group.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return name;
    }
}

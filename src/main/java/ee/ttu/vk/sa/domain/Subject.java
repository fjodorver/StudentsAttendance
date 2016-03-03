package ee.ttu.vk.sa.domain;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "subject")
public class Subject implements Serializable {

    @Id
    @SequenceGenerator(name="subject_id_seq",sequenceName="subject_id_seq", allocationSize=1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator="subject_id_seq")
    private Long id;
    private String code;
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "subject_group", joinColumns = @JoinColumn(name = "subject_id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
    private List<Group> groups = Lists.newArrayList();

    @ManyToOne(cascade = CascadeType.ALL)
    private Teacher teacher;

    public Long getId() {
        return id;
    }

    public Subject setId(Long id) {
        this.id = id;
        return this;
    }

    public String getCode() {
        return code;
    }

    public Subject setCode(String code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public Subject setName(String name) {
        this.name = name;
        return this;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public Subject setGroups(List<Group> groups) {
        this.groups = groups;
        return this;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Subject setTeacher(Teacher teacher) {
        this.teacher = teacher;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return Objects.equal(id, subject.id);
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

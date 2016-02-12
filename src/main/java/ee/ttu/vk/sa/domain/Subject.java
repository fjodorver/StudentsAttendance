package ee.ttu.vk.sa.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "subject")
public class Subject implements Serializable {

    @Id
    @SequenceGenerator(name="subject_id_seq",sequenceName="subject_id_seq", allocationSize=1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator="subject_id_seq")
    private Long id;
    private String lect;
    private String code;
    private String name;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "subject_group", joinColumns = {
            @JoinColumn(name = "group_name") },
            inverseJoinColumns = { @JoinColumn(name = "subject_code") })
    private List<Group> groups;

    public Long getId() {
        return id;
    }

    public Subject setId(Long id) {
        this.id = id;
        return this;
    }


    public String getLect() {
        return lect;
    }

    public Subject setLect(String lect) {
        this.lect = lect;
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
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Subject obj2 = (Subject)obj;
        return this.code.equals(obj2.code);
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }

    public List<Group> getGroups() {
        return groups;
    }

    public Subject setGroups(List<Group> groups) {
        this.groups = groups;
        return this;
    }
}

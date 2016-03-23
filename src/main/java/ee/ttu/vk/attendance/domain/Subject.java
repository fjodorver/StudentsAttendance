package ee.ttu.vk.attendance.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "subject")
public class Subject implements Serializable {

    @Id
    @SequenceGenerator(name="subject_id_seq",sequenceName="subject_id_seq", allocationSize = 1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator="subject_id_seq")
    private Long id;

    private String code;

    private String name;

    @OneToMany(mappedBy = "subject")
    List<Timetable> timetables;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return Objects.equals(id, subject.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return name;
    }

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

    public List<Timetable> getTimetables() {
        return timetables;
    }

    public Subject setTimetables(List<Timetable> timetables) {
        this.timetables = timetables;
        return this;
    }
}

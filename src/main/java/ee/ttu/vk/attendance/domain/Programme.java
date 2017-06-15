package ee.ttu.vk.attendance.domain;

import ee.ttu.vk.attendance.enums.GroupType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Data
@EqualsAndHashCode(of = "id")
@Entity
public class Programme implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String language;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private GroupType groupType;

    @Column(name = "url_id")
    private Long ScheduleId;


    @OneToMany
    @Fetch(FetchMode.SUBSELECT)
    private List<Student> students;

    @OneToMany
    @Fetch(FetchMode.SUBSELECT)
    private List<Timetable> timetables;

    @Override
    public String toString() {
        return name;
    }
}
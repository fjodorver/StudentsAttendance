package ee.ttu.vk.attendance.domain;

import ee.ttu.vk.attendance.enums.GroupType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "programme")
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Programme programme = (Programme) o;
        return Objects.equals(id, programme.id);
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

    public Programme setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Programme setName(String name) {
        this.name = name;
        return this;
    }

    public String getLanguage(){
        return language;
    }
    public Programme setLanguage(String language){
        this.language = language;
        return this;
    }

    public GroupType getGroupType() {
        return groupType;
    }

    public Programme setGroupType(GroupType groupType) {
        this.groupType = groupType;
        return this;
    }

    public Long getScheduleId() {
        return ScheduleId;
    }

    public Programme setScheduleId(Long scheduleId) {
        ScheduleId = scheduleId;
        return this;
    }

    public List<Timetable> getTimetables() {
        return timetables;
    }

    public Programme setTimetables(List<Timetable> timetables) {
        this.timetables = timetables;
        return this;
    }

    public List<Student> getStudents() {
        return students;
    }

    public Programme setStudents(List<Student> students) {
        this.students = students;
        return this;
    }
}

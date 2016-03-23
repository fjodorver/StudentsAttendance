package ee.ttu.vk.sa.domain;

import ee.ttu.vk.sa.enums.GroupType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "\"group\"")
public class Group implements Serializable {

    @Id
    @SequenceGenerator(name="group_id_seq",sequenceName="group_id_seq", allocationSize = 1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator="group_id_seq")
    private Long id;

    private String name;

    private String language;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private GroupType groupType;

    @Column(name = "url_id")
    private Long ScheduleId;


//    @OneToMany
//    private List<Student> students;
//
//    @OneToMany
//    private List<Timetable> timetables;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return Objects.equals(id, group.id);
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

    public String getLanguage(){
        return language;
    }
    public Group setLanguage(String language){
        this.language = language;
        return this;
    }

    public GroupType getGroupType() {
        return groupType;
    }

    public Group setGroupType(GroupType groupType) {
        this.groupType = groupType;
        return this;
    }

    public Long getScheduleId() {
        return ScheduleId;
    }

    public Group setScheduleId(Long scheduleId) {
        ScheduleId = scheduleId;
        return this;
    }

//    public List<Timetable> getTimetables() {
//        return timetables;
//    }
//
//    public Group setTimetables(List<Timetable> timetables) {
//        this.timetables = timetables;
//        return this;
//    }
//
//    public List<Student> getStudents() {
//        return students;
//    }
//
//    public Group setStudents(List<Student> students) {
//        this.students = students;
//        return this;
//    }
}

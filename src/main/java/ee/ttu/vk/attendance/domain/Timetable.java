package ee.ttu.vk.attendance.domain;

import com.google.common.collect.Lists;
import ee.ttu.vk.attendance.OffsetDateTimeConverter;
import ee.ttu.vk.attendance.enums.Type;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "timetable")
@NamedEntityGraph(name = "timetable.detail", attributeNodes = {@NamedAttributeNode("subject"), @NamedAttributeNode("group"), @NamedAttributeNode("teacher")})
public class Timetable implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat
    @Convert(converter = OffsetDateTimeConverter.class)
    private ZonedDateTime start;

    @DateTimeFormat
    @Convert(converter = OffsetDateTimeConverter.class)
    @Column(name = "\"end\"")
    private ZonedDateTime end;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "timetable")
    private List<Attendance> attendances = Lists.newArrayList();

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type lessonType;

    @ManyToOne(fetch = FetchType.LAZY)
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    private Teacher teacher;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Timetable timetable = (Timetable) o;
        return Objects.equals(id, timetable.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public Timetable setId(Long id) {
        this.id = id;
        return this;
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public Timetable setStart(ZonedDateTime start) {
        this.start = start;
        return this;
    }

    public ZonedDateTime getEnd() {
        return end;
    }

    public Timetable setEnd(ZonedDateTime end) {
        this.end = end;
        return this;
    }

    public Type getLessonType() {
        return lessonType;
    }

    public Timetable setLessonType(Type lessonType) {
        this.lessonType = lessonType;
        return this;
    }

    public Subject getSubject() {
        return subject;
    }

    public Timetable setSubject(Subject subject) {
        this.subject = subject;
        return this;
    }

    public Group getGroup() {
        return group;
    }

    public Timetable setGroup(Group group) {
        this.group = group;
        return this;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Timetable setTeacher(Teacher teacher) {
        this.teacher = teacher;
        return this;
    }

    public List<Attendance> getAttendances() {
        return attendances;
    }

    public Timetable setAttendances(List<Attendance> attendances) {
        this.attendances = attendances;
        return this;
    }
}

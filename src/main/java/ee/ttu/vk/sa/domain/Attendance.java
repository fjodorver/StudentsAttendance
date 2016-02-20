package ee.ttu.vk.sa.domain;

import com.google.common.base.Objects;
import ee.ttu.vk.sa.enums.Type;
import ee.ttu.vk.sa.enums.Status;

import javax.persistence.*;
import java.io.Closeable;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by vadimstrukov on 2/12/16.
 */
@Entity
@Table(name = "attendance")
public class Attendance implements Serializable {
    @Id
    @SequenceGenerator(name="attendance_id_seq",sequenceName="attendance_id_seq", allocationSize=1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator="attendance_id_seq")
    private Long id;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Subject subject;

    @ManyToOne
    private Group group;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "lecture_type")
    private Type type;

    @Temporal(TemporalType.DATE)
    private Date date;

    public Long getId() {
        return id;
    }

    public Attendance setId(Long id) {
        this.id = id;
        return this;
    }

    public Student getStudent() {
        return student;
    }

    public Attendance setStudent(Student student) {
        this.student = student;
        return this;
    }

    public Subject getSubject() {
        return subject;
    }

    public Attendance setSubject(Subject subject) {
        this.subject = subject;
        return this;
    }

    public Group getGroup() {
        return group;
    }

    public Attendance setGroup(Group group) {
        this.group = group;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public Attendance setStatus(Status status) {
        this.status = status;
        return this;
    }

    public Type getType() {
        return type;
    }

    public Attendance setType(Type type) {
        this.type = type;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public Attendance setDate(Date date) {
        this.date = date;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attendance that = (Attendance) o;
        return Objects.equal(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

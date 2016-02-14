package ee.ttu.vk.sa.domain;

import ee.ttu.vk.sa.enums.Status;

import javax.persistence.*;
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

    @Enumerated(EnumType.STRING)
    private Status status;

    @Temporal(TemporalType.DATE)
    private Date date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

package ee.ttu.vk.attendance.domain;

import ee.ttu.vk.attendance.enums.Status;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by strukov on 3/7/16.
 */
@Entity
@Table(name = "attendance")
public class Attendance implements Serializable{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Timetable timetable;


    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status attendanceType;

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

    public Timetable getTimetable() {
        return timetable;
    }

    public Attendance setTimetable(Timetable timetable) {
        this.timetable = timetable;
        return this;
    }

    public Status getAttendanceType() {
        return attendanceType;
    }

    public Attendance setAttendanceType(Status attendanceType) {
        this.attendanceType = attendanceType;
        return this;
    }
}
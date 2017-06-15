package ee.ttu.vk.attendance.domain;

import ee.ttu.vk.attendance.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@NamedEntityGraph(name = "attendance.all", attributeNodes = {@NamedAttributeNode("student"), @NamedAttributeNode("timetable")})
public class Attendance implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Timetable timetable;


    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status = Status.INACTIVE;

    public Attendance(Student student, Timetable timetable) {
        this.student = student;
        this.timetable = timetable;
    }
}
package ee.ttu.vk.attendance.domain;

import com.google.common.collect.Lists;
import ee.ttu.vk.attendance.OffsetDateTimeConverter;
import ee.ttu.vk.attendance.enums.Type;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Entity
@NamedEntityGraph(name = "timetable.detail", attributeNodes = {@NamedAttributeNode("subject"), @NamedAttributeNode("programme"), @NamedAttributeNode("teacher")})
public class Timetable implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat
    @Convert(converter = OffsetDateTimeConverter.class)
    @Column(name = "_begin")
    private ZonedDateTime start;

    @DateTimeFormat
    @Convert(converter = OffsetDateTimeConverter.class)
    @Column(name = "_end")
    private ZonedDateTime end;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "timetable")
    private List<Attendance> attendances = Lists.newArrayList();

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type lessonType;

    @ManyToOne(fetch = FetchType.LAZY)
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    private Programme programme;

    @ManyToOne(fetch = FetchType.LAZY)
    private Teacher teacher;

    public Timetable(Subject subject, Programme programme) {
        this.subject = subject;
        this.programme = programme;
    }
}
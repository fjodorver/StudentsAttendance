package ee.ttu.vk.attendance.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Subject implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String name;

    @OneToMany(mappedBy = "subject")
    private List<Timetable> timetables;

    public Subject(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
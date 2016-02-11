package ee.ttu.vk.sa.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "teacher")
public class Teacher implements Serializable{
    @Id
    @SequenceGenerator(name="teacher_id_seq",sequenceName="teacher_id_seq", allocationSize=1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator="teacher_id_seq")
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public Teacher setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Teacher setName(String name) {
        this.name = name;
        return this;
    }
}

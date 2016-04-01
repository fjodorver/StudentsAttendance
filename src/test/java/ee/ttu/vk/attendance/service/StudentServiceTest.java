package ee.ttu.vk.attendance.service;

import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import ee.ttu.vk.attendance.domain.Programme;
import ee.ttu.vk.attendance.domain.Student;
import ee.ttu.vk.attendance.repository.ProgrammeRepository;
import ee.ttu.vk.attendance.repository.StudentRepository;
import ee.ttu.vk.attendance.service.impl.StudentServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

/**
 * Created by fjodor on 1.03.16.
 */
@RunWith(MockitoJUnitRunner.class)
public class StudentServiceTest {
    private List<Student> students;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private ProgrammeRepository programmeRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @Before
    public void setUp(){
        students = Lists.newArrayList();
        students.add(newStudent(0L, "131000", "Abdul", "Al Hazred", new Programme().setName("RDIR32")));
        students.add(newStudent(1L, "131001", "Vadim", "Strukov", new Programme().setName("RDIR32")));
        students.add(newStudent(2L, "131002", "Iron", "Man", new Programme().setName("RDIR31")));
    }

    @Test
    public void testSize() {
        given(studentRepository.count("131000", "Abdul Al Hazred", "RDIR32")).willReturn(1L);
        Long size = studentService.size(students.get(0));
        Assert.assertEquals(1, (long) size);
    }

    @Test
    public void testSave() throws Exception {
        List<Student> studentList = Lists.newArrayList();
        studentList.add(newStudent(null, "131000", "Abdul", "Al Hazred", new Programme().setName("RDIR61")));
        studentList.add(newStudent(null, "131001", "Vadim", "Strukov", new Programme().setName("RDIR61")));
        studentList.add(newStudent(null, "131002", "Iron", "Man", new Programme().setName("RDIR12")));
        studentList.add(newStudent(null, "131003", "Kino", "Man", new Programme().setName("RDIR12")));
        given(studentRepository.findByCode("131000")).willReturn(students.get(0));
        given(studentRepository.findByCode("131001")).willReturn(students.get(1));
        given(studentRepository.findByCode("131002")).willReturn(students.get(2));
        given(programmeRepository.findByName("RDIR61")).willReturn(new Programme().setId(0L).setName("RDIR61"));
        given(studentRepository.save(any(Student.class))).will(x -> x.getArguments()[0]);
        Student[] studentArray = Iterators.toArray(studentService.save(studentList).iterator(), Student.class);
        Assert.assertArrayEquals(Iterables.toArray(studentList, Student.class), studentArray);
        Assert.assertNotNull(studentArray[1].getId());
        Assert.assertNull(studentArray[3].getId());
        Assert.assertNotNull(studentArray[1].getProgramme().getId());
        Assert.assertNull(studentArray[3].getProgramme().getId());
    }

    private Student newStudent(Long id, String code, String firstname, String lastname, Programme programme){
        Student student = new Student();
        student.setId(id);
        student.setCode(code);
        student.setFullname(firstname + " " + lastname);
        student.setProgramme(programme);
        return student;
    }
}

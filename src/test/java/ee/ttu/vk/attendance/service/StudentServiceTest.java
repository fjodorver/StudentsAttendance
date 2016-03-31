package ee.ttu.vk.attendance.service;

import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import ee.ttu.vk.attendance.domain.Group;
import ee.ttu.vk.attendance.domain.Student;
import ee.ttu.vk.attendance.repository.GroupRepository;
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
    private GroupRepository groupRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @Before
    public void setUp(){
        students = Lists.newArrayList();
        students.add(newStudent(0L, "131000", "Abdul", "Al Hazred", new Group().setName("RDIR32")));
        students.add(newStudent(1L, "131001", "Vadim", "Strukov", new Group().setName("RDIR32")));
        students.add(newStudent(2L, "131002", "Iron", "Man", new Group().setName("RDIR31")));
    }

    @Test
    public void testSize() {
        given(studentRepository.count("131000", "Abdul", "Al Hazred", "RDIR32")).willReturn(1L);
        Long size = studentService.size(students.get(0));
        Assert.assertEquals(1, (long) size);
    }

    @Test
    public void testSave() throws Exception {
        List<Student> studentList = Lists.newArrayList();
        studentList.add(newStudent(null, "131000", "Abdul", "Al Hazred", new Group().setName("RDIR61")));
        studentList.add(newStudent(null, "131001", "Vadim", "Strukov", new Group().setName("RDIR61")));
        studentList.add(newStudent(null, "131002", "Iron", "Man", new Group().setName("RDIR12")));
        studentList.add(newStudent(null, "131003", "Kino", "Man", new Group().setName("RDIR12")));
        given(studentRepository.findByCode("131000")).willReturn(students.get(0));
        given(studentRepository.findByCode("131001")).willReturn(students.get(1));
        given(studentRepository.findByCode("131002")).willReturn(students.get(2));
        given(groupRepository.findByName("RDIR61")).willReturn(new Group().setId(0L).setName("RDIR61"));
        given(studentRepository.save(any(Student.class))).will(x -> x.getArguments()[0]);
        Student[] studentArray = Iterators.toArray(studentService.save(studentList).iterator(), Student.class);
        Assert.assertArrayEquals(Iterables.toArray(studentList, Student.class), studentArray);
        Assert.assertNotNull(studentArray[1].getId());
        Assert.assertNull(studentArray[3].getId());
        Assert.assertNotNull(studentArray[1].getGroup().getId());
        Assert.assertNull(studentArray[3].getGroup().getId());
    }

    private Student newStudent(Long id, String code, String firstname, String lastname, Group group){
        Student student = new Student();
        student.setId(id);
        student.setCode(code);
        student.setFirstname(firstname);
        student.setLastname(lastname);
        student.setGroup(group);
        return student;
    }
}

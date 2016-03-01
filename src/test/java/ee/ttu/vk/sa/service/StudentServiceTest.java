package ee.ttu.vk.sa.service;

import ee.ttu.vk.sa.domain.Group;
import ee.ttu.vk.sa.domain.Student;
import ee.ttu.vk.sa.repository.StudentRepository;
import ee.ttu.vk.sa.service.impl.StudentServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.given;

/**
 * Created by fjodor on 1.03.16.
 */
@RunWith(MockitoJUnitRunner.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @Test
    public void testSize() {
        Student student = new Student();
        student.setCode("131000").setFirstname("Abdul").setLastname("Al Hazred").setGroup(new Group().setName("RDIR32"));
        given(studentRepository.count("131000", "Abdul", "Al Hazred", "RDIR32")).willReturn(1L);
        given(studentRepository.count()).willReturn(1L);
        Long size = studentService.size(student);
        Assert.assertEquals(1, (long) size);
    }
}

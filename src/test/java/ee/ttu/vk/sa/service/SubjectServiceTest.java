package ee.ttu.vk.sa.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import ee.ttu.vk.sa.domain.Group;
import ee.ttu.vk.sa.domain.Subject;
import ee.ttu.vk.sa.domain.Teacher;
import ee.ttu.vk.sa.repository.GroupRepository;
import ee.ttu.vk.sa.repository.SubjectRepository;
import ee.ttu.vk.sa.repository.TeacherRepository;
import ee.ttu.vk.sa.service.impl.SubjectServiceImpl;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;

@RunWith(MockitoJUnitRunner.class)
public class SubjectServiceTest {

    List<Subject> subjects;


    @Mock
    private SubjectRepository subjectRepository;

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private SubjectServiceImpl subjectService;

    @Before
    public void setUp(){
        subjects = Lists.newArrayList();
        subjects.add(newSubject(null, "1", "Subject 1", new Teacher(), Lists.newArrayList(newGroup(null, "RDIR12"), newGroup(null, "RDIR22"))));
        subjects.add(newSubject(null, "2", "Subject 2", new Teacher(), Lists.newArrayList()));
        subjects.add(newSubject(null, "3", "Subject 3", new Teacher(), Lists.newArrayList()));
    }

    @Test
    public void testSave() throws Exception {
        given(subjectRepository.findByCode("1")).willReturn(subjects.get(0).setId(0L));
        given(subjectRepository.findByCode("2")).willReturn(subjects.get(1).setId(1L));
        given(subjectRepository.findByCode("3")).willReturn(subjects.get(2).setId(2L));
        given(groupRepository.findByName("RDIR12")).willReturn(newGroup(0L, "RDIR12"));
        given(subjectRepository.save(anyListOf(Subject.class))).will(x -> x.getArguments()[0]);
        List<Subject> subjectList = subjectService.save(subjects);
        Assert.assertEquals(3, Sets.newHashSet(subjectList).size());
        subjectList.forEach(x -> x.getGroups().forEach(y -> Assert.assertNotNull(y.getName())));
        Assert.assertNotNull(subjectList.get(0).getGroups().get(0).getId());
        Assert.assertNull(subjectList.get(0).getGroups().get(1).getId());
    }

    private Subject newSubject(Long id, String code, String name, Teacher teacher, List<Group> groups){
        Subject subject = new Subject();
        subject.setCode(code);
        subject.setId(id);
        subject.setName(name);
        subject.setTeacher(teacher);
        subject.setGroups(groups);
        return subject;
    }

    private Group newGroup(Long id, String name){
        return new Group().setId(id).setName(name);
    }
}

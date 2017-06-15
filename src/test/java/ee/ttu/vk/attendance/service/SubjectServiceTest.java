package ee.ttu.vk.attendance.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import ee.ttu.vk.attendance.domain.Programme;
import ee.ttu.vk.attendance.domain.Subject;
import ee.ttu.vk.attendance.domain.Timetable;
import ee.ttu.vk.attendance.repository.ProgrammeRepository;
import ee.ttu.vk.attendance.repository.SubjectRepository;
import ee.ttu.vk.attendance.repository.TeacherRepository;
import ee.ttu.vk.attendance.service.impl.SubjectServiceImpl;
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
    private ProgrammeRepository programmeRepository;

    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private SubjectServiceImpl subjectService;

    @Before
    public void setUp(){
        subjects = Lists.newArrayList();
        subjects.add(newSubject(null, "1", "Subject 1",  Lists.newArrayList()));
        subjects.add(newSubject(null, "2", "Subject 2",  Lists.newArrayList()));
        subjects.add(newSubject(null, "3", "Subject 3",  Lists.newArrayList()));
    }

    @Test
    public void testSave() throws Exception {
//        given(subjectRepository.findByCode("1")).willReturn(subjects.get(0).setId(0L));
//        given(subjectRepository.findByCode("2")).willReturn(subjects.get(1).setId(1L));
//        given(subjectRepository.findByCode("3")).willReturn(subjects.get(2).setId(2L));
        given(programmeRepository.findByName("RDIR12")).willReturn(newGroup(0L, "RDIR12"));
        given(subjectRepository.save(anyListOf(Subject.class))).will(x -> x.getArguments()[0]);
        List<Subject> subjectList = subjectService.save(subjects);

        Assert.assertEquals(3, Sets.newHashSet(subjectList).size());
        subjectList.forEach(x -> x.getTimetables().stream().map(Timetable::getProgramme).forEach(y -> Assert.assertNotNull(y.getName())));
    }

    private Subject newSubject(Long id, String code, String name, List<Timetable> timetables){
        Subject subject = new Subject();
        subject.setCode(code);
        subject.setId(id);
        subject.setName(name);
        subject.setTimetables(timetables);
//        subject.setTeacher(teacher);
//        subject.setGroups(groups);
        return subject;
    }

    private Programme newGroup(Long id, String name){
        return null;
//        return new Programme().setId(id).setName(name);
    }
}

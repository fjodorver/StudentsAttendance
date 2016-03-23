package ee.ttu.vk.sa.service.impl;

import com.google.common.collect.Lists;
import ee.ttu.vk.sa.domain.Group;
import ee.ttu.vk.sa.domain.Subject;
import ee.ttu.vk.sa.domain.Teacher;
import ee.ttu.vk.sa.domain.Timetable;
import ee.ttu.vk.sa.repository.TimetableRepository;
import ee.ttu.vk.sa.service.GroupService;
import ee.ttu.vk.sa.service.SubjectService;
import ee.ttu.vk.sa.service.TeacherService;
import ee.ttu.vk.sa.service.TimetableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TimetableServiceImpl implements TimetableService {

    @Autowired
    private TimetableRepository timetableRepository;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private GroupService groupService;

    @Override
    public List<Timetable> save(List<Timetable> timetables) {
        Map<String, Subject> subjectMap = timetables.stream().map(Timetable::getSubject).collect(Collectors.toMap(Subject::getCode, y -> y, (x, y) -> x));
        Map<String, Teacher> teacherMap = timetables.stream().map(Timetable::getTeacher).collect(Collectors.toMap(Teacher::getUsername, y -> y, (x, y) -> x));
        Map<String, Group> groupMap = timetables.stream().map(Timetable::getGroup).collect(Collectors.toMap(Group::getName, y -> y, (x, y) -> x));
        subjectService.saveAll(Lists.newArrayList(subjectMap.values()));
        teacherService.saveAll(Lists.newArrayList(teacherMap.values()));
        groupService.saveAll(Lists.newArrayList(groupMap.values()));
        for (Timetable timetable : timetables) {
            timetable.setGroup(Optional.ofNullable(groupMap.get(timetable.getGroup().getName())).orElse(timetable.getGroup()));
            timetable.setTeacher(Optional.ofNullable(teacherMap.get(timetable.getTeacher().getUsername())).orElse(timetable.getTeacher()));
            timetable.setSubject(Optional.ofNullable(subjectMap.get(timetable.getSubject().getCode())).orElse(timetable.getSubject()));
            Timetable temp = timetableRepository.find(timetable.getGroup(), timetable.getSubject(), timetable.getTeacher(), timetable.getStart(), timetable.getEnd());
            timetable.setId(Optional.ofNullable(temp).orElse(timetable).getId());
        }
        return timetableRepository.save(timetables);
    }

}

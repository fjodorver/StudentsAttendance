package ee.ttu.vk.attendance.service.impl;

import com.google.common.collect.Lists;
import ee.ttu.vk.attendance.domain.Group;
import ee.ttu.vk.attendance.domain.Subject;
import ee.ttu.vk.attendance.domain.Teacher;
import ee.ttu.vk.attendance.domain.Timetable;
import ee.ttu.vk.attendance.pages.filters.TimetableFilter;
import ee.ttu.vk.attendance.repository.TimetableRepository;
import ee.ttu.vk.attendance.service.GroupService;
import ee.ttu.vk.attendance.service.SubjectService;
import ee.ttu.vk.attendance.service.TeacherService;
import ee.ttu.vk.attendance.service.TimetableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
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
    public void save(List<Timetable> timetables) {
        List<Timetable> test = timetableRepository.findAll();
        Map<String, Subject> subjectMap = timetables.stream().map(Timetable::getSubject).collect(Collectors.toMap(Subject::getCode, y -> y, (x, y) -> x));
        Map<String, Teacher> teacherMap = timetables.stream().map(Timetable::getTeacher).collect(Collectors.toMap(Teacher::getUsername, y -> y, (x, y) -> x));
        Map<String, Group> groupMap = timetables.stream().map(Timetable::getGroup).collect(Collectors.toMap(Group::getName, y -> y, (x, y) -> x));
        subjectService.saveAll(Lists.newArrayList(subjectMap.values()));
        teacherService.saveAll(Lists.newArrayList(teacherMap.values()));
        groupService.save(Lists.newArrayList(groupMap.values()));
        for (Timetable timetable : timetables) {
            timetable.setGroup(Optional.ofNullable(groupMap.get(timetable.getGroup().getName())).orElse(timetable.getGroup()));
            timetable.setTeacher(Optional.ofNullable(teacherMap.get(timetable.getTeacher().getUsername())).orElse(timetable.getTeacher()));
            timetable.setSubject(Optional.ofNullable(subjectMap.get(timetable.getSubject().getCode())).orElse(timetable.getSubject()));
            test.stream()
                    .filter(x -> x.getTeacher().getFullname().equals(timetable.getTeacher().getFullname()) && x.getStart().equals(timetable.getStart()))
                    .findAny().ifPresent(x -> timetable.setId(x.getId()));
        }
        timetables.forEach(x -> timetableRepository.saveAndFlush(x));
    }

    @Override
    public List<Timetable> find(TimetableFilter filter, Pageable pageable) {
        ZonedDateTime dateTime = ZonedDateTime.ofInstant(filter.getDate().toInstant(), ZoneId.systemDefault());
        return timetableRepository.find(dateTime.withHour(0), dateTime.withHour(23), pageable);
    }

    @Override
    public long size(TimetableFilter filter) {
        ZonedDateTime dateTime = ZonedDateTime.ofInstant(filter.getDate().toInstant(), ZoneId.systemDefault());
        return timetableRepository.count(dateTime.withHour(0), dateTime.withHour(23));
    }
}

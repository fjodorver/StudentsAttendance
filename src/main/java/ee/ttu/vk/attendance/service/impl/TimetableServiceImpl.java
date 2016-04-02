package ee.ttu.vk.attendance.service.impl;

import com.google.common.collect.Lists;
import ee.ttu.vk.attendance.domain.Programme;
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
    public Timetable save(Timetable timetable) {
        return null;
    }

    @Override
    public List<Timetable> save(List<Timetable> timetables) {
        List<Timetable> tmpTimetable = timetableRepository.findAll();
        Map<String, Subject> subjectMap = timetables.stream().map(Timetable::getSubject).collect(Collectors.toMap(Subject::getCode, y -> y, (x, y) -> x));
        Map<String, Teacher> teacherMap = timetables.stream().map(Timetable::getTeacher).collect(Collectors.toMap(Teacher::getUsername, y -> y, (x, y) -> x));
        Map<String, Programme> groupMap = timetables.stream().map(Timetable::getProgramme).collect(Collectors.toMap(Programme::getName, y -> y, (x, y) -> x));
        subjectService.save(Lists.newArrayList(subjectMap.values()));
        teacherService.save(Lists.newArrayList(teacherMap.values()));
        groupService.save(Lists.newArrayList(groupMap.values()));
        for (Timetable timetable : timetables) {
            timetable.setProgramme(Optional.ofNullable(groupMap.get(timetable.getProgramme().getName())).orElse(timetable.getProgramme()));
            timetable.setTeacher(Optional.ofNullable(teacherMap.get(timetable.getTeacher().getUsername())).orElse(timetable.getTeacher()));
            timetable.setSubject(Optional.ofNullable(subjectMap.get(timetable.getSubject().getCode())).orElse(timetable.getSubject()));
            tmpTimetable.stream().filter(x -> compareTimetable(x, timetable)).findAny().ifPresent(x -> timetable.setId(x.getId()));
        }
        return timetableRepository.save(timetables);
    }

    @Override
    public List<Timetable> findAll(TimetableFilter filter, Pageable pageable) {
        if(filter.getDate() == null) return timetableRepository.findAll(filter.getTeacher());
        ZonedDateTime dateTime = ZonedDateTime.ofInstant(filter.getDate().toInstant(), ZoneId.systemDefault());
        return timetableRepository.find(dateTime.withHour(0), dateTime.withHour(23), filter.getTeacher(), pageable);
    }

    @Override
    public long size(TimetableFilter filter) {
        if(filter.getDate() == null) return timetableRepository.findAll(filter.getTeacher()).size();
        ZonedDateTime dateTime = ZonedDateTime.ofInstant(filter.getDate().toInstant(), ZoneId.systemDefault());
        return timetableRepository.count(dateTime.withHour(0), dateTime.withHour(23), filter.getTeacher());
    }
    private boolean compareTimetable(Timetable obj1, Timetable obj2){
        boolean teacher = obj1.getTeacher().getFullname().equals(obj2.getTeacher().getFullname());
        boolean start = obj1.getStart().toString().equals(obj2.getStart().toString());
        boolean end = obj1.getEnd().toString().equals(obj2.getEnd().toString());
        boolean programme = obj1.getProgramme().getName().equals(obj2.getProgramme().getName());
        return !(teacher && start && end && programme);
    }

    @Override
    public void clearAll() {
        timetableRepository.findAll().forEach(x->timetableRepository.delete(x));
    }
}

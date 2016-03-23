package ee.ttu.vk.sa.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import ee.ttu.vk.sa.domain.Group;
import ee.ttu.vk.sa.domain.Subject;
import ee.ttu.vk.sa.domain.Teacher;
import ee.ttu.vk.sa.domain.Timetable;
import ee.ttu.vk.sa.enums.GroupType;
import ee.ttu.vk.sa.enums.Type;
import ee.ttu.vk.sa.service.GroupService;
import ee.ttu.vk.sa.service.ScheduleService;
import ee.ttu.vk.sa.service.TimetableService;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.CalendarComponent;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.IOException;
import java.text.MessageFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Transactional
@EnableScheduling
public class ScheduleServiceImpl implements ScheduleService {

    private static final String GROUPS_URL = "https://ois.ttu.ee/portal/page?_pageid=35,435155&_dad=portal&_schema=PORTAL&e=-1&e_sem=162&a=1&b={0}&c=-1&d=-1&k=&q=neto&g=";
    private static final String SCHEDULE_URL = "https://ois.ttu.ee/pls/portal/tunniplaan.PRC_EXPORT_DATA?p_page=view_plaan&pn=i&pv=2&pn=e_sem&pv=162&pn=e&pv=-1&pn=b&pv={0}&pn=g&pv={1,number,#}&pn=is_oppejoud&pv=false&pn=q&pv=1";

    @Inject
    private CloseableHttpClient httpClient;

    @Inject
    private GroupService groupService;

    @Inject
    private TimetableService timetableService;

    @Override
    @Scheduled(cron = "*/30 * * * * *")
    public void updateGroups() throws IOException {
        Map<String, Group> groupMap = Maps.newHashMap();
        Pattern pattern = Pattern.compile("g=(\\w+)");
        for (int i = 1; i <= 2; i++) {
            HttpGet httpGet = new HttpGet(MessageFormat.format(GROUPS_URL, i));
            try (CloseableHttpResponse response = httpClient.execute(httpGet)){
                HttpEntity entity = response.getEntity();
                Document doc = Jsoup.parse(IOUtils.toString(entity.getContent()));
                for (Element span : doc.select("span").select("span:has(a)")) {
                    Matcher matcher = pattern.matcher(span.attr("onclick"));
                    if (matcher.find() && span.select("a").html().length() == 6) {
                        Group group = new Group().setName(span.select("a").html()).setGroupType(GroupType.values()[i-1]).setScheduleId(Long.valueOf(matcher.group(1)));
                        groupMap.put(group.getName(), group);
                    }
                }
                EntityUtils.consume(entity);
            }
        }
        groupService.save(Lists.newArrayList(groupMap.values()));
    }

    @Override
    @Scheduled(cron = "*/30 * * * * *")
    public void update() throws IOException, ParserException {
        List<Timetable> timetables = Lists.newArrayList();
        CalendarBuilder calendarBuilder = new CalendarBuilder();
        for (Group group : groupService.findAll().stream().filter(x -> x.getName().startsWith("R")).collect(Collectors.toList())) {
            HttpGet httpGet = new HttpGet(MessageFormat.format(SCHEDULE_URL, group.getGroupType().ordinal()+1, group.getScheduleId()));
            try (CloseableHttpResponse response = httpClient.execute(httpGet)){
                HttpEntity entity = response.getEntity();
                Calendar calendar = calendarBuilder.build(entity.getContent());
                ComponentList<CalendarComponent> componentList = calendar.getComponents(Component.VEVENT);
                for (CalendarComponent component : componentList) {
                    String description = component.getProperty(Property.DESCRIPTION).getValue();
                    String summary = component.getProperty(Property.SUMMARY).getValue();
                    Timetable timetable = new Timetable();
                    timetable.setStart(getDateTime(component.getProperty(Property.DTSTART).getValue()));
                    timetable.setEnd(getDateTime(component.getProperty(Property.DTEND).getValue()));
                    timetable.setGroup(group);
                    timetable.setTeacher(getTeacher(description));
                    timetable.setSubject(getSubject(summary));
                    timetable.setLessonType(getLessonType(summary));
                    timetables.add(timetable);
                }
                EntityUtils.consume(entity);
            }
        }
        timetableService.save(timetables);
    }

    private Subject getSubject(String summary) {
        Matcher code = Pattern.compile("(.*?)-").matcher(summary);
        Matcher name = Pattern.compile("-(.*?) -").matcher(summary);
        if(code.find() && name.find())
            return new Subject().setCode(code.group(1)).setName(name.group(1));
        return null;
    }

    private Type getLessonType(String summary){
        Matcher type = Pattern.compile("->   (.*?)$").matcher(summary);
        if(type.find()) {
            switch (type.group(1)) {
                case "loeng":
                    return Type.LECTURE;
                case "praktikum":
                    return Type.PRACTICE;
                default:
                    return Type.UNKNOWN;
            }
        }
        return Type.UNKNOWN;
    }

    private Teacher getTeacher(String description){
        Matcher fullname = Pattern.compile("  (.*?)\\n").matcher(description);
        Teacher teacher = new Teacher();
        if(fullname.find()){
            teacher = new Teacher().setFullname(fullname.group(1));
            teacher.setUsername(String.format("%1$s.%2$s", teacher.getFullname().split(" ")));
            teacher.setPassword("PASS");
        }
        return teacher;
    }

    private ZonedDateTime getDateTime(String datetime) {
        return ZonedDateTime.of(LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss")), ZoneId.of("Europe/Tallinn"));
    }
}
package ee.ttu.vk.sa.utils;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import ee.ttu.vk.sa.domain.Group;
import ee.ttu.vk.sa.domain.Subject;
import ee.ttu.vk.sa.domain.Teacher;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;


public class SubjectXlsParser extends XlsParser<Subject> {

    private Map<String, Subject> subjectMap;
    private Map<String, Group> groupMap;
    private static final int SUBJECT_CODE_CELL = 2;
    private static final int SUBJECT_NAME_CELL = 3;
    private static final int GROUP_NAME_CELL = 4;
    private static final int GROUP_LANGUAGE_CELL = 13;
    private static final int TEACHER_NAME_CELL = 12;

    public SubjectXlsParser() {
        groupMap = Maps.newHashMap();
        subjectMap = Maps.newHashMap();
        this.SHEET_NUMBER = 0;
        this.START_ROW_NUMBER = 10;
    }

    @Override
    public void parse(InputStream io) {
        try (POIFSFileSystem fileSystem = new POIFSFileSystem(io)) {
            HSSFWorkbook workbook = new HSSFWorkbook(fileSystem);
            HSSFSheet sheet = workbook.getSheetAt(SHEET_NUMBER);
            for (Row row : sheet) {
                Iterator<Cell> cellIterator = row.cellIterator();
                Subject subject = new Subject();
                Group group = new Group();
                Teacher teacher = new Teacher();
                if (row.getRowNum() > START_ROW_NUMBER) {
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        int columnIndex = cell.getColumnIndex();
                        switch (columnIndex) {
                            case SUBJECT_CODE_CELL:
                                subject.setCode((String) getCellValue(cell));
                                break;
                            case SUBJECT_NAME_CELL:
                                subject.setName((String) getCellValue(cell));
                                break;
                            case GROUP_NAME_CELL:
                                group.setName((String) getCellValue(cell));
                                break;
                            case GROUP_LANGUAGE_CELL:
                                group.setLanguage((String) getCellValue(cell));
                                break;
                            case TEACHER_NAME_CELL:
                                teacher.setName((String)getCellValue(cell));
                                break;
                        }
                    }
                    if(subjectMap.containsKey(subject.getCode()))
                        groupMap.put(group.getName(), group);
                    else{
                        if(groupMap.size() > 0){
                            Iterators.getLast(subjectMap.entrySet().iterator()).getValue().setGroups(Lists.newArrayList(groupMap.values()));
                            groupMap.clear();
                        }
                        groupMap.put(group.getName(), group);
                    }
                    subject.setGroups(Lists.newArrayList(groupMap.values()));
                    subject.setTeacher(teacher);
                    subjectMap.put(subject.getCode(), subject);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Subject> getElements() {
        return Lists.newArrayList(subjectMap.values());
    }

}

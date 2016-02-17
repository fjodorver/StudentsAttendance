package ee.ttu.vk.sa.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import ee.ttu.vk.sa.domain.Group;
import ee.ttu.vk.sa.domain.Subject;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;


public class XlsParser implements IParser<Subject> {

    private Map<String, Subject> subjectMap;
    private Map<String, Group> groupMap;

    public XlsParser() {
        groupMap = Maps.newHashMap();
        subjectMap = Maps.newHashMap();
    }

    @Override
    public void parse(InputStream io) {
        try (POIFSFileSystem fileSystem = new POIFSFileSystem(io)) {
            HSSFWorkbook workbook = new HSSFWorkbook(fileSystem);
            HSSFSheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                Iterator<Cell> cellIterator = row.cellIterator();
                Subject subject = new Subject();
                Group group = new Group();
                if (row.getRowNum() > 10) {
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        int columnIndex = cell.getColumnIndex();
                        switch (columnIndex) {
                            case 2:
                                subject.setCode((String) getCellValue(cell));
                                break;
                            case 3:
                                subject.setName((String) getCellValue(cell));
                                break;
                            case 4:
                                group.setName((String) getCellValue(cell));
                                break;
                            case 13:
                                group.setLanguage((String) getCellValue(cell));
                                break;
                        }
                    }
                    if(subjectMap.containsKey(subject.getCode()))
                        groupMap.put(group.getName(), group);
                    else{
                        if(groupMap.size() > 0){
                            subjectMap.entrySet().iterator().next().getValue().setGroups(Lists.newArrayList(groupMap.values()));
                            groupMap.clear();
                        }
                        groupMap.put(group.getName(), group);
                    }
                    subject.setGroups(Lists.newArrayList(groupMap.values()));
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

    private Object getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue();
            case Cell.CELL_TYPE_NUMERIC:
                return cell.getNumericCellValue();
        }
        return null;
    }
}

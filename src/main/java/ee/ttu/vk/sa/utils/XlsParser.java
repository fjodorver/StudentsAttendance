package ee.ttu.vk.sa.utils;

import com.google.common.collect.Lists;
import ee.ttu.vk.sa.domain.Group;
import ee.ttu.vk.sa.domain.Subject;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.*;


public class XlsParser implements IParser<Subject>, Serializable {

    private Set<Subject> subjects;
    private Set<Group> groups;

    public XlsParser() {
        groups = new HashSet<>();
        subjects = new HashSet<>();
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
                        }
                    }

                    Subject tmpSubject = subjects.stream().filter(x -> x.getCode().equals(subject.getCode())).findFirst().orElse(null);
                    if (tmpSubject != null) {
                        groups.add(group);
                    }
                    else{
                        if(groups.size() > 0){
                            subjects.iterator().next().setGroups(groups);
                            groups = new HashSet<>();
                        }
                        groups.add(group);
                        subject.setGroups(groups);
                    }
                    subjects.add(subject);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Subject> getElements() {
        return Lists.newArrayList(subjects);
    }

    @Override
    public String getExtension() {
        return ".xls";
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

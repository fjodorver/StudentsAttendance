package ee.ttu.vk.sa.utils;

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

    private List<Subject> subjects;
    private List<Group> groups;

    public XlsParser(){
        groups = new ArrayList<>();
        subjects = new ArrayList<>();
    }

    @Override
    public void parse(InputStream io) {
        try(POIFSFileSystem fileSystem = new POIFSFileSystem(io)){
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
                            case 1:
                                subject.setLect((String) getCellValue(cell));
                                break;
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
                    groups.add(group);
                    subjects.add(subject.setGroups(groups));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Subject> getElements() {
        return subjects;
    }

    @Override
    public String getExtension() {
        return ".xls";
    }

    private  Object getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue();
            case Cell.CELL_TYPE_NUMERIC:
                return cell.getNumericCellValue();
        }
        return null;
    }
}

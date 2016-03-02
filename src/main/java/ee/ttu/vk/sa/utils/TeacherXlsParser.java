package ee.ttu.vk.sa.utils;

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
import org.apache.wicket.authroles.authorization.strategies.role.Roles;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by vadimstrukov on 2/17/16.
 */
public class TeacherXlsParser extends XlsParser<Teacher> {

    private Map<String,Teacher> teacherMap;
    private PasswordEncryptor encryptor;

    private final int TEACHER_NAME_CELL = 2;
    private final int TEACHER_EMAIL_CELL = 1;

    public TeacherXlsParser(){
        teacherMap = Maps.newHashMap();
        encryptor = new PasswordEncryptor();
        this.SHEET_NUMBER = 1;
        this.START_ROW_NUMBER = 2;
    }

    @Override
    public void parse(InputStream io) {
        try (POIFSFileSystem fileSystem = new POIFSFileSystem(io)) {
            HSSFWorkbook workbook = new HSSFWorkbook(fileSystem);
            HSSFSheet sheet = workbook.getSheetAt(SHEET_NUMBER);
            for (Row row : sheet) {
                Iterator<Cell> cellIterator = row.cellIterator();
                Teacher teacher = new Teacher();
                if (row.getRowNum() > START_ROW_NUMBER) {
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        int columnIndex = cell.getColumnIndex();
                        switch (columnIndex) {
                            case TEACHER_NAME_CELL:
                                teacher.setName((String) getCellValue(cell));
                                break;
                            case TEACHER_EMAIL_CELL:
                                teacher.setEmail((String) getCellValue(cell));
                                break;
                        }
                    }
                    //Temporary
                    teacher.setPassword(encryptor.encryptPassword("pass")).setRole(Roles.USER);
                    //
                    teacherMap.put(teacher.getEmail(), teacher);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Teacher> getElements() {
        return Lists.newArrayList(teacherMap.values());
    }
}

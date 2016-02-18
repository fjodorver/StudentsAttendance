package ee.ttu.vk.sa.utils;

import org.apache.poi.ss.usermodel.Cell;

import java.io.InputStream;
import java.util.List;

/**
 * Created by vadimstrukov on 2/17/16.
 */
public abstract class XlsParser<T> implements IParser<T> {

    protected int SHEET_NUMBER;
    protected int START_ROW_NUMBER;

    @Override
    public abstract void parse(InputStream io);

    @Override
    public abstract List<T> getElements();

    public Object getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue();
            case Cell.CELL_TYPE_NUMERIC:
                return cell.getNumericCellValue();
        }
        return null;
    }
}

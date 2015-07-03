package cn.limw.summer.file.poi.xls;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cn.limw.summer.util.StringUtil;

/**
 * @author li
 * @version 1 (2014年10月27日 下午5:21:16)
 * @since Java7
 */
public class Xls {
    public static List<Object[]> read(String fileName, InputStream inputStream) {
        try {
            return fromXlsFile(fileName, inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Object[]> fromXlsFile(String fileName, InputStream inputStream) throws IOException {
        Workbook wbk = null;
        if (fileName.toLowerCase().lastIndexOf(".xlsx") > 0) {
            wbk = new XSSFWorkbook(inputStream);
        } else {
            wbk = new HSSFWorkbook(inputStream);
        }
        List<Object[]> list = new ArrayList<Object[]>();
        int sheetCount = wbk.getNumberOfSheets();
        if (sheetCount > 0) {
            Sheet sheet = wbk.getSheetAt(0);
            int rows = sheet.getPhysicalNumberOfRows();
            for (int i = 0; i < rows; i++) {
                Row row = sheet.getRow(i);
                int cellNum = row.getLastCellNum();
                Object[] cellObj = new Object[cellNum];
                for (int j = 0; j < cellNum; j++) {
                    Cell cell = row.getCell(j);
                    if (cell != null) {
                        if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
                            Date theDate = cell.getDateCellValue();
                            cellObj[j] = theDate;
                        } else {
                            cellObj[j] = cell.getRichStringCellValue().getString();
                        }
                    } else {
                        cellObj[j] = null;
                    }
                }
                list.add(cellObj);
            }
        }
        return list;
    }

    public static InputStream fromList(List<String> titleList, List<String[]> dataList) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("sheet0");
        int rowNo = 0;
        if (titleList != null && titleList.size() > 0) {
            HSSFRow titlerow = sheet.createRow(rowNo);
            for (int i = 0, length = titleList.size(); i < length; i++) {
                HSSFCell cell = titlerow.createCell(i);
                cell.setCellValue(titleList.get(i));
            }
            rowNo++;
        }

        for (int i = 0, listLength = dataList.size(); i < listLength; i++) {
            HSSFRow datarow = sheet.createRow(rowNo);
            datarow.setHeight((short) 400);
            for (int j = 0; j < dataList.get(i).length; j++) {
                HSSFCell cell = datarow.createCell(j);
                sheet.setColumnWidth(j, 6000);
                String value = dataList.get(i)[j];
                if (!StringUtil.isEmpty(value)) {
                    int length = value.length();
                    if (length >= 32767) {
                        cell.setCellValue(value.substring(0, 32767));
                    } else {
                        cell.setCellValue(value);
                    }
                } else {
                    cell.setCellValue("");
                }
            }
            rowNo++;
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        byte[] b = out.toByteArray();
        out.close();
        return new ByteArrayInputStream(b);
    }

    public static HSSFWorkbook newHSSFWorkbook(InputStream inputStream) {
        try {
            return new HSSFWorkbook(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
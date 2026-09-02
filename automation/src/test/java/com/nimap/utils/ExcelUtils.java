package com.nimap.utils;

import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads rows from an Excel sheet and returns them as Object[][]
 * so they can be fed directly into a TestNG @DataProvider.
 * This is how "parametrization" for Login / Add Customer is implemented.
 */
public class ExcelUtils {

    public static Object[][] getData(String filePath, String sheetName) {
        List<Object[]> rows = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            int totalRows = sheet.getLastRowNum();
            int totalCols = sheet.getRow(0).getLastCellNum();

            // row 0 = header, so data starts from row 1
            for (int i = 1; i <= totalRows; i++) {
                Row row = sheet.getRow(i);
                Object[] rowData = new Object[totalCols];
                for (int j = 0; j < totalCols; j++) {
                    Cell cell = row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cell.setCellType(CellType.STRING);
                    rowData[j] = cell.getStringCellValue();
                }
                rows.add(rowData);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read Excel file: " + filePath, e);
        }

        return rows.toArray(new Object[0][0]);
    }
}

package com.oskarkraak.bundestagvotescraper;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class XLSX {

    private final XSSFWorkbook workbook;

    /**
     * This constructor will instantiate an XLSX object with a workbook.
     * If you want to create an XLSX object from a XLSX file, use the <code>XLSX.read(String path)</code> method
     *
     * @param workbook is the workbook of the XLSX file
     */
    public XLSX(XSSFWorkbook workbook) {
        this.workbook = workbook;
    }

    public static XLSX read(String path) throws IOException {
        FileInputStream inputStream = new FileInputStream(path);
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        return new XLSX(workbook);
    }

    public void write(String path) throws IOException {
        FileOutputStream out = new FileOutputStream(path);
        workbook.write(out);
    }

    public static XLSX concatenate(XLSX[] files) {
        XSSFWorkbook mergedWorkbook = new XSSFWorkbook();
        XSSFSheet mergedSheet = mergedWorkbook.createSheet();
        int fileStartRowNum = 0;
        for (int i = 0; i < files.length; i++) {
            XSSFSheet sheet = files[i].workbook.getSheetAt(0);
            for (Row row : sheet) {
                Row mergedRow = mergedSheet.createRow(fileStartRowNum + row.getRowNum());
                for (Cell cell : row) {
                    Cell newCell = mergedRow.createCell(cell.getColumnIndex(), cell.getCellType());
                    copyCell(cell, newCell);
                }
            }
            fileStartRowNum += sheet.getLastRowNum() + 1;
        }
        return new XLSX(mergedWorkbook);
    }

    private static void copyCell(Cell oldCell, Cell newCell) {
        switch (oldCell.getCellType()) {
            case STRING:
                newCell.setCellValue(oldCell.getRichStringCellValue());
                break;
            case NUMERIC:
                newCell.setCellValue(oldCell.getNumericCellValue());
                break;
            case BLANK:
                newCell.setBlank();
                break;
            case BOOLEAN:
                newCell.setCellValue(oldCell.getBooleanCellValue());
                break;
            case ERROR:
                newCell.setCellErrorValue(oldCell.getErrorCellValue());
                break;
            case FORMULA:
                newCell.setCellFormula(oldCell.getCellFormula());
                break;
            default:
                break;
        }
    }

    public XSSFWorkbook getWorkbook() {
        return workbook;
    }

}

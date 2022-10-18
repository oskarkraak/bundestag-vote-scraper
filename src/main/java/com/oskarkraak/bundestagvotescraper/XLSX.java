package com.oskarkraak.bundestagvotescraper;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class XLSX {

    private final XSSFWorkbook workbook;

    /**
     * This constructor will instantiate an XLSX object with a workbook.
     * If you want to create an XLSX object from a XLSX file, use the <code>XLSX.read(String path)</code> method.
     *
     * @param workbook is the workbook of the XLSX file
     */
    public XLSX(XSSFWorkbook workbook) {
        this.workbook = workbook;
    }

    /**
     * Read a .xlsx file.
     *
     * @param path is the file path (including name) of the file
     * @return A new XLSX instance containing the .xlsx file
     * @throws IOException if the file cannot be found or if reading the file fails
     */
    public static XLSX read(String path) throws IOException {
        FileInputStream inputStream = new FileInputStream(path);
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        return new XLSX(workbook);
    }

    /**
     * Download a .xlsx file from a URL.
     *
     * @param urlString is the URL of the file
     * @return A new XLSX instance containing the .xlsx file
     * @throws IOException if the URL is malformed or an I/O exception occurs
     */
    public static XLSX download(String urlString) throws IOException {
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();
        XSSFWorkbook workbook = new XSSFWorkbook(connection.getInputStream());
        return new XLSX(workbook);
    }

    /**
     * Writes an .xlsx file with the content of this XLSX instance.
     *
     * @param path is the file path (including name) of the file
     * @throws IOException if writing the file fails
     */
    public void write(String path) throws IOException {
        FileOutputStream out = new FileOutputStream(path);
        workbook.write(out);
    }

    /**
     * Concatenates the contents of an array of XLSX instances.
     * The contents will be concatenated in the order in which they are in the array.
     *
     * @param files contains the XLSX instances that should be concatenated
     * @return A new XLSX instance that contains the concatenated contents of the given XLSX instances
     */
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

    /**
     * @return The XSSFWorkbook of this XLSX instance
     */
    public XSSFWorkbook getWorkbook() {
        return workbook;
    }

}




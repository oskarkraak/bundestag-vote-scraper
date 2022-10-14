package com.oskarkraak.bundestagvotescraper;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;

public class XLSX {

    private XSSFWorkbook workbook;

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
        return null;
    }

    public void write(String path) throws IOException {
    }

    public static XLSX merge(XLSX[] files) {
        return null;
    }

    public XSSFWorkbook getWorkbook() {
        return workbook;
    }

}

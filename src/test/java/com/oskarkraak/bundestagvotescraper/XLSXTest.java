package com.oskarkraak.bundestagvotescraper;

import org.apache.poi.ss.usermodel.Cell;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class XLSXTest {

    // Utility

    XLSX loadTestFile() {
        try {
            return XLSX.read(getClass().getClassLoader().getResource("TestFile.xlsx").getPath());
        } catch (IOException e) {
            e.printStackTrace();
            fail();
            return null;
        }
    }

    Cell getCell(XLSX xlsx, int row, int col) {
        return xlsx.getWorkbook().getSheetAt(0).getRow(row).getCell(col);
    }

    // XLSX.read(String path)

    @Test
    void readingMissingFileShouldThrowIOException() {
        assertThrows(IOException.class, () -> XLSX.read("/some/wrong/path.xlsx"));
    }

    @Test
    void readingExistingFileShouldWork() {
        assertDoesNotThrow(() -> {
            XLSX.read(getClass().getClassLoader().getResource("TestFile.xlsx").getPath());
        });
    }

    @Test
    void A1ShouldContainA1() {
        String value = getCell(loadTestFile(), 0, 0).getStringCellValue();
        assertEquals("A1", value);
    }

    @Test
    void D1ShouldContainEtc() {
        String value = getCell(loadTestFile(), 0, 3).getStringCellValue();
        assertEquals("etc", value);
    }

    @Test
    void F1ShouldBeNull() {
        assertNull(getCell(loadTestFile(), 0, 5));
    }

    @Test
    void A2ShouldBeNull() {
        assertNull(loadTestFile().getWorkbook().getSheetAt(0).getRow(1));
    }

    @Test
    void A3ShouldContain100() {
        double value = getCell(loadTestFile(), 2, 0).getNumericCellValue();
        assertEquals(100.0, value);
    }

    // xlsx.write(String path)

    // Tests inserting cells
    @Test
    void afterWritingF5ShouldContainAdded() {
        XLSX xlsx = loadTestFile();
        xlsx.getWorkbook().getSheetAt(0).createRow(4).createCell(5).setCellValue("Added");
        String path;
        try {
            path = File.createTempFile("xlsxTest-", "").getPath();
            xlsx.write(path);
            xlsx = XLSX.read(path);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Could not create temp file");
            return;
        }
        String value = xlsx.getWorkbook().getSheetAt(0).getRow(0).getCell(0).getStringCellValue();
        assertEquals("A1", value);
        value = xlsx.getWorkbook().getSheetAt(0).getRow(4).getCell(5).getStringCellValue();
        assertEquals("Added", value);
    }

    // Tests overriding cells
    @Test
    void afterWritingA1ShouldContainChanged() {
        XLSX xlsx = loadTestFile();
        xlsx.getWorkbook().getSheetAt(0).createRow(0).createCell(0).setCellValue("Changed");
        String path;
        try {
            path = File.createTempFile("xlsxTest-", ".xlsx").getPath();
            xlsx.write(path);
            xlsx = XLSX.read(path);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Could not create temp file");
            return;
        }
        String value = xlsx.getWorkbook().getSheetAt(0).getRow(0).getCell(0).getStringCellValue();
        assertEquals("Changed", value);
    }

    // XSLX.merge(XLSX[] files)

    @Test
    void afterMergingA1ShouldContainA1() {
        XLSX xlsx = XLSX.concatenate(new XLSX[]{loadTestFile(), loadTestFile()});
        String value = getCell(xlsx, 0, 0).getStringCellValue();
        assertEquals("A1", value);
    }

    @Test
    void afterMergingE4ShouldContainPp() {
        XLSX xlsx = XLSX.concatenate(new XLSX[]{loadTestFile(), loadTestFile()});
        String value = getCell(xlsx, 3, 4).getStringCellValue();
        assertEquals("pp", value);
    }

    @Test
    void afterMergingC6ShouldContain2() {
        XLSX xlsx = XLSX.concatenate(new XLSX[]{loadTestFile(), loadTestFile()});
        double value = getCell(xlsx, 5, 2).getNumericCellValue();
        assertEquals(2.0, value);
    }

    @Test
    void afterMergingA5ShouldBeNull() {
        XLSX xlsx = XLSX.concatenate(new XLSX[]{loadTestFile(), loadTestFile()});
        assertNull(xlsx.getWorkbook().getSheetAt(0).getRow(4));
    }

    @Test
    void afterMergingA4ShouldContainA1() {
        XLSX xlsx = XLSX.concatenate(new XLSX[]{loadTestFile(), loadTestFile(), loadTestFile()});
        String value = getCell(xlsx, 3, 0).getStringCellValue();
        assertEquals("A1", value);
    }

    @Test
    void afterMergingA12ShouldContain100() {
        XLSX xlsx = XLSX.concatenate(new XLSX[]{loadTestFile(), loadTestFile(), loadTestFile(), loadTestFile()});
        double value = getCell(xlsx, 11, 0).getNumericCellValue();
        assertEquals(100.0, value);
    }

    @Test
    void afterMergingA11ShouldBeNull() {
        XLSX xlsx = XLSX.concatenate(new XLSX[]{loadTestFile(), loadTestFile()});
        assertNull(xlsx.getWorkbook().getSheetAt(0).getRow(10));
    }

}
package com.oskarkraak.bundestagvotescraper;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;

import static org.junit.jupiter.api.Assertions.*;

public class HTMLTest {

    private HTML loadHTML() {
        StringBuilder html = new StringBuilder();
        try {
            FileReader fr = new FileReader(getClass().getClassLoader().getResource("TestFile.xlsx").getPath());
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null)
                html.append(line);
            br.close();
            return new HTML(html.toString());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            return null;
        }
    }

    private static final String HTML_STRING_1 = "            <td data-th=\"Veröffentlichung\">\n" +
            "                <p>\n" +
            "                    4. Dezember 2009</p>\n" +
            "            </td>\n" +
            "            <td data-th=\"Thema\">\n" +
            "                <p>\n" +
            "                </p>\n" +
            "            </td>";

    private static final String HTML_STRING_2 = "            <th><p>Veröffentlichung</p></th>\n" +
            "            <th><p>Thema</p></th>\n" +
            "            <th><p>Dokumentname</p></th>\n" +
            "            <th><p>Dokumenttyp</p></th>";

    @Test
    void getTagsTest1() {
        // Length
        HTML html = new HTML(HTML_STRING_1);
        HTML.Tag[] tags = html.getTags();
        assertEquals(2, tags.length);
        // Content
        String tagContent1 = "<p>\n" +
                "                    4. Dezember 2009</p>";
        String tagContent2 = "<p>\n" +
                "                </p>";
        assertEquals(tagContent1, tags[0].getContent().toString());
        assertEquals(tagContent2, tags[1].getContent().toString());
    }

    @Test
    void getTagsTest2() {
        // Length
        HTML html = new HTML(HTML_STRING_2);
        HTML.Tag[] tags = html.getTags();
        assertEquals(4, html.getTags().length);
        // Content
        String tagContent1 = "<p>Veröffentlichung</p>";
        String tagContent2 = "<p>Thema</p>";
        String tagContent3 = "<p>Dokumentname</p>";
        String tagContent4 = "<p>Dokumenttyp</p>";
        assertEquals(tagContent1, tags[0].getContent().toString());
        assertEquals(tagContent2, tags[1].getContent().toString());
        assertEquals(tagContent3, tags[2].getContent().toString());
        assertEquals(tagContent4, tags[3].getContent().toString());
    }

    @Test
    void missingTagPropertyShouldReturnNull() {
        HTML html = new HTML(HTML_STRING_1);
        assertNull(html.getTags()[0].getProperty("th"));
    }

    @Test
    void getTagPropertyTest() {
        HTML html = loadHTML();
        assertEquals("809", html.getTags()[0].getProperty("data-hits"));
        assertEquals("bt-slide col-xs-12 bt-standard-content", html.getTags()[1].getProperty("class"));
    }

    @Test
    void emptyTagContentShouldBeEmpty() {
        HTML html = new HTML(HTML_STRING_1);
        String content = html.getTags()[1].getContent().getTags()[0].getContent().toString();
        assertEquals("", content);
    }

    @Test
    void getOpeningTest() {
        HTML html = new HTML(HTML_STRING_1);
        assertEquals("<td data-th=\"Veröffentlichung\">", html.getTags()[0].getOpening());
    }

}

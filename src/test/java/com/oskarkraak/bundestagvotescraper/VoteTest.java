package com.oskarkraak.bundestagvotescraper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class VoteTest {

    @Test
    void creatingVoteShouldNotThrowErrors() {
        assertDoesNotThrow(() -> new Vote("", "", null));
    }

    @Test
    void emptyPublishingDateShouldHaveEmptyDate() {
        String des = "Ergebnis der Namentlichen Abstimmung zur Sicherheitspräsenz im Kosovo";
        String date = new Vote("", des, null).getDate();
        assertEquals("", date);
    }

    @Test
    void emptyPublishingDateShouldHaveDescriptionAsName() {
        String des = "Ergebnis der Namentlichen Abstimmung zur Sicherheitspräsenz im Kosovo";
        String name = new Vote("", des, null).getName();
        assertEquals(des, name);
    }

    @Test
    void dateShouldBeExtractedFromDescription() {
        String des = "09.12.2020: Beschlussempfehlung des Haushaltsausschusses (hier: Geschäftsbereich Bundeskanzlerin und Bundeskanzleramt)";
        String date = new Vote("9. Dezember 2020", des, null).getDate();
        assertEquals("09.12.2020", date);
    }

    @Test
    void nameShouldBeExtractedFromDescription() {
        String des = "09.12.2020: Beschlussempfehlung des Haushaltsausschusses (hier: Geschäftsbereich Bundeskanzlerin und Bundeskanzleramt)";
        String name = new Vote("9. Dezember 2020", des, null).getName();
        assertEquals("Beschlussempfehlung des Haushaltsausschusses (hier: Geschäftsbereich Bundeskanzlerin und Bundeskanzleramt", name);
    }


}

package com.oskarkraak.bundestagvotescraper;

public class Vote {

    private final String date;
    private final String name;
    private final String publishingDate;
    private final XLSX results;

    public Vote(String publishingDate, String documentDescription, XLSX results) {
        this.publishingDate = publishingDate;
        this.results = results;
        date = getDateFromDescription(documentDescription);
        name = getNameFromDescription(documentDescription);
    }

    public Vote(String publishingDate, String date, String name, XLSX results) {
        this.date = date;
        this.name = name;
        this.publishingDate = publishingDate;
        this.results = results;
    }

    private String getDateFromDescription(String description) {
        // TODO
        return "";
    }

    private String getNameFromDescription(String description) {
        // TODO
        return "";
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getPublishingDate() {
        return publishingDate;
    }

    public XLSX getResults() {
        return results;
    }

}

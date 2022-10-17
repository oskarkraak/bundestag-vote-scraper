package com.oskarkraak.bundestagvotescraper;

public class HTML {

    String html;

    /**
     * Creates a new HTML instance from HTML code.
     *
     * @param html is a string containing the HTML code
     */
    public HTML(String html) {
        this.html = html;
    }

    /**
     * Connects to a website and retrieves its HTML code.
     *
     * @param url is the URL of the website
     * @return A HTML instance containing the website's HTML code
     */
    public static HTML get(String url) {
        // TODO
        return null;
    }

    /**
     * Gives an array containing all tag that are inside no other tags.
     * The Strings will contain the tag's contents, i.e. text or other tags.
     *
     * @return A String[] where each String contains one tag and its contents
     */
    public String[] getRootTags() {
        // TODO
        return null;
    }

    /**
     * Extracts the contents of a tag, including texts and other tags.
     * Will return the contents for the first match in this HTML instance.
     *
     * @param tag is a String containing the tag and the tag's contents
     * @return A String containing the contents of the tag
     */
    public String getTagContent(String tag) {
        // TODO
        return "";
    }

    /**
     * Extracts the value of a root tag's property.
     * Will return the value for the first match in this HTML instance.
     *
     * @param propertyName is the property which should be returned
     * @return A String containing the value of the property
     */
    public String getRootTagProperty(String propertyName) {
        // TODO
        return "";
    }

}

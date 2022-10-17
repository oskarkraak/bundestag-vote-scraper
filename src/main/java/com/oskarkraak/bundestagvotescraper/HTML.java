package com.oskarkraak.bundestagvotescraper;

public class HTML {

    private String html;

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
     * Gives an array containing all tags that are inside no other tags.
     *
     * @return A Tag[] where the tags also contain their contents
     */
    public Tag[] getTags() {
        // TODO
        return null;
    }

    @Override
    public String toString() {
        // TODO
        return null;
    }

    public static class Tag {

        private String html;

        private Tag(String html) {
            this.html = html;
        }

        /**
         * Extracts the value of a property.
         *
         * @param propertyName is the property which should be returned
         * @return A String containing the value of the property or null if the property does not exist
         */
        public String getProperty(String propertyName) {
            // TODO
            return null;
        }

        /**
         * Extracts the opening tag from this tag.
         *
         * @return A String containing the opening tag
         */
        public String getOpening() {
            // TODO
            return null;
        }

        /**
         * Extracts the contents of this tag, including texts and other tags.
         *
         * @return A new HTML instance containing the contents of this tag
         */
        public HTML getContent() {
            // TODO
            return null;
        }

    }

}

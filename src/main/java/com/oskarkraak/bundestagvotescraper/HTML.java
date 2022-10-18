package com.oskarkraak.bundestagvotescraper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class HTML {

    private final String html;

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
     * @param urlString is the URL of the website
     * @return A new HTML instance containing the website's HTML code
     * @throws IOException if the URL is malformed or an I/O exception occurs
     */
    public static HTML get(String urlString) throws IOException {
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder html = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null)
            html.append(line);
        in.close();
        return new HTML(html.toString());
    }

    /**
     * Gives an array containing all tags that are inside no other tags.
     * Warning: Will not work if the HTML contains void elements other than "br" and "br/".
     *
     * @return A Tag[] where the tags also contain their contents
     */
    public Tag[] getTags() {
        String html = this.html.replace("<br>", "\n")
                .replace("<br/>", "\n"); // Remove any br tags that would break the stack
        ArrayList<Tag> tags = new ArrayList<>();
        StringBuilder tag = new StringBuilder();
        int stack = 0;
        for (int i = 0; i < html.length(); i++) {
            char c = html.charAt(i);
            tag.append(c);
            // Manage stack
            if (c == '<') {
                if (html.charAt(i + 1) != '/')
                    stack++;
                else
                    stack--;
            } else if (c == '>') {
                if (stack <= 0) {
                    // Start new tag
                    tags.add(new Tag(tag.toString()));
                    tag = new StringBuilder();
                }
            }
        }
        // Convert to array
        Tag[] result = new Tag[tags.size()];
        for (int i = 0; i < tags.size(); i++)
            result[i] = tags.get(i);
        return result;
    }

    @Override
    public String toString() {
        return html.trim();
    }

    public static class Tag {

        private final String html;

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
            String opening = getOpening();
            int start = opening.indexOf(" " + propertyName + "=\"");
            if (start == -1)
                return null;
            start += propertyName.length() + 3;
            int end = -1;
            for (int i = start; i < opening.length(); i++) {
                if (html.charAt(i) == '"') {
                    end = i;
                    break;
                }
            }
            return opening.substring(start, end);
        }

        /**
         * Extracts the opening tag from this tag.
         *
         * @return A String containing the opening tag
         */
        public String getOpening() {
            int start = -1;
            int end = -2;
            for (int i = 0; i < html.length(); i++) {
                if (start == -1) {
                    if (html.charAt(i) == '<')
                        start = i;
                } else {
                    if (html.charAt(i) == '>') {
                        end = i;
                        break;
                    }
                }
            }
            return html.substring(start, end + 1);
        }

        /**
         * Extracts the contents of this tag, including texts and other tags.
         * Warning: Will not work if the HTML contains void elements other than "br" and "br/".
         *
         * @return A new HTML instance containing the contents of this tag
         */
        public HTML getContent() {
            String html = this.html.replace("<br>", "\n")
                    .replace("<br/>", "\n"); // Remove any br tags that would break the stack
            int start = html.indexOf(getOpening()) + getOpening().length();
            int end = -1;
            // Iterate through the HTML, skipping this tag's opening
            int stack = 1;
            for (int i = start; i < html.length(); i++) {
                if (html.charAt(i) == '<') {
                    // Manage stack
                    if (html.charAt(i + 1) != '/')
                        stack++;
                    else
                        stack--;
                    // Determine end
                    if (stack <= 0) {
                        end = i;
                        break;
                    }
                }
            }
            return new HTML(html.substring(start, end));
        }

        @Override
        public String toString() {
            return html;
        }

    }

}

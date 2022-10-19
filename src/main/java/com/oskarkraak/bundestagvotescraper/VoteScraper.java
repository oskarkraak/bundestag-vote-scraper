package com.oskarkraak.bundestagvotescraper;

import java.io.IOException;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class VoteScraper implements Iterable<Vote> {

    private static final String BUNDESTAG_DOMAIN = "https://www.bundestag.de";
    private static final String URL = BUNDESTAG_DOMAIN + "/ajax/filterlist/de/parlament/plenum/abstimmung/liste/462112-462112?limit={LIMIT}&noFilterSet={NO_FILTER_SET}&offset={OFFSET}";
    private static final int LIMIT = 30;
    private static final boolean NO_FILTER_SET = true;

    @Override
    public Iterator<Vote> iterator() {
        return new VoteIterator();
    }

    private static class VoteIterator implements Iterator<Vote> {

        private final int totalEntries;
        private int currentEntry;
        Vote[] results;

        private VoteIterator() {
            currentEntry = -1;
            totalEntries = Integer.parseInt(getHTML().getTags()[0].getProperty("data-hits"));
        }

        @Override
        public boolean hasNext() {
            return currentEntry < totalEntries - 1;
        }

        @Override
        public Vote next() {
            currentEntry++;
            if (currentEntry % LIMIT == 0) {
                HTML html = getHTML();
                if (totalEntries != Integer.parseInt(html.getTags()[0].getProperty("data-hits")))
                    throw new ConcurrentModificationException("The number of votes on the website has changed whilst iterating");
                results = getVotes(html);
            }
            return results[currentEntry % LIMIT];
        }

        private HTML getHTML() {
            HTML html = null;
            try {
                html = HTML.get(currentUrl());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return html;
        }

        private Vote[] getVotes(HTML html) {
            Vote[] votes = new Vote[LIMIT];
            // Parse HTML
            HTML.Tag[] voteTags = html.getTags()[1]
                    .getContent().getTags()[0]
                    .getContent().getTags()[1]
                    .getContent().getTags();
            for (int i = 0; i < voteTags.length; i++) {
                HTML.Tag[] voteTag = voteTags[i].getContent().getTags();
                String publishingDate = voteTag[0]
                        .getContent().getTags()[0]
                        .getContent().toString();
                String documentDescription = voteTag[2]
                        .getContent().getTags()[0]
                        .getContent().getTags()[0]
                        .getContent().getTags()[0]
                        .getContent().toString();
                HTML.Tag[] downloadLinks = voteTag[2]
                        .getContent().getTags()[0]
                        .getContent().getTags()[1]
                        .getContent().getTags();
                String xlsxLink = null;
                for (HTML.Tag link : downloadLinks)
                    if (link.getContent().getTags()[0].getProperty("title").startsWith("XLSX"))
                        xlsxLink = link.getContent().getTags()[0].getProperty("href");
                XLSX results;
                if (xlsxLink == null) {
                    results = null;
                } else {
                    try {
                        results = XLSX.download(BUNDESTAG_DOMAIN + xlsxLink);
                    } catch (IOException e) {
                        results = null;
                        e.printStackTrace();
                    }
                }
                votes[i] = new Vote(publishingDate, documentDescription, results);
            }
            return votes;
        }

        private String currentUrl() {
            int offset = currentEntry - (currentEntry % LIMIT);
            return URL.replace("{LIMIT}", Integer.toString(LIMIT))
                    .replace("{NO_FILTER_SET}", Boolean.toString(NO_FILTER_SET))
                    .replace("{OFFSET}", Integer.toString(offset));
        }

    }

}

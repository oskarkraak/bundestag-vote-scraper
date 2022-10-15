package com.oskarkraak.bundestagvotescraper;

import java.util.Iterator;

public class VoteScraper implements Iterable<Vote> {

    private static final String URL = "https://www.bundestag.de/ajax/filterlist/de/parlament/plenum/abstimmung/liste/462112-462112?limit={LIMIT}&noFilterSet={NO_FILTER_SET}&offset={OFFSET}}";
    private static final int LIMIT = 30;
    private static final boolean NO_FILTER_SET = true;

    @Override
    public Iterator<Vote> iterator() {
        return new VoteIterator();
    }

    private class VoteIterator implements Iterator<Vote> {

        @Override
        public boolean hasNext() {
            // TODO
            return false;
        }

        @Override
        public Vote next() {
            // TODO
            return null;
        }

    }

}

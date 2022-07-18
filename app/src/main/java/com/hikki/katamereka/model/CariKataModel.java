package com.hikki.katamereka.model;

import java.util.List;
public class CariKataModel {

    private Boolean hasNext;
    private String keyNext;
    private String status;
    private List<Result> results;

    public Boolean getHasNext() {
        return hasNext;
    }

    public void setHasNext(Boolean hasNext) {
        this.hasNext = hasNext;
    }

    public String getKeyNext() {
        return keyNext;
    }

    public void setKeyNext(String keyNext) {
        this.keyNext = keyNext;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public class Result{
        private String author;
        private String category;
        private String quote;

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getQuote() {
            return quote;
        }

        public void setQuote(String quote) {
            this.quote = quote;
        }
    }
}

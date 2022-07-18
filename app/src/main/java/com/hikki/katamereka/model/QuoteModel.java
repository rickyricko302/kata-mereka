package com.hikki.katamereka.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuoteModel {
    @SerializedName("next")
    @Expose
    private Boolean hasNext;
    @SerializedName("tokoh")
    @Expose
    private String tokoh;
    @SerializedName("keterangan")
    @Expose
    private String keterangan;
    @SerializedName("results")
    @Expose
    private List<String> quotes;

    public Boolean getHasNext() {
        return hasNext;
    }

    public void setHasNext(Boolean hasNext) {
        this.hasNext = hasNext;
    }

    public String getTokoh() {
        return tokoh;
    }

    public void setTokoh(String tokoh) {
        this.tokoh = tokoh;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public List<String> getQuotes() {
        return quotes;
    }

    public void setQuotes(List<String> quotes) {
        this.quotes = quotes;
    }
}

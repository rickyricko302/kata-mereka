package com.hikki.katamereka.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TokohModel {
    @SerializedName("next")
    @Expose
    private Boolean next;
    @SerializedName("results")
    @Expose
    private List<Tokoh> tokohList;

    public Boolean getNext() {
        return next;
    }

    public void setNext(Boolean next) {
        this.next = next;
    }

    public List<Tokoh> getTokohList() {
        return tokohList;
    }

    public void setTokohList(List<Tokoh> tokohList) {
        this.tokohList = tokohList;
    }

    public class Tokoh {
        @SerializedName("nama")
        @Expose
        private String nama;
        @SerializedName("tahun")
        @Expose
        private String tahun;
        @SerializedName("keterangan")
        @Expose
        private String keterangan;

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public String getTahun() {
            return tahun;
        }

        public void setTahun(String tahun) {
            this.tahun = tahun;
        }

        public String getKeterangan() {
            return keterangan;
        }

        public void setKeterangan(String keterangan) {
            this.keterangan = keterangan;
        }
    }

}



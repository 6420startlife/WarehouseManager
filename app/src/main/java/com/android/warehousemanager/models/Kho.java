package com.android.warehousemanager.models;

import java.io.Serializable;

public class Kho implements Serializable {
    private String maKho;
    private String tenKho;

    public Kho() {
    }

    public Kho(String id, String name) {
        this.maKho = id;
        this.tenKho = name;
    }

    public String getMaKho() {
        return maKho;
    }

    public void setMaKho(String id) {
        this.maKho = id;
    }

    public String getTenKho() {
        return tenKho;
    }

    public void setTenKho(String tenKho) {
        this.tenKho = tenKho;
    }
}

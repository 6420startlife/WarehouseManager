package com.android.warehousemanager.models;

import java.io.Serializable;

public class GoodsReceipt implements Serializable {
    private int id;
    private String date;
    private String idStorage;

    public GoodsReceipt(int id, String date, String idStorage) {
        this.id = id;
        this.date = date;
        this.idStorage = idStorage;
    }

    public GoodsReceipt(String id, String idStorage) {
        this.date = id;
        this.idStorage = idStorage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdStorage() {
        return idStorage;
    }

    public void setIdStorage(String idStorage) {
        this.idStorage = idStorage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

package com.android.warehousemanager.models;

import java.io.Serializable;

public class PhieuNhap implements Serializable {
    private int id;
    private String dateOfStart;
    private String idWarehouse;

    public PhieuNhap(int id, String dateOfStart, String idWarehouse) {
        this.id = id;
        this.dateOfStart = dateOfStart;
        this.idWarehouse = idWarehouse;
    }

    public PhieuNhap(String dateOfStart, String idWarehouse) {
        this.dateOfStart = dateOfStart;
        this.idWarehouse = idWarehouse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdWarehouse() {
        return idWarehouse;
    }

    public void setIdWarehouse(String idWarehouse) {
        this.idWarehouse = idWarehouse;
    }

    public String getDateOfStart() {
        return dateOfStart;
    }

    public void setDateOfStart(String dateOfStart) {
        this.dateOfStart = dateOfStart;
    }
}

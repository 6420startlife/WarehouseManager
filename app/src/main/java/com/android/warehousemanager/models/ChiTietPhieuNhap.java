package com.android.warehousemanager.models;

import java.io.Serializable;

public class ChiTietPhieuNhap implements Serializable {
    private int id;
    private String idSupply;
    private int amount;

    public ChiTietPhieuNhap(int id, String idSupply, int amount) {
        this.id = id;
        this.idSupply = idSupply;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdSupply() {
        return idSupply;
    }

    public void setIdSupply(String idSupply) {
        this.idSupply = idSupply;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}

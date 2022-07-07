package com.android.warehousemanager.models;

import java.io.Serializable;

public class DetailGoodsReceipt implements Serializable {
    private int id;
    private String idSupply;
    private int amount;

    public DetailGoodsReceipt(int id, String id_supply, int amount) {
        this.id = id;
        this.idSupply = id_supply;
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

package com.android.warehousemanager.models;

import java.io.Serializable;

public class VatTu implements Serializable {
    private String id;
    private String name;
    private String image;
    private String unit;
    private String from;

    public VatTu(String maVatTu, String tenVatTu, String anhVatTu, String donViTinh, String xuatXu) {
        this.id = maVatTu;
        this.name = tenVatTu;
        this.image = anhVatTu;
        this.unit = donViTinh;
        this.from = xuatXu;
    }

    public VatTu() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}

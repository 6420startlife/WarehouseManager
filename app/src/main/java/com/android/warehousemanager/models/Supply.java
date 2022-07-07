package com.android.warehousemanager.models;

import java.io.Serializable;

public class Supply implements Serializable {
    private String id;
    private String name;
    private String image;
    private String unit;
    private String from;

    public Supply(String mvt, String tvt, String avt, String dvt, String xx) {
        this.id = mvt;
        this.name = tvt;
        this.image = avt;
        this.unit = dvt;
        this.from = xx;
    }

    public Supply() {
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

package com.android.warehousemanager.models;

import java.io.Serializable;

public class Supplies implements Serializable {
    private String id;
    private String name;
    private String image;
    private String unit;
    private String from;

<<<<<<< Updated upstream:app/src/main/java/com/android/warehousemanager/models/Supply.java
    public Supply(String mvt, String tvt, String avt, String dvt, String xx) {
        this.id = mvt;
        this.name = tvt;
        this.image = avt;
        this.unit = dvt;
        this.from = xx;
=======
    public Supplies(String id, String name, String image, String unit, String from) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.unit = unit;
        this.from = from;
>>>>>>> Stashed changes:app/src/main/java/com/android/warehousemanager/models/Supplies.java
    }

    public Supplies() {
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

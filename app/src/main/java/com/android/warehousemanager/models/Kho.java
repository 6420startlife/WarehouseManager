package com.android.warehousemanager.models;

import java.io.Serializable;

public class Kho implements Serializable {
    private String id;
    private String name;

    public Kho() {
    }

    public Kho(String id, String name) {
        this.id = id;
        this.name = name;
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
}

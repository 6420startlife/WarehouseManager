package com.android.warehousemanager.models;

import java.io.Serializable;

public class ChiTietPhieuNhap implements Serializable {
    private int soPhieu;
    private String maVatTu;
    private int qualtity;

    public ChiTietPhieuNhap(int soPhieu, String maVatTu, int soLuong) {
        this.soPhieu = soPhieu;
        this.maVatTu = maVatTu;
        this.qualtity = soLuong;
    }

    public int getSoPhieu() {
        return soPhieu;
    }

    public void setSoPhieu(int soPhieu) {
        this.soPhieu = soPhieu;
    }

    public String getMaVatTu() {
        return maVatTu;
    }

    public void setMaVatTu(String maVatTu) {
        this.maVatTu = maVatTu;
    }

    public int getQualtity() {
        return qualtity;
    }

    public void setQualtity(int qualtity) {
        this.qualtity = qualtity;
    }

}

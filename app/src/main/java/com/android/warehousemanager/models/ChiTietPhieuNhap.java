package com.android.warehousemanager.models;

import java.io.Serializable;

public class ChiTietPhieuNhap implements Serializable {
    private int soPhieu;
    private String maVatTu;
    private int soLuong;

    public ChiTietPhieuNhap(int sophieu, String mavattu, int soluong) {
        this.soPhieu = sophieu;
        this.maVatTu = mavattu;
        this.soLuong = soluong;
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

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

}

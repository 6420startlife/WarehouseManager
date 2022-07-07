package com.android.warehousemanager.models;

import java.io.Serializable;

public class ChiTietPhieuNhap implements Serializable {
    private int sophieu;
    private String mavattu;
    private int soluong;

    public ChiTietPhieuNhap(int sophieu, String mavattu, int soluong) {
        this.sophieu = sophieu;
        this.mavattu = mavattu;
        this.soluong = soluong;
    }

    public int getSophieu() {
        return sophieu;
    }

    public void setSophieu(int sophieu) {
        this.sophieu = sophieu;
    }

    public String getMavattu() {
        return mavattu;
    }

    public void setMavattu(String mavattu) {
        this.mavattu = mavattu;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

}

package com.android.warehousemanager.models;

import java.io.Serializable;

public class ChiTietPhieuNhap implements Serializable {
    private int sophieu;
    private String mavatlieu;
    private int soluong;

    public ChiTietPhieuNhap(int sophieu, String mavattu, int soluong) {
        this.sophieu = sophieu;
        this.mavatlieu = mavattu;
        this.soluong = soluong;
    }

    public int getSophieu() {
        return sophieu;
    }

    public void setSophieu(int sophieu) {
        this.sophieu = sophieu;
    }

    public String getMavatlieu() {
        return mavatlieu;
    }

    public void setMavatlieu(String mavatlieu) {
        this.mavatlieu = mavatlieu;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

}

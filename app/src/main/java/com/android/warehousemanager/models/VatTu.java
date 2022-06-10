package com.android.warehousemanager.models;

import java.io.Serializable;

public class VatTu implements Serializable {
    private String maVatTu;
    private String tenVatTu;
    private String anhVatTu;
    private String donViTinh;
    private String xuatXu;

    public VatTu(String maVatTu, String tenVatTu, String anhVatTu, String donViTinh, String xuatXu) {
        this.maVatTu = maVatTu;
        this.tenVatTu = tenVatTu;
        this.anhVatTu = anhVatTu;
        this.donViTinh = donViTinh;
        this.xuatXu = xuatXu;
    }

    public VatTu() {
    }

    public String getMaVatTu() {
        return maVatTu;
    }

    public void setMaVatTu(String maVatTu) {
        this.maVatTu = maVatTu;
    }

    public String getTenVatTu() {
        return tenVatTu;
    }

    public void setTenVatTu(String tenVatTu) {
        this.tenVatTu = tenVatTu;
    }

    public String getAnhVatTu() {
        return anhVatTu;
    }

    public void setAnhVatTu(String anhVatTu) {
        this.anhVatTu = anhVatTu;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public String getXuatXu() {
        return xuatXu;
    }

    public void setXuatXu(String xuatXu) {
        this.xuatXu = xuatXu;
    }
}

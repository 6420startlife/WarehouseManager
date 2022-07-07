package com.android.warehousemanager.models;

import java.io.Serializable;

public class VatTu implements Serializable {
    private String ma_vat_tu;
    private String ten_vat_tu;
    private String anh_vat_tu;
    private String don_vi_tinh;
    private String xuat_xu;

    public VatTu(String maVatTu, String tenVatTu, String anhVatTu, String donViTinh, String xuatXu) {
        this.ma_vat_tu = maVatTu;
        this.ten_vat_tu = tenVatTu;
        this.anh_vat_tu = anhVatTu;
        this.don_vi_tinh = donViTinh;
        this.xuat_xu = xuatXu;
    }

    public VatTu() {
    }

    public String getMa_vat_tu() {
        return ma_vat_tu;
    }

    public void setMa_vat_tu(String ma_vat_tu) {
        this.ma_vat_tu = ma_vat_tu;
    }

    public String getTen_vat_tu() {
        return ten_vat_tu;
    }

    public void setTen_vat_tu(String ten_vat_tu) {
        this.ten_vat_tu = ten_vat_tu;
    }

    public String getAnh_vat_tu() {
        return anh_vat_tu;
    }

    public void setAnh_vat_tu(String anh_vat_tu) {
        this.anh_vat_tu = anh_vat_tu;
    }

    public String getDon_vi_tinh() {
        return don_vi_tinh;
    }

    public void setDon_vi_tinh(String don_vi_tinh) {
        this.don_vi_tinh = don_vi_tinh;
    }

    public String getXuat_xu() {
        return xuat_xu;
    }

    public void setXuat_xu(String xuat_xu) {
        this.xuat_xu = xuat_xu;
    }
}

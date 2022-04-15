package com.example.bookhair.Class;


public class DichvuItemSpinner {
    private int id_dichvu;
    private String tenDichvu;
    private int thoigian;
    private int gia;
    private String hinhAnh;


    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public int getId_dichvu() {
        return id_dichvu;
    }

    public void setId_dichvu(int id_dichvu) {
        this.id_dichvu = id_dichvu;
    }

    public void setTenDichvu(String tenDichvu) {
        this.tenDichvu = tenDichvu;
    }

    public void setThoigian(int thoigian) {
        this.thoigian = thoigian;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public String getTenDichvu() {
        return tenDichvu;
    }

    public int getThoigian() {
        return thoigian;
    }

    public int getGia() {
        return gia;
    }
}
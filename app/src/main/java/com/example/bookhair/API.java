package com.example.bookhair;

public class API {
    public static final String URL= "http://192.168.1.11:4000";
    public static final String HOME = URL+ "/api";
    // finish
    public static final String LOGIN= HOME+"/login";
    public static final String GET_SALON= HOME+"/getSalon";
    public static final String GET_SALON_FEATURE= HOME+"/getSalonFeature";
    public static final String GET_DICHVU= HOME+"/getDichvu";
    public static final String YEU_THICH= HOME+"/YeuThich";
    public static final String GET_SALON_BY_ID= HOME+"/getSalonById";
    public static final String GET_DICHVU_BY_SALON= HOME+"/getDichVuBySalon";

    // tam thoi xong
    public static final String GET_NHANVIEN_BY_SALON= HOME+"/getNhanVienBySalon";
    // not finish
    // dat lich
    public static final String DAT_LICH= HOME+"/DatLich";
}

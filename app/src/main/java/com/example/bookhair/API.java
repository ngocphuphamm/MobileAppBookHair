package com.example.bookhair;

public class API {
    public static final String URL= "http://192.168.20.91:4000";
    public static final String HOME = URL+ "/api";
    // finish
    public static final String LOGIN= HOME+"/login";
    public static final String GET_SALON= HOME+"/getSalon";
    public static final String GET_SALON_FEATURE= HOME+"/getSalonFeature";
    public static final String GET_DICHVU= HOME+"/getDichvu";
    public static final String YEU_THICH= HOME+"/YeuThich";
    public static final String GET_SALON_BY_ID= HOME+"/getSalonById";
    public static final String GET_DICHVU_BY_SALON= HOME+"/getDichVuBySalon";
    public static final String DAT_LICH= HOME+"/DatLich";
    // tam thoi xong fix cái này nếu có nhân viên có lịch giờ đó thì bỏ đi ko hiển thị
    public static final String GET_NHANVIEN_BY_SALON= HOME+"/getNhanVienBySalon";
    // not finish
    public static final String GET_LICHHEN_SAP_TOI= HOME+"/getLichHenSapToi";
    public static final String GET_LICHHEN_DA_DAT= HOME+"/getLichDaDat";
    public static final String GET_LICHHEN_DA_DUYET = HOME + "/getLichHenDaDuyet";

}

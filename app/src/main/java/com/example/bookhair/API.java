package com.example.bookhair;

public class API {
    public static final String URL= "http://192.168.1.6:4000";
    public static final String HOME = URL+ "/api";
    // finish

    // SALON
    public static final String GET_SALON= HOME+"/salon/getSalon";
    public static final String GET_SALON_FEATURE= HOME+"/salon/getSalonFeature";
    public static final String GET_SALON_BY_ID= HOME+"/salon/getSalonById";
    //fix chua xong
    public static final String GET_INFO_SALON= HOME+"/salon/getInfoSalon";
    // DICHVU
    public static final String GET_DICHVU= HOME+"/dichvu/getDichvu";
    public static final String GET_DICHVU_BY_SALON= HOME+"/dichvu/getDichVuBySalon";
    public static final String GET_INFO_CTDV= HOME+"/dichvu/getChiTietDV";
    // YEUTHICH
    public static final String YEU_THICH= HOME+"/yeuthich/YeuThich";
    public static final String GET_LIST_YEUTHICH= HOME+"/yeuthich/getListYeuThich";



    // AUTH
    public static final String LOGIN= HOME+"/auth/login";
    public static final String LOGOUT= HOME+"/auth/logout";
    // not finish
    public static final String REGISTER= HOME+"/auth/register";


    //lich hen
    public static final String DAT_LICH= HOME+"/lichhen/DatLich";
    public static final String GET_LICHHEN_SAP_TOI= HOME+"/lichhen/getLichHenSapToi";
    public static final String GET_LICHHEN_DA_DAT= HOME+"/lichhen/getLichDaDat";
    public static final String GET_LICHHEN_DA_DUYET = HOME + "/lichhen/getLichHenDaDuyet";



    //user
    public static final String GET_INFO_USER= HOME+"/user/show_info_user";
    public static final String SAVE_USER_INFO= HOME+"/user/save_user_info";
    public static final String SAVE_USER_INFO_REGISTER = HOME + "/user/saveUserInfoRegister";

    public static final String GET_THONGBAO= HOME+"/thongbao/getThongBao";


    // nhanvien
    // tam thoi xong fix cái này nếu có nhân viên có lịch giờ đó thì bỏ đi ko hiển thị
    public static final String GET_NHANVIEN_BY_SALON= HOME+"/nhanvien/getNhanVienBySalon";







}

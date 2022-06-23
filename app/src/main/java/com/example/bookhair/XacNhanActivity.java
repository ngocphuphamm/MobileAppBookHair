package com.example.bookhair;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bookhair.Class.NhanVienItemSpinner;
import com.example.bookhair.Class.SalonNoti;
import com.example.bookhair.adapter.NhanVienSpinnerAdapter;
import com.example.bookhair.fragment.HomeFragment;
import com.example.bookhair.fragment.SapToiFragment;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import vn.momo.momo_partner.AppMoMoLib;

public class XacNhanActivity extends AppCompatActivity {

    private String amount = "10000";
    private String fee = "0";
    int environment = 0;//developer default
    private String merchantName = "Demo SDK";
    private String merchantCode = "SCB01";
    private String merchantNameLabel = "Nhà cung cấp";
    private String description = "Thanh toán dịch vụ ABC";
    private int giaDichVu = 0 ;
    private String idLichHen ;

    private ArrayList nhanVienLists;
    private NhanVienSpinnerAdapter nhanVienSpinnerAdapter;
    private Spinner spinnerNhanVien;
    private Button btn_xacnhandatlich,btn_momo;
    private int id_nhanvien = 0;
    private int id_dichvu = 0;
    private int id_salon = 0;
    private String thoiGian = "";
    private String ngayHen = "";
    private ProgressDialog dialog;
    private SharedPreferences userPref;

    TextView ngayDat;
    TextView gio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_xac_nhan);
        id_dichvu = getIntent().getIntExtra("id_dichvu",0);
        id_salon = getIntent().getIntExtra("id_salon",0);
        thoiGian = getIntent().getStringExtra("gio");
        ngayHen = getIntent().getStringExtra("ngayDat");
        giaDichVu = getIntent().getIntExtra("giaDichVu",0);
        userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        initNhanVienList();
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        spinnerNhanVien = findViewById(R.id.spinnerNhanVien);
        btn_xacnhandatlich = findViewById(R.id.btn_xacnhandatlich);
        nhanVienSpinnerAdapter = new NhanVienSpinnerAdapter(this, nhanVienLists);
        spinnerNhanVien.setAdapter(nhanVienSpinnerAdapter);
        ngayDat = findViewById(R.id.txtNgayDat);
        btn_momo = findViewById(R.id.btn_momo);
        ngayDat.setText(getIntent().getStringExtra("ngayDat"));
        gio = findViewById(R.id.txtGio);
        gio.setText(getIntent().getStringExtra("gio"));
        TextView dvChon = findViewById(R.id.dichvuDachon);
        dvChon.setText(getIntent().getStringExtra("dichvu"));
        spinnerNhanVien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                NhanVienItemSpinner nhanVienItemSpinner = (NhanVienItemSpinner) adapterView.getItemAtPosition(i);
                id_nhanvien = nhanVienItemSpinner.getId_nhanvien();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_xacnhandatlich.setOnClickListener(v->{
            dialog.setMessage("Đang xử lý");
            dialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, API.DAT_LICH, response -> {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getBoolean("success")){
                        JSONObject lichhenobject = object.getJSONObject("lichhen");
                        JSONObject nhanvienobject = lichhenobject.getJSONObject("nhanvien");
                        JSONObject userobject = lichhenobject.getJSONObject("user");
                        JSONObject salonobject = lichhenobject.getJSONObject("salon");

                        SalonNoti salonNoti = new SalonNoti();
                        salonNoti.setId_lichhen(lichhenobject.getString("id"));
                        idLichHen = lichhenobject.getString("id");
                        salonNoti.setTrangThai(lichhenobject.getString("status"));
                        salonNoti.setThoiGian(lichhenobject.getString("ngayHen"));
                        salonNoti.setHinhAnh(salonobject.getString("hinhAnh"));
                        salonNoti.setNhanVienCatToc(nhanvienobject.getString("hoTen"));
                        salonNoti.setTenSalon(salonobject.getString("tenSalon"));



                    }
                    else
                    {
                        Toast.makeText(this, "Đã có lỗi xảy ra ", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }, error -> {
                error.printStackTrace();
                dialog.dismiss();
            }){

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    String token = userPref.getString("token", "");

                    HashMap<String,String> map = new HashMap<>();

                    map.put("Authorization", "Bearer "+token);
                    return map;
                }

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String,String> map = new HashMap<>();
                    String user = userPref.getString("userId","");
                    map.put("userId",user);
                    map.put("ngayHen", ngayHen);
                    map.put("id_Dichvu", id_dichvu+"");
                    map.put("id_Nhanvien", id_nhanvien+"");
                    map.put("id_salon", id_salon+"");
                    map.put("thoiGian", thoiGian);

                    return map;
                }
            };
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);
            requestPayment(idLichHen);
            Toast.makeText(this, "Đặt lịch thành thông", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(XacNhanActivity.this, DashboardActivity.class);
            intent.putExtra("message", 2);
            startActivity(intent);

        });




        btn_momo.setOnClickListener(v->{
            dialog.setMessage("Đang xử lý");
            dialog.show();
            requestPayment(idLichHen);

        });

    }

    private void initNhanVienList() {
        nhanVienLists = new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.GET, API.GET_NHANVIEN_BY_SALON + "/" +  getIntent().getStringExtra("ngayDat") +","+ getIntent().getStringExtra("gio") + "," + id_salon + "," + id_dichvu, response -> {

            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")) {
                    JSONArray array = new JSONArray(object.getString("nhanvien"));

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject nhanvienObject = array.getJSONObject(i);
                        NhanVienItemSpinner nhanVienItemSpinner = new NhanVienItemSpinner();
                        nhanVienItemSpinner.setId_nhanvien(nhanvienObject.getInt("id_NhanVien"));
                        nhanVienItemSpinner.setTenNhanVien(nhanvienObject.getString("hoTen"));
                        nhanVienItemSpinner.setChucVu(nhanvienObject.getString("chucvu"));
                        nhanVienLists.add(nhanVienItemSpinner);

                    }
                    if (nhanVienLists.isEmpty()) {
                        Toast.makeText(XacNhanActivity.this, "Không có nhân viên làm lúc " + getIntent().getStringExtra("gio"), Toast.LENGTH_SHORT).show();
                        btn_xacnhandatlich.setEnabled(false);
                        btn_xacnhandatlich.setBackgroundResource(R.drawable.border_gray);
                    }
                    nhanVienSpinnerAdapter = new NhanVienSpinnerAdapter(this, nhanVienLists);
                    spinnerNhanVien.setAdapter(nhanVienSpinnerAdapter);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            error.printStackTrace();
        }) {

        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }



    //Get token through MoMo app
    private void requestPayment(String idLichDat) {
        AppMoMoLib.getInstance().setAction(AppMoMoLib.ACTION.PAYMENT);
        AppMoMoLib.getInstance().setActionType(AppMoMoLib.ACTION_TYPE.GET_TOKEN);

        Map<String, Object> eventValue = new HashMap<>();
        //client Required
        eventValue.put("merchantname", merchantName); //Tên đối tác. được đăng ký tại https://business.momo.vn. VD: Google, Apple, Tiki , CGV Cinemas
        eventValue.put("merchantcode", merchantCode); //Mã đối tác, được cung cấp bởi MoMo tại https://business.momo.vn
        eventValue.put("amount", giaDichVu); //Kiểu integer
        eventValue.put("orderId", idLichDat); //uniqueue id cho Bill order, giá trị duy nhất cho mỗi đơn hàng
        eventValue.put("orderLabel", idLichDat); //gán nhãn

        //client Optional - bill info
        eventValue.put("merchantnamelabel", "Dịch vụ");//gán nhãn
        eventValue.put("fee", 0); //Kiểu integer
        eventValue.put("description", description); //mô tả đơn hàng - short description

        //client extra data
        eventValue.put("requestId",  merchantCode+"merchant_billId_"+System.currentTimeMillis());
        eventValue.put("partnerCode", merchantCode);
        //Example extra data
        JSONObject objExtraData = new JSONObject();
        try {
            objExtraData.put("site_code", "008");
            objExtraData.put("site_name", "CGV Cresent Mall");
            objExtraData.put("screen_code", 0);
            objExtraData.put("screen_name", "Special");
            objExtraData.put("movie_name", "Kẻ Trộm Mặt Trăng 3");
            objExtraData.put("movie_format", "2D");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        eventValue.put("extraData", objExtraData.toString());

        eventValue.put("extra", "");
        AppMoMoLib.getInstance().requestMoMoCallBack(this, eventValue);


    }
    //Get token callback from MoMo app an submit to server side
    protected  void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AppMoMoLib.getInstance().REQUEST_CODE_MOMO && resultCode == -1) {
            if(data != null) {
                if(data.getIntExtra("status", -1) == 0) {
                    //TOKEN IS AVAILABLE
                    Log.d("Thanh Cong",data.getStringExtra(("message")));
                    String token = data.getStringExtra("data"); //Token response


                    String phoneNumber = data.getStringExtra("phonenumber");
                    String env = data.getStringExtra("env");
                    if(env == null){
                        env = "app";
                    }

                    if(token != null && !token.equals("")) {
                        // TODO: send phoneNumber & token to your server side to process payment with MoMo server
                        // IF Momo topup success, continue to process your order
                    } else {
                        Log.d("Thanh Cong","Khong thanh cong");
                    }
                } else if(data.getIntExtra("status", -1) == 1) {
                    //TOKEN FAIL
                    String message = data.getStringExtra("message") != null?data.getStringExtra("message"):"Thất bại";
                    Log.d("Thanh Cong","Khong thanh cong");;

                } else if(data.getIntExtra("status", -1) == 2) {
                    //TOKEN FAIL
                    Log.d("Thanh Cong","Khong thanh cong");

                } else {
                    //TOKEN FAIL
                    Log.d("Thanh Cong","Khong thanh cong");

                }
            } else {
                Log.d("Thanh Cong","Khong thanh cong");

            }
        } else {
            Log.d("Thanh Cong","Khong thanh cong");

        }
    }
}

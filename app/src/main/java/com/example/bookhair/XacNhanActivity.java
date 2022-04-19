package com.example.bookhair;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.example.bookhair.fragment.SapToiFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class XacNhanActivity extends AppCompatActivity {
    private ArrayList nhanVienLists;
    private NhanVienSpinnerAdapter nhanVienSpinnerAdapter;
    private Spinner spinnerNhanVien;
    private Button btn_xacnhandatlich;
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
        userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        initNhanVienList();
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        spinnerNhanVien = findViewById(R.id.spinnerNhanVien);
        btn_xacnhandatlich = findViewById(R.id.btn_xacnhandatlich);
        nhanVienSpinnerAdapter = new NhanVienSpinnerAdapter(this, nhanVienLists);
        spinnerNhanVien.setAdapter(nhanVienSpinnerAdapter);
        ngayDat = findViewById(R.id.txtNgayDat);
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
                        salonNoti.setId_lichhen(lichhenobject.getInt("id"));
                        salonNoti.setTrangThai(lichhenobject.getString("status"));
                        salonNoti.setThoiGian(lichhenobject.getString("ngayHen"));
                        salonNoti.setHinhAnh(salonobject.getString("hinhAnh"));
                        salonNoti.setNhanVienCatToc(nhanvienobject.getString("hoTen"));
                        salonNoti.setTenSalon(salonobject.getString("tenSalon"));

//
//                         SapToiFragment.arraySalonNoti.add(0, salonNoti);
//                        SapToiFragment.lvThongBaoSapToi.getAdapter().notifyItemInserted(0);
//                        SapToiFragment.lvThongBaoSapToi.getAdapter().notifyDataSetChanged();
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
            Toast.makeText(this, "Đặt lịch thành thông", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(XacNhanActivity.this, DashboardActivity.class);
            intent.putExtra("message", 2);
            startActivity(intent);


        });
    }

    private void initNhanVienList() {
        nhanVienLists = new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.GET, API.GET_NHANVIEN_BY_SALON + "/" + getIntent().getStringExtra("gio") + "," + id_salon + "," + id_dichvu, response -> {

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
}

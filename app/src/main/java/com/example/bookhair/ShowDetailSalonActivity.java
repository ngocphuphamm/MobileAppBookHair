package com.example.bookhair;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ShowDetailSalonActivity extends AppCompatActivity {
    TextView nameSalon, diachi, name2, txtSoNam, txtSoCho, txtChuTiem, txtGioiThieu;
    RoundedImageView image;
    Button btnDatLich;
    private ImageButton btn_yeuthich, btn_home;
    private SharedPreferences userPref;
    private int id_salon = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_show_detail_salon);
        userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        nameSalon = findViewById(R.id.ten_detail_salon);
        name2 = findViewById(R.id.txtTensalon);
        diachi = findViewById(R.id.txtDiachi);
//        txtSoCho = findViewById(R.id.txtSoCho);
//        txtSoNam = findViewById(R.id.txtSoNam);
        txtChuTiem = findViewById(R.id.txtTenChutiem);
        txtGioiThieu = findViewById(R.id.txtGioithieu);
        btn_yeuthich = findViewById(R.id.btn_yeuthich);
        image = findViewById(R.id.image_detail_salon);
        btnDatLich = findViewById(R.id.btn_datlich);
        id_salon = getIntent().getIntExtra("salonId", 0);
        btn_home = findViewById(R.id.btn_home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShowDetailSalonActivity.this, DashboardActivity.class));
            }
        });
        getInFoSalon();
        btnDatLich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowDetailSalonActivity.this, DatLichActivity.class);
                intent.putExtra("id_salon", id_salon);
                startActivity(intent);
            }
        });

        btn_yeuthich.setOnClickListener(v->{
            StringRequest request = new StringRequest(Request.Method.POST, API.YEU_THICH, response -> {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getBoolean("success")){
                        StringRequest request2 = new StringRequest(Request.Method.GET, API.GET_SALON_BY_ID+"/"+id_salon, res->{

                            try {

                                JSONObject object2 = new JSONObject(res);
                                if (object2.getBoolean("success")) {
                                    JSONArray salonarray = new JSONArray(object2.getString("salon"));
                                    JSONObject salonobject = salonarray.getJSONObject(0);
                                    Boolean selfLove = salonobject.getBoolean("selfLove");
                                    btn_yeuthich.setImageResource(
                                            selfLove ? R.drawable.ic_baseline_favorite_red : R.drawable.ic_baseline_favorite_24
                                    );
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }, error -> {
                            error.printStackTrace();
                        }){
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                String token = userPref.getString("token", "");
                                HashMap<String,String> map = new HashMap<>();
                                map.put("Authorization","Bearer "+token);
                                return map;
                            }
                        };
                        RequestQueue queue = Volley.newRequestQueue(this);
                        queue.add(request2);
                    }else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }, error -> {
                error.printStackTrace();
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
                    map.put("id", id_salon+"");
                    map.put("userId",user);
                    return map;
                }


            };
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);
        });
    }


    public void getInFoSalon(){
        StringRequest request = new StringRequest(Request.Method.GET, API.GET_INFO_SALON+"/"+id_salon, res->{

            try {

                JSONObject object = new JSONObject(res);
                if (object.getBoolean("success")) {
                    Boolean selfLove = object.getBoolean("selfLove");
                    JSONArray salonarray = new JSONArray(object.getString("salon"));
                    JSONObject salonobject = salonarray.getJSONObject(0);
                    String namesalon = salonobject.getString("tenSalon");
                    String name2salon = salonobject.getString("tenSalon");
                    String diaChi = salonobject.getString("diaChi");
//                    String soCho = salonobject.getString("soChoNgoi");
//                    String soNam = salonobject.getString("soNamThanhLap");
                    String imageSalon = salonobject.getString("hinhAnh");
                    String chuTiem = salonobject.getString("chuTiem");
                    String gioiThieu = salonobject.getString("gioiThieu");

                    nameSalon.setText(namesalon);
                    name2.setText(name2salon);
                    diachi.setText(diaChi);
//                    txtSoCho.setText(soCho);
//                    txtSoNam.setText(soNam);
                    txtChuTiem.setText(chuTiem);
                    txtGioiThieu.setText(gioiThieu);
                    Picasso.get().load(API.URL + "/storage/salon/" + imageSalon).into(image);
                    btn_yeuthich.setImageResource(
                            selfLove ? R.drawable.ic_baseline_favorite_red : R.drawable.ic_baseline_favorite_24
                    );
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            error.printStackTrace();
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = userPref.getString("token", "");
                HashMap<String,String> map = new HashMap<>();
                map.put("Authorization","Bearer "+token);
                return map;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

}
package com.example.bookhair;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bookhair.Class.YeuThich;
import com.example.bookhair.adapter.YeuThichAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YeuThichActivity extends AppCompatActivity {
    private RecyclerView yeuThichRecycler;
    private List<YeuThich> yeuThichList;
    private ImageButton btn_home;
    private SharedPreferences userPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_yeu_thich);
        btn_home = findViewById(R.id.btn_home_yt);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(YeuThichActivity.this, DashboardActivity.class);
                intent.putExtra("message", 3);
                startActivity(intent);
            }
        });
        userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        yeuThichRecycler = findViewById(R.id.recyclerYeuThich);

        yeuThichList = new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.GET, API.GET_LIST_YEUTHICH, response -> {

            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")){
                    JSONArray array = new JSONArray(object.getString("yeuthich"));
                    for (int i = 0; i< array.length(); i++){
                        JSONObject yeuthichObject = array.getJSONObject(i);
                        JSONObject salonObject = yeuthichObject.getJSONObject("salon");
                        YeuThich yeuThich = new YeuThich();
                        yeuThich.setId_salon(salonObject.getInt("id"));
                        yeuThich.setImageSalon(salonObject.getString("hinhAnh"));
                        yeuThich.setTenSalon(salonObject.getString("tenSalon"));
                        yeuThichList.add(yeuThich);

                    }
                    yeuThichRecycler.setLayoutManager(new GridLayoutManager(this, 3));
                    yeuThichRecycler.setAdapter(new YeuThichAdapter(this, yeuThichList));

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
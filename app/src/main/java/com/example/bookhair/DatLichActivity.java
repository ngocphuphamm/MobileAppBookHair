package com.example.bookhair;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bookhair.Class.DichvuItemSpinner;
import com.example.bookhair.adapter.DichvuSpinnerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class DatLichActivity extends AppCompatActivity {
    private ArrayList<DichvuItemSpinner> dichvuLists;
    private DichvuSpinnerAdapter dichvuSpinnerAdapter;
    private Spinner spinnerDichvu;
    private int id_dichvu = 0;
    private int giaDichVu = 0;
    CalendarView calendarView;
    RadioGroup radioGroupTime;
    private SharedPreferences userPref;
    private int id_salon = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dat_lich);
        id_salon = getIntent().getIntExtra("id_salon", 0);
        userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        TextView textViewHidden = findViewById(R.id.textViewHidden);
        textViewHidden.setVisibility(View.INVISIBLE);
        TextView textViewTimeHidden = findViewById(R.id.textHiddenTime);
        textViewTimeHidden.setVisibility(View.INVISIBLE);
        spinnerDichvu = findViewById(R.id.spinnerDV);
        initListDvSpinner();

        Button btnTieptuc = findViewById(R.id.btn_tieptuc);
        calendarView = findViewById(R.id.calendar);
        Calendar calendar = Calendar.getInstance();
        calendarView.setMinDate(calendarView.getDate());
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
                public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {


                     int month  = i1 + 1 ;
                    textViewHidden.setText( i + "-" + month+ "-" + i2);


            }
        });
        radioGroupTime = findViewById(R.id.radioGrTime);


        radioGroupTime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                String time = ((RadioButton)findViewById(radioGroupTime.getCheckedRadioButtonId())).getText().toString();
                textViewTimeHidden.setText(time);
            }
        });
        TextView tenDV = findViewById(R.id.textHiddenDV);
        tenDV.setVisibility(View.INVISIBLE);

        spinnerDichvu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                DichvuItemSpinner dichvuItemSpinner = (DichvuItemSpinner) adapterView.getItemAtPosition(i);
                String dvnhan = dichvuItemSpinner.getTenDichvu();
                tenDV.setText(dvnhan);
                id_dichvu = dichvuItemSpinner.getId_dichvu();
                giaDichVu = dichvuItemSpinner.getGia();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnTieptuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(textViewHidden.getText().toString()))
                {
                    Toast.makeText(DatLichActivity.this,
                            "Vui lòng khách hàng chọn ngày đặt lịch!",
                            Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(textViewTimeHidden.getText().toString())){
                    Toast.makeText(DatLichActivity.this,
                            "Vui lòng khách hàng chọn thời gian đặt lịch",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(DatLichActivity.this, XacNhanActivity.class);
                    intent.putExtra("gio", textViewTimeHidden.getText());
                    intent.putExtra("ngayDat", textViewHidden.getText());
                    intent.putExtra("dichvu", tenDV.getText());
                    intent.putExtra("id_dichvu", id_dichvu);
                    intent.putExtra("id_salon", id_salon);
                    intent.putExtra("giaDichVu", giaDichVu);
                    startActivity(intent);
                }

            }
        });


    }

    private void initListDvSpinner() {
        dichvuLists = new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.GET, API.GET_DICHVU_BY_SALON+"/"+id_salon, response -> {

            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")){
                    JSONArray array = new JSONArray(object.getString("dichvu"));
                    for (int i = 0; i< array.length(); i++){
                        JSONObject dichvuObject = array.getJSONObject(i);
                        DichvuItemSpinner dichvuItemSpinner = new DichvuItemSpinner();
                        dichvuItemSpinner.setHinhAnh(dichvuObject.getString("hinhanh"));
                        dichvuItemSpinner.setId_dichvu(dichvuObject.getInt("id"));
                        dichvuItemSpinner.setTenDichvu(dichvuObject.getString("tenDichvu"));
                        dichvuItemSpinner.setThoigian(dichvuObject.getInt("thoiGian"));
                        dichvuItemSpinner.setGia(dichvuObject.getInt("giaTien"));
                        dichvuLists.add(dichvuItemSpinner);

                    }
                    dichvuSpinnerAdapter = new DichvuSpinnerAdapter(this, dichvuLists);
                    spinnerDichvu.setAdapter(dichvuSpinnerAdapter);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            error.printStackTrace();
        }){

        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);

    }

}
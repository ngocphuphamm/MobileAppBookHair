package com.example.bookhair;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class DatLichDVActivity extends AppCompatActivity {
    private int id_salon = 0;
    private int id_dichvu = 0;
    private String ngayHen = "";
    private String gioHen = "";
    private String tenDV = "";
    CalendarView calendarView;
    RadioGroup radioGroupTime;
    private SharedPreferences userPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dat_lich_dvactivity);
        id_salon = getIntent().getIntExtra("id_salon", 0);
        id_dichvu = getIntent().getIntExtra("id_dichvu", 0);
        tenDV = getIntent().getStringExtra("tenDV");
        userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        Button btnTieptuc = findViewById(R.id.btn_tieptucCTDV);

        calendarView = findViewById(R.id.calendarCTDV);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                ngayHen =  i + "-" +i1+1+ "-" + i2;
            }
        });
        radioGroupTime = findViewById(R.id.radioGrTimeCTDV);
        radioGroupTime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                String time = ((RadioButton)findViewById(radioGroupTime.getCheckedRadioButtonId())).getText().toString();
                gioHen = time;
            }
        });

        btnTieptuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DatLichDVActivity.this, XacNhanActivity.class);
                intent.putExtra("gio", gioHen);
                intent.putExtra("ngayDat", ngayHen);
                intent.putExtra("dichvu", tenDV);
                intent.putExtra("id_dichvu", id_dichvu);
                intent.putExtra("id_salon", id_salon);
                startActivity(intent);
            }
        });
    }
}
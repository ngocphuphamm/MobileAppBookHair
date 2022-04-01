package com.example.bookhair;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.bookhair.fragment.HomeFragment;
import com.example.bookhair.fragment.LichFragment;
import com.example.bookhair.fragment.TaiKhoanFragment;
import com.example.bookhair.fragment.ThongBaoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends AppCompatActivity {
    public static FragmentManager fragmentManager;
    BottomNavigationView navigationView;
    private int message = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);

        message = getIntent().getIntExtra("message",0);
        navigationView = findViewById(R.id.bottom_nav);

        fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.frameHomeContainer, new HomeFragment(),HomeFragment.class.getSimpleName()).commit();
        if (message == 1){
            fragmentManager.beginTransaction().replace(R.id.frameHomeContainer, new LichFragment(),LichFragment.class.getSimpleName()).commit();
        }
        if (message == 2){
            fragmentManager.beginTransaction().replace(R.id.frameHomeContainer, new LichFragment(),LichFragment.class.getSimpleName()).commit();
        }
        if (message == 3){
            fragmentManager.beginTransaction().replace(R.id.frameHomeContainer, new TaiKhoanFragment(),TaiKhoanFragment.class.getSimpleName()).commit();
        }
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()){
                    case R.id.home:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.lichhen:
                        selectedFragment = new LichFragment();
                        break;
                    case R.id.thongbao:
                        selectedFragment = new ThongBaoFragment();
                        break;
                    case R.id.taikhoan:
                        selectedFragment = new TaiKhoanFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frameHomeContainer,
                        selectedFragment).commit();
                return true;

            }
        });

    }
}
package com.example.bookhair;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bookhair.adapter.ViewPagerAdapter;

public class OnBoardActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private Button buttonLeft, buttonRight;
    private ViewPagerAdapter adapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_on_board);
        init();
    }
    private void init(){
        viewPager = findViewById(R.id.view_pager);
        buttonLeft = findViewById(R.id.btn_Left);
        buttonRight = findViewById(R.id.btn_Right);
        dotsLayout = findViewById(R.id.dotsLayout);
        adapter = new ViewPagerAdapter(this);
        addDots(0);
        viewPager.setOnPageChangeListener(listener);
        viewPager.setAdapter(adapter);

        buttonRight.setOnClickListener(v->{
            if(buttonRight.getText().equals("Tiếp")){
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            }else {
                startActivity(new Intent(OnBoardActivity.this, LoginActivity.class));
                finish();
            }
        });

        buttonLeft.setOnClickListener(v->{
            viewPager.setCurrentItem(viewPager.getCurrentItem()+2);
        });
    }

    private void addDots(int position){
        dotsLayout.removeAllViews();
        dots = new TextView[3];
        for (int i=0 ; i< dots.length; i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.black));
            dotsLayout.addView(dots[i]);
        }
        // change selected dot color
        if (dots.length > 0){
            dots[position].setTextColor(getResources().getColor(R.color.black));
        }
    }
    private ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);

            if (position==0){
                buttonLeft.setVisibility(View.VISIBLE);
                buttonLeft.setEnabled(true);
                buttonRight.setText("Tiếp");
            }else if (position==1){
                buttonLeft.setVisibility(View.GONE);
                buttonLeft.setEnabled(false);
                buttonRight.setText("Tiếp");
            }else {
                buttonLeft.setVisibility(View.GONE);
                buttonLeft.setEnabled(true);
                buttonRight.setText("Vào app");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
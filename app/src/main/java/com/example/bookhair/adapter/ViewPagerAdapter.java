package com.example.bookhair.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.bookhair.R;

public class ViewPagerAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;

    public ViewPagerAdapter(Context context) {
        this.context = context;
    }
    private int images[] = {
            R.drawable.start1,
            R.drawable.start2,
            R.drawable.start3
    };
    private String titles[] = {
            "Nhiều Salon nổi tiếng",
            "Lựa chọn",
            "Nhanh chóng"
    };
    private String descs[] = {
            "Đặt lịch nhiều salon nổi tiếng",
            "Tự lựa chọn nhân viên, dịch vụ theo sở thích",
            "Đặt lịch, thanh toán online nhanh chóng, thuận tiện"
    };
    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout)object ;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.view_pager, container, false);

        // init View
        ImageView imageView = v.findViewById(R.id.imgViewPager);
        TextView txtTitle = v.findViewById(R.id.titleViewPager);
        TextView txtDesc = v.findViewById(R.id.descViewPager);

        imageView.setImageResource(images[position]);
        txtTitle.setText(titles[position]);
        txtDesc.setText(descs[position]);

        container.addView(v);
        return  v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}


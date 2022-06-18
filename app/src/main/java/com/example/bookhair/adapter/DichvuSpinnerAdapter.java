package com.example.bookhair.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bookhair.API;
import com.example.bookhair.Class.DichvuItemSpinner;
import com.example.bookhair.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class DichvuSpinnerAdapter extends ArrayAdapter<DichvuItemSpinner> {
    public DichvuSpinnerAdapter(Context context, ArrayList<DichvuItemSpinner> dichvulist) {
        super(context, 0, dichvulist);

    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }
    private View initView(int position, View convertView, ViewGroup parent){
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.dichvu_spinner_row, parent, false
            );
        }
        TextView tenDV = convertView.findViewById(R.id.tendv);
        TextView thoiGian = convertView.findViewById(R.id.thoigian);
        TextView gia = convertView.findViewById(R.id.giaDV);
        CircleImageView imageView = convertView.findViewById(R.id.imageDichvusp);
        DichvuItemSpinner currentItem = getItem(position);
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        if (currentItem != null){
            String giafm = formatter.format(currentItem.getGia());
            tenDV.setText(currentItem.getTenDichvu());
            thoiGian.setText(String.valueOf(currentItem.getThoigian()+ " phút"));
            gia.setText(String.valueOf(giafm+ " VNĐ"));
            Picasso.get().load(API.URL + "/storage/dichvu/" + currentItem.getHinhAnh()).into(imageView);

        }
        return convertView;
    }
}

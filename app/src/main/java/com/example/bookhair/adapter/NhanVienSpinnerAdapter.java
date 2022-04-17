package com.example.bookhair.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bookhair.Class.NhanVienItemSpinner;
import com.example.bookhair.R;

import java.util.ArrayList;

public class NhanVienSpinnerAdapter extends ArrayAdapter<NhanVienItemSpinner> {

    public NhanVienSpinnerAdapter(Context context, ArrayList<NhanVienItemSpinner> nhanvienlist) {
        super(context, 0, nhanvienlist);
    }

    public NhanVienSpinnerAdapter(@NonNull Context context, int resource) {
        super(context, resource);
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

    private View initView(int position, View convertView, ViewGroup parent ){
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.spinner_item_nhanvien , parent, false
            );
        }
        TextView tenNhanVien = convertView.findViewById(R.id.txtTenNhanVien);
        TextView vaiTro = convertView.findViewById(R.id.txtVaiTro);
        NhanVienItemSpinner currentItem = getItem(position);
        if (currentItem != null){
            tenNhanVien.setText(currentItem.getTenNhanVien());
            vaiTro.setText(currentItem.getChucVu());
        }
        return convertView;
    }
}

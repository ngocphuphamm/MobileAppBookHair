package com.example.bookhair.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bookhair.Class.ThongBao;
import com.example.bookhair.R;

import java.util.List;

public class ThongBaoAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<ThongBao> lThongBao;

    public ThongBaoAdapter(Context context, int layout, List<ThongBao> lThongBao){
        this.context = context;
        this.layout = layout;
        this.lThongBao = lThongBao;
    }

    @Override
    public int getCount() {
        if(lThongBao != null){
            return lThongBao.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    private class ViewHolder{
        TextView textViewTenThongBao,textViewNoiDungThongBao,textViewNgayNhanThongBao;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder = new ViewHolder();
            holder.textViewTenThongBao = (TextView) view.findViewById(R.id.textViewTenThongBao);
            holder.textViewNoiDungThongBao = (TextView) view.findViewById(R.id.textViewNoiDungThongBao);
            holder.textViewNgayNhanThongBao = (TextView) view.findViewById(R.id.textViewNgayNhanThongBao);

            view.setTag(holder);
        } else {
            holder =(ViewHolder) view.getTag();
        }

        ThongBao thongBao =lThongBao.get(i);
        holder.textViewTenThongBao.setText(thongBao.getTenThongBao());
        holder.textViewNoiDungThongBao.setText(thongBao.getNoiDung());
        holder.textViewNgayNhanThongBao.setText(thongBao.getNgayThongBao());
        return view;
    }
}

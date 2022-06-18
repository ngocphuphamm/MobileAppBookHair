package com.example.bookhair.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.bookhair.API;
import com.example.bookhair.Class.Salon;
import com.example.bookhair.R;
import com.example.bookhair.ShowDetailSalonActivity;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TimKiemAdapter extends RecyclerView.Adapter<TimKiemAdapter.TimKiemViewHolder> implements Filterable {

    private List<Salon> salons;
    private List<Salon> salonsOld;

    public TimKiemAdapter(List<Salon> salons) {
        this.salons = salons;
        this.salonsOld = salons;
    }

    @NonNull
    @Override
    public TimKiemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_timkiem, parent, false);

        return new TimKiemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimKiemViewHolder holder, int position) {
        Salon salon = salons.get(position);
        if (salon == null){
            return;
        }
        Picasso.get().load(API.URL+"/storage/salon/"+salon.getImage()).into(holder.imagePoster);
        holder.txtAddress.setText(salon.getAddress());
        holder.txtName.setText(salon.getName());
        holder.ratingBar.setRating(salon.getRating());
        holder.imagePoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), ShowDetailSalonActivity.class);
                intent.putExtra("salonId", salon.getId());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (salons != null){
            return salons.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if (strSearch.isEmpty()){
                    salons = salonsOld;
                }else {
                    List<Salon> list = new ArrayList<>();
                    for (Salon salon : salonsOld){
                        if (salon.getName().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(salon);
                        }
                    }
                    salons = list;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = salons;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                salons = (List<Salon>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class TimKiemViewHolder extends RecyclerView.ViewHolder{
        private  RoundedImageView imagePoster;
        private  TextView txtName, txtAddress;
        private  RatingBar ratingBar;
        public TimKiemViewHolder(@NonNull View itemView) {
            super(itemView);
            imagePoster = itemView.findViewById(R.id.imagePoster);
            txtAddress = itemView.findViewById(R.id.textaddress);
            txtName = itemView.findViewById(R.id.textname);
            ratingBar = itemView.findViewById(R.id.ratingBar);

        }
    }
}

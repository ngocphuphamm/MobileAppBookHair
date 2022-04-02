package com.example.bookhair.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class SalonAdapter  extends RecyclerView.Adapter<SalonAdapter.SalonViewHolder>{

    private final ArrayList<Salon> salons;
    private Context context;

    public SalonAdapter(Context context, ArrayList<Salon> salons) {
        this.salons = salons;
        this.context = context;
    }

    @NonNull
    @Override
    public SalonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SalonViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_slider_home,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SalonViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Salon salon = salons.get(position);
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
        return salons.size();
    }

    static class SalonViewHolder extends RecyclerView.ViewHolder{
        private final RoundedImageView imagePoster;
        private final TextView txtName, txtAddress;
        private final RatingBar ratingBar;
        public SalonViewHolder(View view){
            super(view);
            imagePoster = view.findViewById(R.id.imagePoster);
            txtAddress = view.findViewById(R.id.textaddress);
            txtName = view.findViewById(R.id.textname);
            ratingBar = view.findViewById(R.id.ratingBar);
        }

    }
}

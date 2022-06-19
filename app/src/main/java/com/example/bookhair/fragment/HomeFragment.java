package com.example.bookhair.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bookhair.API;
import com.example.bookhair.Class.Dichvu;
import com.example.bookhair.Class.Salon;
import com.example.bookhair.Class.SalonhelperFeature;
import com.example.bookhair.R;
import com.example.bookhair.TimKiemActivity;
import com.example.bookhair.adapter.SalonAdapter;
import com.example.bookhair.adapter.noibatDichvuAdapter;
import com.example.bookhair.adapter.noibatSalonAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private LinearLayout  lnbando, lntimkiem;
    private  RecyclerView noibatRecycler, dvnoibatRecycler;
    private  RecyclerView.Adapter adapter;
    private ArrayList<Salon> salons;
    ArrayList<Dichvu> dichvus;
    ArrayList<SalonhelperFeature> noibatSalons;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View view;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        lntimkiem = (LinearLayout) view.findViewById(R.id.lnloc);
        lnbando = (LinearLayout) view.findViewById(R.id.lnbando);
        swipeRefreshLayout = view.findViewById(R.id.refeshHome);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setupSalonsViewPager();
                noibatRecycler();
                dvNoibatRecycler();
            }
        });
        lnbando.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getContext(), GoogleMap.class);
//                startActivity(intent);
            }
        });
        lntimkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), TimKiemActivity.class);
                startActivity(intent);
            }
        });
        setupSalonsViewPager();
        noibatRecycler =  view.findViewById(R.id.recycleNoibat);
        noibatRecycler();
        dvnoibatRecycler = view.findViewById(R.id.recycleDvNoiBat);
        dvNoibatRecycler();
        return view;
    }
    private void noibatRecycler() {
        noibatRecycler.setHasFixedSize(true);
        noibatRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        noibatSalons = new ArrayList<>();
        swipeRefreshLayout.setRefreshing(true);
        StringRequest request = new StringRequest(Request.Method.GET, API.GET_SALON_FEATURE, response -> {

            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")){
                    JSONArray array = new JSONArray(object.getString("salon"));
                    for (int i = 0; i< array.length(); i++){
                        JSONObject salonObject = array.getJSONObject(i);
                        SalonhelperFeature salon = new SalonhelperFeature();
                        salon.setId_salon(salonObject.getInt("id"));
                        salon.setTitle(salonObject.getString("tenSalon"));
                        salon.setAddress(salonObject.getString("diaChi"));
                        salon.setImage(salonObject.getString("hinhAnh"));

                        noibatSalons.add(salon);

                    }
                    noibatRecycler.setAdapter(new noibatSalonAdapter(getContext(), noibatSalons));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            swipeRefreshLayout.setRefreshing(false);
        }, error -> {
            error.printStackTrace();
            swipeRefreshLayout.setRefreshing(false);
        }){

        };

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);


    }
    private void dvNoibatRecycler() {
        dvnoibatRecycler.setHasFixedSize(true);
        dvnoibatRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        dichvus = new ArrayList<>();
        swipeRefreshLayout.setRefreshing(true);
        StringRequest request = new StringRequest(Request.Method.GET, API.GET_DICHVU, response -> {

            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")){
                    JSONArray array = new JSONArray(object.getString("dichvu"));
                    for (int i = 0; i< array.length(); i++){
                        JSONObject dichvuObject = array.getJSONObject(i);
                        Dichvu dichvu = new Dichvu();
                        dichvu.setId_dichvu(dichvuObject.getInt("id"));
                        dichvu.setTendv(dichvuObject.getString("tenDichvu"));
                        dichvu.setGia(dichvuObject.getString("giaTien"));
                        dichvu.setImage(dichvuObject.getString("hinhanh"));
                        dichvu.setTensalon(dichvuObject.getString("tenSalon"));
                        dichvus.add(dichvu);

                    }
                    dvnoibatRecycler.setAdapter(new noibatDichvuAdapter(getContext(), dichvus));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            swipeRefreshLayout.setRefreshing(false);
        }, error -> {
            error.printStackTrace();
            swipeRefreshLayout.setRefreshing(false);
        }){

        };

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);

//        Drawable gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffeff400, 0xffaff600});
    }

    private void setupSalonsViewPager(){
        ViewPager2 salonViewPager = view.findViewById(R.id.salonViewPager);
        salonViewPager.setClipToPadding(false);
        salonViewPager.setClipChildren(false);
        salonViewPager.setOffscreenPageLimit(3);
        salonViewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(10));
        compositePageTransformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.85f + r * 0.15f);
        });
        salonViewPager.setPageTransformer(compositePageTransformer);
        salons = new ArrayList<>();
        swipeRefreshLayout.setRefreshing(true);
        StringRequest request = new StringRequest(Request.Method.POST, API.GET_SALON, response -> {

            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")){
                    JSONArray array = new JSONArray(object.getString("salon"));
                    for (int i = 0; i< array.length(); i++){
                        JSONObject salonObject = array.getJSONObject(i);
                        Salon salon = new Salon();
                        salon.setId(salonObject.getInt("id"));
                        salon.setName(salonObject.getString("tenSalon"));
                        salon.setAddress(salonObject.getString("diaChi"));
                        salon.setImage(salonObject.getString("hinhAnh"));
                        salon.setRating(salonObject.getInt("rating"));
                        salons.add(salon);

                    }
                    salonViewPager.setAdapter(new SalonAdapter(getContext(), salons));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            swipeRefreshLayout.setRefreshing(false);
        }, error -> {
            error.printStackTrace();
            swipeRefreshLayout.setRefreshing(false);
        }){

        };

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);

    }
}
package com.example.bookhair.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bookhair.API;
import com.example.bookhair.Class.SalonNoti;
import com.example.bookhair.R;
import com.example.bookhair.adapter.SaLonNotiAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LichSuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LichSuFragment extends Fragment {

    public static ListView lvThongBaoSapToi;
    public static ArrayList<SalonNoti> arraySalonNoti;
    private SaLonNotiAdapter solonNotiAdapter;
    private SharedPreferences userPref;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LichSuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FristFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SapToiFragment newInstance(String param1, String param2) {
        SapToiFragment fragment = new SapToiFragment();
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
        View view = inflater.inflate(R.layout.fragment_lichsu, container, false);



        return inflater.inflate(R.layout.fragment_lichsu, container, false);
    }

    public void mapping(){
        arraySalonNoti = new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.GET, API.GET_LICHHEN_DA_DAT, response -> {

            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")){
                    JSONArray array = new JSONArray(object.getString("lichhen"));
                    for (int i = 0; i< array.length(); i++){
                        JSONObject lichhenObject = array.getJSONObject(i);
                        JSONObject salonObject = lichhenObject.getJSONObject("salon");
                        JSONObject nhanvienObject = lichhenObject.getJSONObject("nhanvien");
                        SalonNoti salon = new SalonNoti();
                        salon.setId_salon(salonObject.getInt("id"));
                        salon.setId_lichhen(lichhenObject.getString("_id"));
                        salon.setTrangThai(lichhenObject.getString("status"));
                        salon.setThoiGian(lichhenObject.getString("ngayHen"));
                        salon.setHinhAnh(salonObject.getString("hinhAnh"));
                        salon.setDiaChi(salonObject.getString("diaChi"));
                        salon.setNhanVienCatToc(nhanvienObject.getString("hoTen"));
                        salon.setTenSalon(salonObject.getString("tenSalon"));
                        arraySalonNoti.add(salon);

                    }
                    solonNotiAdapter = new SaLonNotiAdapter(getActivity(), R.layout.lichhensaptoi_view,arraySalonNoti);
                    lvThongBaoSapToi.setAdapter(solonNotiAdapter);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            error.printStackTrace();
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = userPref.getString("token", "");
                HashMap<String,String> map = new HashMap<>();
                String user = userPref.getString("userId","");
                map.put("Authorization","Bearer "+token);
                map.put("userId",user);
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userPref = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        lvThongBaoSapToi = view.findViewById(R.id.lvLichSu);
        mapping();

    }
}
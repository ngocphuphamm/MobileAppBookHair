package com.example.bookhair.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

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
import com.example.bookhair.Class.ThongBao;
import com.example.bookhair.R;
import com.example.bookhair.adapter.ThongBaoAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThongBaoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThongBaoFragment extends Fragment {
    private ListView lvThongBao;
    private ArrayList<ThongBao> thongBaoArray;
    private ThongBaoAdapter thongBaoAdapter;
    private View view;
    private SharedPreferences userPref;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ThongBaoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThongBaoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ThongBaoFragment newInstance(String param1, String param2) {
        ThongBaoFragment fragment = new ThongBaoFragment();
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
        view = inflater.inflate(R.layout.fragment_thong_bao, container, false);
        userPref = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        mapping();
        thongBaoAdapter = new ThongBaoAdapter(getContext(), R.layout.thongbao_view, thongBaoArray);
        lvThongBao.setAdapter(thongBaoAdapter);
        return view;
    }
    public void mapping(){
        lvThongBao = (ListView) view.findViewById(R.id.lvThongBao);
        thongBaoArray = new ArrayList<>();
        String user = userPref.getString("userId","");


        StringRequest request = new StringRequest(Request.Method.GET, API.GET_THONGBAO +"/"+user, response -> {

            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")){
                    JSONArray array = new JSONArray(object.getString("thongBao"));
                    for (int i = 0; i< array.length(); i++){
                        JSONObject thongBaoObject = array.getJSONObject(i);
                        ThongBao thongBao = new ThongBao();
                        thongBao.setId_salon(thongBaoObject.getInt("salon_id"));
                        thongBao.setTenThongBao(thongBaoObject.getString("noiDung"));
                        thongBao.setNoiDung(thongBaoObject.getString("chiTietNoiDung"));
//                        thongBao.setNgayThongBao(thongBaoObject.getString("created_at"));
                        thongBaoArray.add(thongBao);

                    }
                    thongBaoAdapter = new ThongBaoAdapter(getActivity(), R.layout.thongbao_view,thongBaoArray);
                    lvThongBao.setAdapter(thongBaoAdapter);
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
                map.put("Authorization","Bearer "+token);
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);

    }
}
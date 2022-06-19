package com.example.bookhair;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bookhair.Class.Salon;
import com.example.bookhair.adapter.TimKiemAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TimKiemActivity extends AppCompatActivity {

    private RecyclerView recyclerViewTK;
    private TimKiemAdapter timKiemAdapter;
    private ArrayList<Salon> salons;
    private Toolbar toolBar;
    private SearchView searchView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageButton btn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_tim_kiem);
        recyclerViewTK = findViewById(R.id.recycleTimkiem);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewTK.setLayoutManager(linearLayoutManager);
        toolBar = findViewById(R.id.toolbarTK);
        swipeRefreshLayout = findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getListSalon();
            }
        });
        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TimKiemActivity.this, DashboardActivity.class));
            }
        });
        this.setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getListSalon();

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerViewTK.addItemDecoration(itemDecoration);
    }

    private void  getListSalon() {
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
                    timKiemAdapter = new TimKiemAdapter(salons);
                    recyclerViewTK.setAdapter(timKiemAdapter);
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

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_timkiem, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                timKiemAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                timKiemAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()){
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }
}
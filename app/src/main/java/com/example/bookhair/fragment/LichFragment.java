package com.example.bookhair.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookhair.R;
import com.example.bookhair.adapter.LichSuFragmentAdapter;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LichFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LichFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager2 pager2;
    private LichSuFragmentAdapter adapter;
    private View view;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LichFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LichFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LichFragment newInstance(String param1, String param2) {
        LichFragment fragment = new LichFragment();
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
        view = inflater.inflate(R.layout.fragment_lich, container, false);
        tabLayout = view.findViewById(R.id.tab_layout);
        pager2 = view.findViewById(R.id.view_page2);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        adapter = new LichSuFragmentAdapter(fm, getLifecycle());
        pager2.setAdapter(adapter);

        tabLayout.addTab(tabLayout.newTab().setText("Chưa xác nhận"));
        tabLayout.addTab(tabLayout.newTab().setText("Đã xác nhận"));
        tabLayout.addTab(tabLayout.newTab().setText("Lịch sử"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
        return view;
    }
}
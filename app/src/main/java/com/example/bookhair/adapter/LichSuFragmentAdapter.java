package com.example.bookhair.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.bookhair.fragment.DaDuyetFragment;
import com.example.bookhair.fragment.LichSuFragment;
import com.example.bookhair.fragment.SapToiFragment;

public class LichSuFragmentAdapter extends FragmentStateAdapter {

    public LichSuFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0:
                return new SapToiFragment();

            case 1:
                return new DaDuyetFragment();

            case 2:
                return new LichSuFragment();
        }

        return new SapToiFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}

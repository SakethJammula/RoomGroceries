package com.example.roomgroceries;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class OrderTabAdapter extends FragmentStateAdapter {
    private final int totalTabSize;

    public OrderTabAdapter(FragmentActivity fm, int totalTabSize) {
        super(fm);
        this.totalTabSize = totalTabSize;
    }

    /**
     * Display the tab layout with ViewPager2
     * @param position tab index
     * @return the fragment
     */
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Log.e(MainActivity.TAG, "Called position : "+position);
        switch (position) {
            case 0:
                return new OrderListFragment();
            case 1:
                return new PurchasedOrderFragment();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return totalTabSize;
    }
}

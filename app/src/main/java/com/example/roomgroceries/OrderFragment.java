package com.example.roomgroceries;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class OrderFragment extends Fragment {
    /**
     * Create the Fragment Instance
     * @param savedInstanceState Saves the current state instance of the app
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Inflate the fragment to display the order in the order list
     * @param inflater inflates the fragment
     * @param container holds the fragment
     * @param savedInstanceState Saves the current state instance of the app
     * @return current inflated view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order, container, false);
    }
}
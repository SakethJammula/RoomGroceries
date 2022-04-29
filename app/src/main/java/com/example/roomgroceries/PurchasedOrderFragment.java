package com.example.roomgroceries;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PurchasedOrderFragment extends Fragment {
    private static final String dollar = "$";
    private static final String resetAmount = dollar + "0.00";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_purchased_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedStateInstance) {
        RecyclerView purchasedRecycler = view.findViewById(R.id.purchase_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        purchasedRecycler.setLayoutManager(layoutManager);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("purchasedlist");

        ArrayList<AddItem> purchasedList = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    AddItem item = postSnapshot.getValue(AddItem.class);
                    purchasedList.add(item);
                }

                RecyclerView.Adapter<PurchasedOrderRecycler.PurchasedItem> adapter =
                        new PurchasedOrderRecycler(purchasedList);
                purchasedRecycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        TextView displayAmount = view.findViewById(R.id.display_amount);
        Button settle = view.findViewById(R.id.button4);

        displayAmount.setText(dollar + "1.23");
        settle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayAmount.setText(resetAmount);
            }
        });

    }
}
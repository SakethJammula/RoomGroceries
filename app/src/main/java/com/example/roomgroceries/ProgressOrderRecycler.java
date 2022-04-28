package com.example.roomgroceries;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProgressOrderRecycler extends RecyclerView.Adapter<ProgressOrderRecycler.PurchasedItem> {
    private final ArrayList<AddItem> itemList;

    public ProgressOrderRecycler(ArrayList<AddItem> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public PurchasedItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_order,
                parent, false);
        return new PurchasedItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PurchasedItem holder, int position) {
        AddItem item = itemList.get(position);

        holder.itemName.setText(item.getItemName());
        holder.itemQuantity.setText(item.getItemQuantity());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class PurchasedItem extends RecyclerView.ViewHolder {
        TextView itemName;
        TextView itemQuantity;

        public PurchasedItem(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.textView7);
            itemQuantity = itemView.findViewById(R.id.textView8);
        }
    }
}

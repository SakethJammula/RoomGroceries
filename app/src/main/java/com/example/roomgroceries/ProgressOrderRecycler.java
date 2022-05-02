package com.example.roomgroceries;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProgressOrderRecycler extends RecyclerView.Adapter<ProgressOrderRecycler.PurchasedItem> {
    private static ArrayList<AddItem> itemList = null;
    private static ArrayList<String> itemKey = null;
    private final RecyclerView recyclerView;

    public ProgressOrderRecycler(ArrayList<AddItem> itemList, RecyclerView recyclerView,
                                 ArrayList<String> itemKey) {
        ProgressOrderRecycler.itemList = itemList;
        this.recyclerView = recyclerView;
        ProgressOrderRecycler.itemKey = itemKey;
    }

    /**
     * Display the list of items on the RecyclerView on the Progress tab
     * @param parent parentView
     * @param viewType type of view
     * @return instance of the class PurchasedItem
     */
    @NonNull
    @Override
    public PurchasedItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_order,
                parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                AddItem item = itemList.get(recyclerView.getChildAdapterPosition(view));
                intent.putExtra("itemName", item.getItemName());
                intent.putExtra("itemQuantity", item.getItemQuantity());
                intent.putExtra("itemKey", itemKey.get(recyclerView.getChildAdapterPosition(view)));
                intent.setClass(view.getContext(), EditOrderActivity.class);
                view.getContext().startActivity(intent);
            }
        });
        return new PurchasedItem(view);
    }

    /**
     * Display the item in the corresponding index on the RecyclerView
     * @param holder PurchasedItem
     * @param position position of the view on the RecyclerView
     */
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

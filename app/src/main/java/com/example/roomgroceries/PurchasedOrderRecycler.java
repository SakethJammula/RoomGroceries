package com.example.roomgroceries;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PurchasedOrderRecycler extends RecyclerView.Adapter<PurchasedOrderRecycler.PurchasedItem> {
    ArrayList<AddItem> purchasedList;

    public PurchasedOrderRecycler(ArrayList<AddItem> purchasedList) {
        this.purchasedList = purchasedList;
    }


    /**
     * Display the list of items on the RecyclerView on the Purchase tab
     * @param parent parentView
     * @param viewType type of view
     * @return instance of the class PurchasedItem
     */
    @NonNull
    @Override
    public PurchasedItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_order,
                parent, false);
        return new PurchasedItem(view);
    }

    /**
     * Display the item in the corresponding index on the RecyclerView
     * @param holder PurchasedItem
     * @param position position of the view on the RecyclerView
     */
    @Override
    public void onBindViewHolder(@NonNull PurchasedItem holder, int position) {
        AddItem item = purchasedList.get(position);
        String quantity = "Qty: ";
        String dollar = "$";


        holder.itemName.setText(item.getItemName());
        holder.itemQuantity.setText(quantity.concat(item.getItemQuantity()));

        if(item.getItemPrice() !=null){
            holder.itemPrice.setText(dollar.concat(item.getItemPrice()));
        }

    }

    @Override
    public int getItemCount() {
        return purchasedList.size();
    }

    public static class PurchasedItem extends RecyclerView.ViewHolder {
        TextView itemName;
        TextView itemQuantity;
        TextView itemPrice;

        public PurchasedItem(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.textView7);
            itemQuantity = itemView.findViewById(R.id.textView8);
            itemPrice = itemView.findViewById(R.id.textView9);
        }
    }
}

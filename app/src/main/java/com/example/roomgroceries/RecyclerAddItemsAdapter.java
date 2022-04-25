package com.example.roomgroceries;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAddItemsAdapter extends RecyclerView.Adapter<RecyclerAddItemsAdapter.AddItemAdapter> {
    @NonNull
    @Override
    public AddItemAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext()).inflate(R.layout.accept_items, parent, false );
        return new AddItemAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddItemAdapter holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class AddItemAdapter extends RecyclerView.ViewHolder {
        EditText addItem;
        EditText enterQuantity;
        ImageButton cancelButton;

        public AddItemAdapter(@NonNull View itemView) {
            super(itemView);

            addItem = itemView.findViewById(R.id.add_items);
            enterQuantity = itemView.findViewById(R.id.quantity);
            cancelButton = itemView.findViewById(R.id.imageButton);
        }
    }
}

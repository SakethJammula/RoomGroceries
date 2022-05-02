package com.example.roomgroceries;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ItemPriceDialog extends DialogFragment {
    TextView itemText;
    EditText amountEntered;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("totalcost");
    final String itemName;
    final View view;

    public ItemPriceDialog(String itemName, View view) {
        this.itemName = itemName;
        this.view = view;
    }

    /**
     * Dialog to accept price of the item on purchase
     * @param savedInstanceState Saves the current state instance of the app
     * @return created Dialog
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.add_price_of_items,
                (ViewGroup) getActivity().findViewById(R.id.price_dialog));

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(layout);
        builder.setTitle("Price");

        itemText = layout.findViewById(R.id.dialog_item_name);
        itemText.setText(itemName);
        amountEntered = layout.findViewById(R.id.item_cost);

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.setPositiveButton(R.string.Save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String amount = amountEntered.getText().toString();

                if (amount.length() > 0) {
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            DataSnapshot postSnapshot = snapshot.getChildren().iterator().next();
                            AddItem.OrderCost currentCost = postSnapshot.getValue(AddItem.OrderCost.class);

                            if (currentCost != null && postSnapshot.getKey() != null) {
                                currentCost.totalCost = Float.parseFloat(amount) + currentCost.totalCost;
                                myRef.child(postSnapshot.getKey()).setValue(currentCost);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    EditOrderActivity.update_purchase_list(view, amount);
                }
            }
        });

        return builder.create();
    }
}

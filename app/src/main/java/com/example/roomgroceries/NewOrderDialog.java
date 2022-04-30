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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewOrderDialog extends DialogFragment {
    EditText newItem;
    EditText newItemQuantity;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.add_item_dialog,
                (ViewGroup) getActivity().findViewById(R.id.dialog_add_item));

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(layout);
        builder.setTitle("Add Item");

        newItem = layout.findViewById(R.id.dialog_item_name);
        newItemQuantity = layout.findViewById(R.id.dialog_enter_quantity);

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.setPositiveButton(R.string.Save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String itemName = newItem.getText().toString();
                String quantity = newItemQuantity.getText().toString();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("orderlist");

                AddItem addItem = new AddItem(itemName, quantity);

                myRef.push().setValue(addItem).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(layout.getContext(), "Item Added Successfully",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return builder.create();
    }
}

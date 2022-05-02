package com.example.roomgroceries;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditOrderActivity extends AppCompatActivity {
    static String itemKey;
    static EditText quantityView;
    static String itemName;
    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    static DatabaseReference myRef = database.getReference("orderlist");

    /**
     * Loads the Edit Order layout and helps the user to edit, delete and purchase the item
     * @param savedStateInstance Saves the current state instance of the app
     */
    @Override
    public void onCreate(Bundle savedStateInstance) {
        super.onCreate(savedStateInstance);
        setContentView(R.layout.activity_edit_order);

        TextView itemNameView = findViewById(R.id.textView4);
        quantityView = findViewById(R.id.edit_quantity);
        Button modifyButton = findViewById(R.id.modify_button);
        Button purchaseButton = findViewById(R.id.purchase_button);
        Button deleteButton = findViewById(R.id.delete_button);

        Intent intent = getIntent();

        itemName = intent.getStringExtra("itemName");
        String itemQuantity = intent.getStringExtra("itemQuantity");
        itemKey = intent.getStringExtra("itemKey");

        itemNameView.setText(itemName);
        quantityView.setText(itemQuantity);

        ActionBar ab = getSupportActionBar();
        ab.setTitle("Edit Order");
        ab.setDisplayHomeAsUpEnabled(true);

        modifyButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Method invoked after pressing the modify button and helps updating the quantity of the item
             * @param view Current view of the app
             */
            @Override
            public void onClick(View view) {
                String updatedQuantity = quantityView.getText().toString();
                AddItem updatedItem = new AddItem(itemName, updatedQuantity, null);
                myRef.child(itemKey).setValue(updatedItem);

                Intent newIntent = new Intent();
                newIntent.setClass(view.getContext(), ViewOrderHistory.class);
                startActivity(newIntent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Method invoked after pressing the delete button and helps deleting the item
             * @param view Current view of the app
             */
            @Override
            public void onClick(View view) {
                move_to_order_history(view, itemKey);
            }
        });

        purchaseButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Invokes the Dialog to input the price of the items
             * @param view Current view of the app
             */
            @Override
            public void onClick(View view) {
                ItemPriceDialog newDialog = new ItemPriceDialog(itemName, view);
                newDialog.show(getSupportFragmentManager(), null);
            }
        });
    }

    /**
     * Remove item from either the Progress/Purchase list
     * @param view Current view of the app
     * @param itemKey The key of the item in the firebase
     */
    public static void move_to_order_history(View view, String itemKey) {
        myRef.child(itemKey).removeValue();
        Intent newIntent = new Intent();
        newIntent.setClass(view.getContext(), ViewOrderHistory.class);
        view.getContext().startActivity(newIntent);
    }

    /**
     * Helper to update the purchase list with individual prices
     * @param view Current view of the app
     * @param itemPrice The price of the item taken from the firebase
     */
    public static void update_purchase_list(View view, String itemPrice) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference newRef = database.getReference("purchasedlist");
        String updatedQuantity = quantityView.getText().toString();
        AddItem updatedItem = new AddItem(itemName, updatedQuantity, itemPrice);

        newRef.push().setValue(updatedItem).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(view.getContext(), "Item Purchased",
                        Toast.LENGTH_SHORT).show();
            }
        });
        move_to_order_history(view, itemKey);
    }
}

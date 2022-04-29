package com.example.roomgroceries;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditOrderActivity extends AppCompatActivity {
    private Button modifyButton;
    private Button purchaseButton;
    private Button deleteButton;
    private String itemName;
    private String itemQuantity;
    private String itemKey;
    private TextView itemNameView;
    private EditText quantityView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("orderlist");

    @Override
    public void onCreate(Bundle savedStateInstance) {
        super.onCreate(savedStateInstance);
        setContentView(R.layout.activity_edit_order);

        itemNameView = findViewById(R.id.textView4);
        quantityView = findViewById(R.id.edit_quantity);
        modifyButton = findViewById(R.id.modify_button);
        purchaseButton = findViewById(R.id.purchase_button);
        deleteButton = findViewById(R.id.delete_button);

        Intent intent = getIntent();

        itemName = intent.getStringExtra("itemName");
        itemQuantity = intent.getStringExtra("itemQuantity");
        itemKey = intent.getStringExtra("itemKey");

        itemNameView.setText(itemName);
        quantityView.setText(itemQuantity);

        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String updatedQuantity = quantityView.getText().toString();
                AddItem updatedItem = new AddItem(itemName, updatedQuantity);
                myRef.child(itemKey).setValue(updatedItem);

                Intent newIntent = new Intent();
                newIntent.setClass(view.getContext(), ViewOrderHistory.class);
                startActivity(newIntent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.child(itemKey).removeValue();

//                Intent newIntent = new Intent();
//                newIntent.setClass(view.getContext(), ViewOrderHistory.class);
//                startActivity(newIntent);

                move_to_order_history(view);
            }
        });

        purchaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("purchasedlist");
                String updatedQuantity = quantityView.getText().toString();
                AddItem updatedItem = new AddItem(itemName, updatedQuantity);

                myRef.push().setValue(updatedItem).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "Item Purchased",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                move_to_order_history(view);
            }
        });
    }

    public static void move_to_order_history(View view) {
        Intent newIntent = new Intent();
        newIntent.setClass(view.getContext(), ViewOrderHistory.class);
        view.getContext().startActivity(newIntent);
    }
}

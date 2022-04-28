package com.example.roomgroceries;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NewOrder extends AppCompatActivity {
    EditText enterItems;
    EditText enterQuantity;
    Button addItemButton;
    Button logout;
    private final ArrayList<AddItem> listOfItems = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedStateInstance) {
        super.onCreate(savedStateInstance);

        setContentView(R.layout.new_order);

        enterItems = findViewById(R.id.enter_items);
        enterQuantity = findViewById(R.id.quantity);
        addItemButton = findViewById(R.id.button6);
        logout = findViewById(R.id.button7);

        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemName;
                String quantity;
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("orderlist");

                itemName = enterItems.getText().toString();
                quantity = enterQuantity.getText().toString();
                AddItem addItem = new AddItem(itemName, quantity);
                listOfItems.add(addItem);

                myRef.push().setValue(addItem).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "Item Added Successfully",
                                Toast.LENGTH_SHORT).show();
                    }
                });

                enterItems.setText("");
                enterQuantity.setText("");
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), EmailAndPasswordLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}

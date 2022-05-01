package com.example.roomgroceries;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewOrder extends AppCompatActivity {
    EditText enterItems;
    EditText enterQuantity;
    Button addItemButton;
    Button logout;
    Button finalize;

    @Override
    public void onCreate(Bundle savedStateInstance) {
        super.onCreate(savedStateInstance);

        setContentView(R.layout.new_order);

        enterItems = findViewById(R.id.enter_items);
        enterQuantity = findViewById(R.id.quantity);
        addItemButton = findViewById(R.id.button6);
        logout = findViewById(R.id.button7);
        finalize = findViewById(R.id.button8);

        ActionBar ab = getSupportActionBar();
        ab.setTitle("New Order");
        ab.setDisplayHomeAsUpEnabled(true);

        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemName;
                String quantity;
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("orderlist");

                itemName = enterItems.getText().toString();
                quantity = enterQuantity.getText().toString();
                if(itemName.length()==0 || quantity.length() == 0){
                    Toast.makeText(getApplicationContext(), "Enter valid data!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                AddItem addItem = new AddItem(itemName, quantity, null);

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

        finalize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ViewOrderHistory.class);
                startActivity(intent);
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

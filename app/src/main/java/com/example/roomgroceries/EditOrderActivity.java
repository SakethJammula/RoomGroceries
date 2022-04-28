package com.example.roomgroceries;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class EditOrderActivity extends AppCompatActivity {
    private Button modifyButton;
    private Button purchaseButton;
    private Button deleteButton;
    private String itemName;
    private String itemQuantity;
    private TextView itemNameView;
    private EditText quantityView;

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

        itemNameView.setText(itemName);
        quantityView.setText(itemQuantity);

        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}

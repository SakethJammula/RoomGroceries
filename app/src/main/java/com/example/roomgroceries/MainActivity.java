package com.example.roomgroceries;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    TextView welcomeText;
    Button newOrderButton;
    Button viewHistoryButton;
    final static String welcome = "Welcome ";
    public final static String TAG = "RoomGroceries";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        welcomeText = findViewById(R.id.textView2);
        welcomeText.setText(welcome.concat(Objects.requireNonNull(currentUser.getDisplayName())));
        newOrderButton = findViewById(R.id.button2);
        viewHistoryButton = findViewById(R.id.button3);

        newOrderButton.setOnClickListener(new OrderClickListener());
    }

    private class OrderClickListener implements View.OnClickListener {
        private boolean isOrderInProgress = false;

        @Override
        public void onClick(View view) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("isOrderInProgress");

            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for ( DataSnapshot postSnapshot: snapshot.getChildren() ) {
                        if (postSnapshot.getValue(Boolean.parseBoolean(postSnapshot.getKey())) != null) {
                            isOrderInProgress = (boolean) postSnapshot.getValue(Boolean.parseBoolean(postSnapshot.getKey()));
                            Log.d(TAG, "Value retrieved "+isOrderInProgress);
                            break;
                        }
                    }

                    if (!isOrderInProgress) {
                        Log.d(TAG, "First");
                        myRef.push().setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d(TAG, "Added data successfully");
                            }
                        });

                        Intent intent = new Intent(getApplicationContext(), AddItems.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(view.getContext(),
                                "Order already in progress.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getApplicationContext(),EmailAndPasswordLoginActivity.class);
        startActivity(intent);
        finish();
    }
}
package com.example.roomgroceries;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
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
    FirebaseUser currentUser = null;
    TextView welcomeText;
    Button newOrderButton;
    Button viewHistoryButton;
    Button viewParticipants;
    final static String welcome = "Welcome ";
    public final static String TAG = "RoomGroceries";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar ab = getSupportActionBar();
        ab.setTitle("Welcome");
        ab.setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.hasChild("totalcost")) {
                    AddItem.OrderCost totalCost = new AddItem.OrderCost(0.0f);
                    DatabaseReference newRef = database.getReference("totalcost");
                    newRef.push().setValue(totalCost).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference users = database.getReference("users");
        final boolean[] isUserNameAdded = {true};

        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    AddItem.Users addedUser = postSnapshot.getValue(AddItem.Users.class);

                    if (addedUser.getUserName().equals(currentUser.getDisplayName())) {
                        isUserNameAdded[0] = false;
                        break;
                    }
                }

                if (isUserNameAdded[0]) {
                    AddItem.Users newUser = new AddItem.Users(currentUser.getDisplayName(), 0.0f);
                    users.push().setValue(newUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        welcomeText = findViewById(R.id.textView2);
        welcomeText.setText(welcome.concat(Objects.requireNonNull(currentUser.getDisplayName())));
        newOrderButton = findViewById(R.id.button2);
        viewHistoryButton = findViewById(R.id.button3);
        viewParticipants = findViewById(R.id.view_participants);

        newOrderButton.setOnClickListener(new OrderClickListener());
        viewHistoryButton.setOnClickListener(new ViewOrderClickListener());
        viewParticipants.setOnClickListener(new ViewParticipants());
    }

    private class OrderClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), NewOrder.class);
            startActivity(intent);
        }
    }

    private class ViewOrderClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), ViewOrderHistory.class);
            startActivity(intent);
        }
    }

    private class ViewParticipants implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), Participants.class);
            startActivity(intent);
        }
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getApplicationContext(),EmailAndPasswordLoginActivity.class);
        startActivity(intent);
        finish();
    }
}
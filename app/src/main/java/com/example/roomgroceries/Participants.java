package com.example.roomgroceries;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Participants extends AppCompatActivity {
    private LinearLayout verticalLayout;

    @Override
    public void onCreate(Bundle savedStateInstance) {
        super.onCreate(savedStateInstance);

        setContentView(R.layout.participants);

        TextView title = new TextView(getApplicationContext());
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        verticalLayout = findViewById(R.id.participant_layout);
        title.setText("Money Spent");
        title.setLayoutParams(textParams);
        title.setTextColor(getResources().getColor(R.color.black));
        title.setTextSize(24);
        verticalLayout.addView(title);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    AddItem.Users users = postSnapshot.getValue(AddItem.Users.class);
                    set_layout_for_participants(users.userName, String.valueOf(users.memberSpent));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void set_layout_for_participants(String name, String amount) {
        LinearLayout horizontal = new LinearLayout(getApplicationContext());
        TextView participantName = new TextView(getApplicationContext());
        TextView amountSpent = new TextView(getApplicationContext());
        String dollar = "$";

        horizontal.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams textParamsWithWeight = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.5f);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        horizontal.setLayoutParams(params);

        participantName.setLayoutParams(textParamsWithWeight);
        participantName.setTextColor(getResources().getColor(R.color.black));
        participantName.setTextSize(24);
        participantName.setText(name);

        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        amountSpent.setLayoutParams(textParams);
        amountSpent.setTextColor(getResources().getColor(R.color.black));
        amountSpent.setTextSize(24);
        amountSpent.setText(dollar.concat(amount));

        horizontal.addView(participantName);
        horizontal.addView(amountSpent);
        horizontal.layout(10, 10, 10, 10);
        horizontal.setPadding(10, 10, 10, 10);

        verticalLayout.addView(horizontal);
    }
}

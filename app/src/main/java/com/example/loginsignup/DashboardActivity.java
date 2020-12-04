package com.example.loginsignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashboardActivity extends AppCompatActivity {

    CardView add_Attendance;

    TextView welcomeText;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase rootNode;
    DatabaseReference locationReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        add_Attendance= findViewById(R.id.card1);

        welcomeText =  findViewById(R.id.txt_welcome);
        firebaseAuth = FirebaseAuth.getInstance();

        rootNode = FirebaseDatabase.getInstance();
//          locationReference = rootNode.getReference("location");
//          DatabaseReference userReference = rootNode.getReference("Users/" + firebaseAuth.getUid());

        DatabaseReference fullNameReference = rootNode.getReference("Users/" + firebaseAuth.getUid() + "/fullname");

        fullNameReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String fullName = dataSnapshot.getValue(String.class);
                welcomeText.setText("Welcome! \n" + fullName);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                welcomeText.setText("Welcome!");
            }
        });

//        thisActivity = getActivity();
        add_Attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AttendanceActivity.class));
//                finish();

            }
        });

    }
}
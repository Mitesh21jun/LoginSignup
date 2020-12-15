package com.example.loginsignup;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginsignup.adapter.ViewAttendanceAdapter;
import com.example.loginsignup.helper.LocationHelper;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewAttendanceActivity extends AppCompatActivity {

    RecyclerView attendanceRv;
    String attendanceStr;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    LocationHelper locationHelper;

    ViewAttendanceAdapter viewAttendanceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);

        firebaseAuth = FirebaseAuth.getInstance();

        attendanceRv = (RecyclerView) findViewById(R.id.attendance_rv);

        attendanceRv.setLayoutManager(new LinearLayoutManager(this));
        databaseReference = FirebaseDatabase.getInstance().getReference().child("location");
        attendanceStr = databaseReference.toString();
        FirebaseRecyclerOptions<LocationHelper> options =
                new FirebaseRecyclerOptions.Builder<LocationHelper>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("location").child(firebaseAuth.getUid()), LocationHelper.class)
                        .build();


        viewAttendanceAdapter = new ViewAttendanceAdapter(options);
        attendanceRv.setAdapter(viewAttendanceAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        viewAttendanceAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        viewAttendanceAdapter.stopListening();
    }
}
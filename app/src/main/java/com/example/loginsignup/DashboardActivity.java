package com.example.loginsignup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.loginsignup.fragment.AddAttendanceFragment;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class DashboardActivity extends AppCompatActivity {

    CardView add_Attendance=findViewById(R.id.card1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

//        add_Attendance.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent attendanceIntent = new Intent();
//                startActivity(new Intent(getApplicationContext(), AttendanceActivity.class));
//                finish();
//            }
      //  });


    }
}
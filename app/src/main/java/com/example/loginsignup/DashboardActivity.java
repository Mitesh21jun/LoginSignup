package com.example.loginsignup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.loginsignup.fragment.AddAttendanceFragment;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class DashboardActivity extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        chipNavigationBar = findViewById(R.id.bottom_nav_menu);
        getSupportFragmentManager().beginTransaction().replace(R.id.navbar_fragment_container,new AddAttendanceFragment()).commit();

        bottomMenu();

    }

    private void bottomMenu() {

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {

                Fragment fragment = null;
                switch (i){
                    case R.id.bottom_nav_addlocation:
                        fragment=new AddAttendanceFragment();
                        break;

                    case R.id.bottom_nav_viewlocation:
                        fragment=new AddAttendanceFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.navbar_fragment_container,fragment).commit();
            }
        });

    }


}

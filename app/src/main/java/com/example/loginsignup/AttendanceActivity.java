package com.example.loginsignup;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.Locale;

public class AttendanceActivity extends AppCompatActivity {

    FusedLocationProviderClient fusedLocationProviderClient;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    private Button btn ,signout;
    private TextView txt;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        firebaseAuth = FirebaseAuth.getInstance();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        btn = findViewById(R.id.locbtn);
        txt = findViewById(R.id.loctxt);
        signout = findViewById(R.id.signout);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        //get the loacation here


                        fusedLocationProviderClient.getLastLocation()
                                .addOnSuccessListener(new OnSuccessListener<Location>() {
                                    @Override
                                    public void onSuccess(Location location) {
                                        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                                        if (location != null) {

                                            rootNode = FirebaseDatabase.getInstance();
                                            reference = rootNode.getReference("location");

                                            Double lat = location.getLatitude();
                                            Double lng = location.getLongitude();

                                            txt.setText("Hello!  "+firebaseAuth.getCurrentUser().getEmail()+"\n Your location is "+lat + ", " + lng);
                                            Toast.makeText(AttendanceActivity.this, "Success", Toast.LENGTH_SHORT);

// Get a reference to our posts


                                            //get values from text field

                                            UserHelper helperclass = new UserHelper(lat.toString(), lng.toString(), currentTime);

//                                            reference.child(currentTime).setValue(helperclass);

                                            reference.child(Build.MANUFACTURER + " " + Build.MODEL).child(currentTime).setValue(helperclass);

                                            //mFirebaseAnalytics.logEvent(String.valueOf(a),null);

                                        }
                                    }
                                });
                    } else {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                    }
                }
            }
        });

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });
    }
}
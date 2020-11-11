package com.example.loginsignup.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.example.loginsignup.AttendanceActivity;
import com.example.loginsignup.R;
import com.example.loginsignup.helper.SignUpHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpTabFragment extends Fragment {

    FirebaseDatabase rootNode;
    DatabaseReference reference1;
    FirebaseAuth fAuth;
    EditText txtFullName, txtUserName, txtDesignation, txtEmail, txtMobile, txtPass, txtConfPass;
    Button signUp;
    LottieAnimationView validationLottieAnim;
    View view;
    LoginTabFragment loginTabFragment = new LoginTabFragment();


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment, container, false);
        fAuth = FirebaseAuth.getInstance();

        txtFullName = root.findViewById(R.id.full_name);
        txtUserName = root.findViewById(R.id.username);
        txtDesignation = root.findViewById(R.id.designation);
        txtMobile = root.findViewById(R.id.get_mobile);
        txtEmail = root.findViewById(R.id.get_email);
        txtPass = root.findViewById(R.id.get_pass);
        txtConfPass = root.findViewById(R.id.repeat_pass);
        signUp = root.findViewById(R.id.signup);
        validationLottieAnim = root.findViewById(R.id.validationanim);

        //Animation
        loginTabFragment.animateX(txtFullName, 300);
        loginTabFragment.animateX(txtUserName, 600);
        loginTabFragment.animateX(txtDesignation, 900);
        loginTabFragment.animateX(txtMobile, 1200);
        loginTabFragment.animateX(txtEmail, 1500);
        loginTabFragment.animateX(txtPass, 1800);
        loginTabFragment.animateX(txtConfPass, 2100);

        rootNode = FirebaseDatabase.getInstance();
        reference1 = rootNode.getReference("Users");

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                view = v;           //to use view in Snackbar


                final String isRequiredMsg = " is Required !";
                final String fullName = txtFullName.getText().toString();
                final String userName = txtUserName.getText().toString();
                final String designation = txtDesignation.getText().toString();
                final String mobile = txtMobile.getText().toString();
                final String email = txtEmail.getText().toString();
                final String pass = txtPass.getText().toString();
                final String confPass = txtConfPass.getText().toString();

                Pattern p = Pattern.compile("(0/91)?[7-9][0-9]{9}");
                Matcher m = p.matcher(mobile);

                if (fullName.isEmpty()) {
                    txtFullName.setError("First name" + isRequiredMsg);
                } else if (userName.isEmpty()) {
                    txtUserName.setError("Last name" + isRequiredMsg);
                } else if (mobile.isEmpty()) {
                    txtMobile.setError("Mobile" + isRequiredMsg);
                } else if (mobile.length() != 10) {
                    txtMobile.setError("Enter a valid 10 digit mobile number");
                } else if (!(m.find() && m.group().equals(mobile))) {
                    txtMobile.setError("Enter Valid Mobile number type");
                } else if (email.isEmpty()) {
                    txtEmail.setError("Email" + isRequiredMsg);
                } else if (pass.isEmpty()) {
                    txtPass.setError("Password" + isRequiredMsg);
                } else if (pass.length() < 6) {
                    txtPass.setError("Min 6 digit password" + isRequiredMsg);
                } else if (confPass.isEmpty()) {
                    txtConfPass.setError("Password" + isRequiredMsg);
                } else if (!pass.equals(confPass)) {
                    txtConfPass.setError("Both passwords should match");
                } else {

                    fAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            validationLottieAnim.setVisibility(View.VISIBLE);
                            if (task.isSuccessful()) {
                                try {
                                    SignUpHelper signUpHelper = new SignUpHelper(fullName, userName, designation, mobile, email);
                                    reference1.child(fAuth.getUid()).setValue(signUpHelper);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                new Timer().schedule(new TimerTask() {
                                    public void run() {
                                        startActivity(new Intent(getContext(), AttendanceActivity.class));
                                        Activity thisActivity = getActivity();
                                        if (thisActivity != null) {
                                            startActivity(new Intent(thisActivity, AttendanceActivity.class)); // if needed
                                            thisActivity.finish();
                                        }
                                    }
                                }, 3000);
                            } else {
                                validationLottieAnim.setVisibility(View.INVISIBLE);
                                if (Objects.requireNonNull(Objects.requireNonNull(task.getException()).getMessage()).length() >= 60) {
                                    Toast.makeText(getContext(), "Error !" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                } else {
                                    Snackbar snackbar = Snackbar.make(view, "Error! " + task.getException().getMessage(), Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                }
                            }
                        }
                    });
                }
            }
        });
        return root;
    }
}

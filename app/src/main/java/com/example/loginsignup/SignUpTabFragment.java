package com.example.loginsignup;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpTabFragment extends Fragment {

    FirebaseDatabase rootNode;
    DatabaseReference reference1;

    MainActivity mainActivity;
    FirebaseAuth fAuth;
    EditText getFname, getLname, getDesignation, getEmail, getMobile, getPass, repeatPass;
    Button signup;
    ProgressBar progressBar;
    float v = 0;


    View view;


    LoginTabFragment loginTabFragment = new LoginTabFragment();


//    void signUpAnimate() {
//        getMobile.setTranslationX(300);
//        getEmail.setTranslationX(300);
//        getPass.setTranslationX(300);
//        repeatPass.setTranslationX(300);
//        signup.setTranslationX(300);
//
//        getMobile.setAlpha(v);
//        getEmail.setAlpha(v);
//        getPass.setAlpha(v);
//        repeatPass.setAlpha(v);
//        signup.setAlpha(v);
//
//        getMobile.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
//        getEmail.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(450).start();
//        getPass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(600).start();
//        repeatPass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(750).start();
//        signup.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(900).start();
//
//    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment, container, false);
        fAuth = FirebaseAuth.getInstance();

        getFname = root.findViewById(R.id.fname);
        getLname = root.findViewById(R.id.lname);
        getDesignation = root.findViewById(R.id.designation);
        getMobile = root.findViewById(R.id.get_mobile);
        getEmail = root.findViewById(R.id.get_email);
        getPass = root.findViewById(R.id.get_pass);
        repeatPass = root.findViewById(R.id.repeat_pass);
        signup = root.findViewById(R.id.signup);
        progressBar = root.findViewById(R.id.progressbar);
        //signUpAnimate();



        //Animation

        loginTabFragment.animateX(getFname, 300, 1000, 300);
        loginTabFragment.animateX(getLname, 300, 1000, 600);
        loginTabFragment.animateX(getDesignation, 300, 1000, 900);
        loginTabFragment.animateX(getMobile, 300, 1000, 1200);
        loginTabFragment.animateX(getEmail, 300, 1000, 1500);
        loginTabFragment.animateX(getPass, 300, 1000, 1800);
        loginTabFragment.animateX(repeatPass, 300, 1000, 2100);

        rootNode = FirebaseDatabase.getInstance();
        reference1 = rootNode.getReference("Users");

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String isRequiredMsg = " is Required !";
                final String fname = getFname.getText().toString();
                final String lname = getLname.getText().toString();
                final String designation = getDesignation.getText().toString();
                final String mobile = getMobile.getText().toString();
                final String email = getEmail.getText().toString();
                final String pass = getPass.getText().toString();
                final String confPass = repeatPass.getText().toString();

                Pattern p = Pattern.compile("(0/91)?[7-9][0-9]{9}");
                Matcher m = p.matcher(mobile);

                if (fname.isEmpty()) {
                    getFname.setError("First name" + isRequiredMsg);
                } else if (lname.isEmpty()) {
                    getLname.setError("Last name" + isRequiredMsg);
                } else if (designation.isEmpty()) {
                    getDesignation.setError("Designation in company" + isRequiredMsg);
                } else if (mobile.isEmpty()) {
                    getMobile.setError("Mobile" + isRequiredMsg);
                } else if (mobile.length() != 10) {
                    getMobile.setError("Enter a valid 10 digit mobile number");
                } else if (!(m.find() && m.group().equals(mobile))) {
                    getMobile.setError("Enter Valid Mobile number type");

                } else if (email.isEmpty()) {
                    getEmail.setError("Email" + isRequiredMsg);
                } else if (pass.isEmpty()) {
                    getPass.setError("Password" + isRequiredMsg);
                } else if (pass.length() < 6) {
                    getPass.setError("Min 6 digit password" + isRequiredMsg);
                } else if (confPass.isEmpty()) {
                    repeatPass.setError("Password" + isRequiredMsg);
                } else if (!pass.equals(confPass)) {
                    repeatPass.setError("Both passwords should match");
                } else {
                    progressBar.setVisibility(View.VISIBLE);

                    fAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                Toast.makeText(getContext(), "User created", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getContext(), AttendanceActivity.class));

                                try {
                                    SignUpHelper signUpHelper = new SignUpHelper(fname, lname, designation, mobile, email);
                                    reference1.child(mobile).setValue(signUpHelper);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
//                                mainActivity.finish();

                                if (getActivity() != null) {

                                    getActivity().finish();


                                }

                            } else {
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(getContext(), "Error !" + task.getException().getMessage(), Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                }
            }
        });
        return root;
//        return super.onCreateView(inflater, container, savedInstanceState);
    }
}

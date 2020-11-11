package com.example.loginsignup.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.example.loginsignup.AttendanceActivity;
import com.example.loginsignup.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Timer;
import java.util.TimerTask;

public class LoginTabFragment extends Fragment {
    FirebaseAuth firebaseAuth;
    EditText txtEmail, txtPass;
    TextView forgetPass;
    Button loginBtn;
    LottieAnimationView loginSuccessAnim;
    Snackbar snackbar;

    void animateX(View element, long delay) {
        element.setTranslationX((float) 300);
        element.setAlpha(0);
        element.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(delay).start();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        txtEmail = root.findViewById(R.id.email);
        txtPass = root.findViewById(R.id.pass);
        forgetPass = root.findViewById(R.id.forget_pass);
        loginBtn = root.findViewById(R.id.login);
        loginSuccessAnim = root.findViewById(R.id.loginanim);

        final View[] view = new View[1];

        animateX(txtEmail, 300);
        animateX(txtPass, 500);
        animateX(forgetPass, 500);
        animateX(loginBtn, 700);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view[0] = v;
                final String isRequiredMsg = " is Required !";
                final String email = txtEmail.getText().toString().trim();
                final String pass = txtPass.getText().toString();
                if (email.isEmpty()) {
                    txtEmail.setError("Email" + isRequiredMsg);
                } else if (pass.isEmpty()) {
                    txtPass.setError("Password" + isRequiredMsg);
                } else {
                    firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                loginSuccessAnim.setVisibility(View.VISIBLE);
                                new Timer().schedule(new TimerTask() {
                                    public void run() {
                                        startActivity(new Intent(getContext(), AttendanceActivity.class));
                                        Activity thisActivity = getActivity();
                                        if (thisActivity != null) {
                                            startActivity(new Intent(thisActivity, AttendanceActivity.class)); // if needed
                                            thisActivity.finish();
                                        }
                                    }
                                }, 2000);
                            } else {
                                loginSuccessAnim.setVisibility(View.INVISIBLE);
                                if (task.getException().getMessage().length() >= 50) {
                                    Toast.makeText(getContext(), "Error !" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                } else {
                                    snackbar = Snackbar.make(view[0], "Error! " + task.getException().getMessage(), Snackbar.LENGTH_LONG);
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

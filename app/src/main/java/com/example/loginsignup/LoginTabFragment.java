package com.example.loginsignup;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginTabFragment extends Fragment {

    MainActivity mainActivity;
    FirebaseAuth firebaseAuth;
    EditText txtEmail, txtPass;
    TextView forgetPass;
    Button loginBtn;
    ProgressBar progressBar;
    Snackbar snackbar;
    void animateX(View element,float translationX,long duration,long delay){

        element.setTranslationX(translationX);
        element.setAlpha(0);
        element.animate().translationX(0).alpha(1).setDuration(duration).setStartDelay(delay).start();

    }

//    void loginAnimate(){
//
//
//        mEmail.setTranslationX(300);
//        mPass.setTranslationX(300);
//        forgetPass.setTranslationX(300);
//        login.setTranslationX(300);
//
//        mEmail.setAlpha(v);
//        mPass.setAlpha(v);
//        forgetPass.setAlpha(v);
//        login.setAlpha(v);
//
//        mEmail.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
//        mPass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
//        forgetPass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
//        login.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
//
//    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container, false);
//        return super.onCreateView(inflater, container, savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        txtEmail = root.findViewById(R.id.email);
        txtPass = root.findViewById(R.id.pass);
        forgetPass = root.findViewById(R.id.forget_pass);
        loginBtn = root.findViewById(R.id.login);
        progressBar=root.findViewById(R.id.login_progress);

        final View[] view = new View[1];

        animateX(txtEmail,300,1000,300);
        animateX(txtPass,300,1000,500);
        animateX(forgetPass,300,1000,500);
        animateX(loginBtn,300,1000,700);

//        loginAnimate();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                view[0] = v;
                        /*.setAction("Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Snackbar snackbar1 = Snackbar.make(view, "Message is restored!", Snackbar.LENGTH_SHORT);
                                snackbar1.show();
                            }
                        });*/
                final String isRequiredMsg = " is Required !";

                final String email = txtEmail.getText().toString().trim();
                final String pass = txtPass.getText().toString();


                if (email.isEmpty()) {
                    txtEmail.setError("Email" + isRequiredMsg);
                } else if (pass.isEmpty()) {
                    txtPass.setError("Password" + isRequiredMsg);
                }
                else {
                    firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                progressBar.setVisibility(View.VISIBLE);
                                Toast.makeText(getContext(), "Logged in successfully", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(getContext(), AttendanceActivity.class);
                                i.putExtra("Email",email);
                                i.putExtra("Pass",pass);
                                startActivity(i);


                                if (getActivity() !=null) {
                                    getActivity().finish();
                                }

                            }
                            else {
                                progressBar.setVisibility(View.INVISIBLE);

                                if(task.getException().getMessage().length()>=50){
                                    Toast.makeText(getContext(), "Error !" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                                else {
                                    snackbar = Snackbar.make(view[0], "Error! "+task.getException().getMessage(), Snackbar.LENGTH_LONG);

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

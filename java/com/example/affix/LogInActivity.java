package com.example.affix;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.affix.model.UserModel;
import com.example.affix.utils.FirebaseUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class LogInActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    protected EditText loginEmail, loginPassword;
    private TextView signupRedirectText;
    private Button loginButton;
    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        auth=FirebaseAuth.getInstance();
        loginEmail= findViewById(R.id.login_email);
        loginPassword= findViewById(R.id.login_password);
        loginButton= findViewById(R.id.login_button);
        signupRedirectText = findViewById(R.id.signUpRedirectText);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = loginEmail.getText().toString();
                String pass = loginPassword.getText().toString();


                //TJ EDITS
                getUsername();
                loginButton.setOnClickListener((v->{
                    setUsername();
                }));


                if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    if (!pass.isEmpty()) {
                        auth.signInWithEmailAndPassword(email, pass)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        Toast.makeText(LogInActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(LogInActivity.this, MainActivity.class));
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(LogInActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        loginPassword.setError("Empty fields are not allowed");
                    }
                } else if (email.isEmpty()) {
                    loginEmail.setError("Empty fields are not allowed");
                } else {
                    loginEmail.setError("Please enter correct email");
                }
            }
        });
        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this, SignUpActivity.class));
            }
        });
    }

    void setUsername(){
        setInProgress(true);
        String email = loginEmail.getText().toString();
        if(email.isEmpty()||email.length()<3){
            loginEmail.setError("Email should be at least 3 characters -TJ");
            return;
        }
        if(userModel!=null){
            userModel.setEmail(email);
        }
        else{
            userModel=new UserModel(email,Timestamp.now(),FirebaseUtil.currentUserId());
        }

        FirebaseUtil.currentUserDetails().set(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                setInProgress(false);
                if(task.isSuccessful()){
                    Intent intent = new Intent(LogInActivity.this,ContactsPageActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });
    }

    void getUsername(){
        setInProgress(true);
        FirebaseUtil.currentUserDetails().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                setInProgress(false);
                if(task.isSuccessful()){
                    userModel= task.getResult().toObject(UserModel.class);
                    if(userModel!=null){
                        loginEmail.setText(userModel.getEmail());
                    }

                }
            }
        });
    }

    void setInProgress(boolean inProgress){
        if(inProgress) {
            //progressBar.setVisibility(View.VISIBLE);
            loginButton.setVisibility(View.GONE);
        }
        else {
            //progressBar.setVisibility(View.VISIBLE);
            loginButton.setVisibility(View.VISIBLE);
        }

    }


}

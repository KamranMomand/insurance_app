package com.example.projectinsurance;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserLogin extends AppCompatActivity {

    EditText txtEmail,txtPass;
    Button btnLogin;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    ProgressDialog progressDialog;
//    ArrayList<User> userArrayList;

    @SuppressLint("MissingInflatedId")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        txtEmail=findViewById(R.id.txtEmail);
        txtPass=findViewById(R.id.txtPass);
        btnLogin = findViewById(R.id.btnLogin);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();

//                switch (view.getId()) {

//                    case R.id.nav_logOut:
//                        FirebaseAuth.getInstance().signOut();
//                        break;

//                }
            }
        });
    }
    public void userLogin(){
        String email = txtEmail.getText().toString();
        String password = txtPass.getText().toString();
        if (email.isEmpty()){
            txtEmail.setError("Email is required");
            txtEmail.requestFocus();
            return;
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            txtEmail.setError("Please enter valid Email");
            txtEmail.requestFocus();
            return;
        }else if (password.isEmpty()) {
            txtPass.setError("Password is required");
            txtPass.requestFocus();
            return;
        }else if (password.length()<6){
            txtPass.setError("Min password length is 6 characters");
            txtPass.requestFocus();
            return;
        }else {
            progressDialog.setTitle("Login....");
            progressDialog.setMessage("Please wait while Login......");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();

//                        Toast.makeText(UserLogin.this, "Login Successfully..."+user.getDisplayName(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(UserLogin.this, "Login Successfully...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(UserLogin.this,MainActivity.class));
                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(UserLogin.this, "invalid email and password", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UserLogin.this, "ERROR : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        user=mAuth.getCurrentUser();
        if(user != null){
//            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(UserLogin.this,MainActivity.class));
        }
    }
}
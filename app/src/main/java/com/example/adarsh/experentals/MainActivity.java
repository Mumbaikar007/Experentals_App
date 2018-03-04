package com.example.adarsh.experentals;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignin;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()!= null){
            //profile activity
            finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        }

        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewSignin = (TextView) findViewById(R.id.textViewSignin);

        buttonRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);

    }

    private void registerUser(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            // email is empty
            Toast.makeText(this,"Please enter Email Address", Toast.LENGTH_SHORT).show();
            return; //stop function from executing further
        }
        if(TextUtils.isEmpty(password)){
            // password is empty
            Toast.makeText(this,"Please enter Password", Toast.LENGTH_SHORT).show();
            return; //stop function from executing further
        }
        //if proper then register :
        progressDialog.setMessage("Creating your account...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //this method is executed when the reistration is done successfully
                if(task.isSuccessful()){
                    // actually open LoginActivity
                    //for now we use TOAST only


                        //profile activity
                        finish();
                        startActivity(new Intent(getApplicationContext(),ProfileActivity.class));



                    //Toast.makeText(MainActivity.this,"Account created successfully", Toast.LENGTH_SHORT).show();
                }
                   else{
                    Toast.makeText(MainActivity.this,"Could not register OR Already registered", Toast.LENGTH_SHORT).show();
                }
                progressDialog.hide();
            }
        });

    }

    @Override
    public void onClick(View view) {
        if(view == buttonRegister){
            registerUser();
        }
        if(view == textViewSignin){
            //open LoginActivity
            startActivity(new Intent(this,AuthActivity.class));
        }
    }
    public void onBackPressed() {
        // do something on back.
        //startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        //firebaseAuth.signOut();
        finish();
        //startActivity(new Intent(this,AuthActivity.class));
    }
}

package com.example.adarsh.experentals;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private TextView textViewUserEmail;
    private Button buttonLogout;
    private ImageView imageView1, imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth=FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
            //user is not logged in
            finish();
            startActivity(new Intent(this,AuthActivity.class));

        }

        FirebaseUser user  = firebaseAuth.getCurrentUser();

        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);

        textViewUserEmail.setText("Welcome\n"+user.getEmail());

        buttonLogout = (Button) findViewById(R.id.buttonLogout);

        buttonLogout.setOnClickListener(this);

        imageView1 = (ImageView) findViewById(R.id.imageView3);

        imageView1.setOnClickListener(this);

        imageView2 = (ImageView) findViewById(R.id.imageView4);

        imageView2.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
            if(view == buttonLogout){
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(this,AuthActivity.class));
            }
            else if(view == imageView1)
            {
                finish();
                startActivity(new Intent(this,PlaceAdActivity.class));
            }
            else if ( view == imageView2 ){
                finish();
                startActivity(new Intent(this,Receiver.class));
            }
    }
}

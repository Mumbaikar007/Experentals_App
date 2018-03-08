package com.example.adarsh.experentals;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private Button buttonLogout,chats;
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

        //textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);

        getSupportActionBar().setTitle(user.getEmail());

        //textViewUserEmail.setText("Welcome\n"+user.getEmail());

        //buttonLogout = (Button) findViewById(R.id.buttonLogout);

        chats=(Button) findViewById(R.id.button3);

        chats.setOnClickListener(this);

        //buttonLogout.setOnClickListener(this);


        imageView1 = (ImageView) findViewById(R.id.imageView3);

        imageView1.setOnClickListener(this);

        imageView2 = (ImageView) findViewById(R.id.imageView4);

        imageView2.setOnClickListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_name:
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(this,AuthActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
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
            else if(view == chats){
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.example.adarsh.uchat");
                if (launchIntent != null) {
                    startActivity(launchIntent);//null pointer check in case package name was not found
                }
            }
    }

    public void onBackPressed() {
        // do something on back.
        //startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        firebaseAuth.signOut();
        finish();
        //startActivity(new Intent(this,AuthActivity.class));
    }

}

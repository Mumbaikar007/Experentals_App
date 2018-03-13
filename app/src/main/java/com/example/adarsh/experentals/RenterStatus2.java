package com.example.adarsh.experentals;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RenterStatus2 extends AppCompatActivity  {

    Spinner spinnerCategory, spinnerLocation;
    Button buttonQuery;
    ListView listViewItems, listViewItemsDone;

    List <String> itemNamesYet, itemNamesDone;
    ArrayAdapter<String> arrayAdapter;
    private ArrayList<String>arrayListUid;
    ArrayList<Advert> advertArrayList;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renter_status2);

        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerLocation = findViewById(R.id.spinnerLocations);
        arrayListUid= new ArrayList<>();
        buttonQuery = findViewById(R.id.buttonQuery);
        listViewItems = findViewById(R.id.listViewYet);
        listViewItemsDone = findViewById(R.id.listViewDone);
        advertArrayList = new ArrayList<>();
        itemNamesYet = new ArrayList<String>();
        itemNamesDone = new ArrayList<String>();

        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            //user is not logged in
            finish();
            startActivity(new Intent(this,AuthActivity.class));

        }

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Advert");
        firebaseUser=firebaseAuth.getCurrentUser();


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();
                itemNamesYet.clear();
                itemNamesDone.clear();
                String[] from = { "flag","txt","cur" };
                int[] to = { R.id.flag,R.id.txt,R.id.cur};

                for ( DataSnapshot ds: dataSnapshot.getChildren() ){

                    Advert advert = ds.getValue(Advert.class);

                    if ( advert.bidder.equals("NULL") && advert.renterId.equals(firebaseAuth.getCurrentUser().getUid())){
                        itemNamesYet.add(advert.itemName);
                    }

                    else if (advert.renterId.equals(firebaseAuth.getCurrentUser().getUid() ) ){
                        itemNamesDone.add(advert.itemName);
                        arrayListUid.add(ds.getKey());
                    }

                }

                arrayAdapter = new ArrayAdapter<String>(RenterStatus2.this, android.R.layout.simple_list_item_1, itemNamesYet);
                listViewItems.setAdapter(arrayAdapter);

                arrayAdapter = new ArrayAdapter<String>(RenterStatus2.this, android.R.layout.simple_list_item_1, itemNamesDone);
                listViewItemsDone.setAdapter(arrayAdapter);

                listViewItemsDone.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                    public boolean onItemLongClick(AdapterView<?> arg0, View v, int index, long arg3) {
                        alertMessage(index);
                        return false;
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void alertMessage(final int index) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        // Yes button clicked
                        Toast.makeText(RenterStatus2.this, "Bid Accepted ... ", Toast.LENGTH_LONG).show();
                        databaseReference.child(arrayListUid.get(index)).removeValue();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        // No button clicked
                        // do nothing
                        Toast.makeText(RenterStatus2.this, "Bid Not Accepted ... ", Toast.LENGTH_LONG).show();

                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Confirm Delete ... ")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();


    }



    @Override
    public void onBackPressed() {
        // do something on back.

        startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        finish();
    }

}
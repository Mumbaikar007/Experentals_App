package com.example.adarsh.experentals;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class laCart extends AppCompatActivity {


    Spinner spinnerCategory, spinnerLocation;
    Button buttonQuery;
    ListView listViewItems, listViewItemsDone;

    List <String> itemNamesYet, itemNamesDone;
    ArrayAdapter<String> arrayAdapter;
    private ArrayList<String>arrayListUid, arrayListUid2;
    ArrayList<Advert> advertArrayList;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_la_cart);

        arrayListUid = new ArrayList<>();
        arrayListUid2 = new ArrayList<>();
        buttonQuery = findViewById(R.id.buttonQuery);
        listViewItems = findViewById(R.id.listViewNotConfirmed);
        listViewItemsDone = findViewById(R.id.listViewConfirmed);
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

                    if ( advert.bidConfirmed.equals("NO") && advert.bidder.equals(firebaseAuth.getCurrentUser().getUid())){
                        itemNamesYet.add(advert.itemName);
                        arrayListUid2.add(ds.getKey());
                    }

                    else if (advert.bidder.equals(firebaseAuth.getCurrentUser().getUid() ) ){
                        itemNamesDone.add(advert.itemName);
                        arrayListUid.add(ds.getKey());
                    }

                }

                arrayAdapter = new ArrayAdapter<String>(laCart.this, android.R.layout.simple_list_item_1, itemNamesYet);
                listViewItems.setAdapter(arrayAdapter);

                arrayAdapter = new ArrayAdapter<String>(laCart.this, android.R.layout.simple_list_item_1, itemNamesDone);
                listViewItemsDone.setAdapter(arrayAdapter);

                listViewItemsDone.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                    public boolean onItemLongClick(AdapterView<?> arg0, View v, int index, long arg3) {
                        alertMessage(index );
                        return false;
                    }
                });

                listViewItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                    public boolean onItemLongClick(AdapterView<?> arg0, View v, int index, long arg3) {
                        alertMessage2(index);
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
                        Toast.makeText(laCart.this, "Bid Accepted ... ", Toast.LENGTH_LONG).show();
                        databaseReference.child( arrayListUid.get(index) ).removeValue();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        // No button clicked
                        // do nothing
                        Toast.makeText(laCart.this, "Bid Not Accepted ... ", Toast.LENGTH_LONG).show();

                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Confirm Delete ... ")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();


    }


    public void alertMessage2(final int index) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        // Yes button clicked
                        Toast.makeText(laCart.this, "Bid Accepted ... ", Toast.LENGTH_LONG).show();
                        databaseReference.child( arrayListUid2.get(index) ).removeValue();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        // No button clicked
                        // do nothing
                        Toast.makeText(laCart.this, "Bid Not Accepted ... ", Toast.LENGTH_LONG).show();

                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Confirm Delete ... ")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();


    }


}

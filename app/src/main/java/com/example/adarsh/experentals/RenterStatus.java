package com.example.adarsh.experentals;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RenterStatus extends AppCompatActivity {

    private TextView textViewStatus;
    private ListView listViewYet, listViewDone;
    private List<String> arrayListYet, arrayListDone;
    private ArrayList<String> arrayListUid;
    private ArrayAdapter<String> arrayAdapterYet, arrayAdapterDone;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser firebaseUser;
    //String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renter_status);


        //textViewStatus = (TextView) findViewById(R.id.textView_Status);


        listViewYet = findViewById(R.id.listViewYet);
        listViewDone = findViewById(R.id.listViewDone);
        //arrayList_food_status = new ArrayList<>();
        arrayListUid = new ArrayList<>();
        arrayListYet = new ArrayList<String>();
        arrayListDone = new ArrayList<String>();

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        firebaseUser=firebaseAuth.getCurrentUser();

        if(firebaseUser== null) {
            finish();
            startActivity(new Intent(this, AuthActivity.class) );
        }


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                arrayListDone.clear();
                arrayListYet.clear();

                for ( DataSnapshot ds: dataSnapshot.getChildren()){

                    Advert advert = ds.getValue(Advert.class);

                    if ( advert.bidder == "NULL"){
                        arrayListYet.add(advert.itemName);
                    }
                    else{
                        arrayListDone.add(advert.itemName);
                        arrayListUid.add(dataSnapshot.getKey());
                    }

                }

                arrayAdapterDone = new ArrayAdapter<String>(RenterStatus.this, android.R.layout.simple_list_item_1,arrayListDone);
                arrayAdapterYet  = new ArrayAdapter<String>(RenterStatus.this, android.R.layout.simple_list_item_1,arrayListYet);

                listViewYet.setAdapter(arrayAdapterYet);
                listViewDone.setAdapter(arrayAdapterDone);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /*
        listViewDone.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View v, int index, long arg3) {
                alertMessage(index);
                return false;
            }
        });
        */

    }

    public void alertMessage(final int index) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        // Yes button clicked
                        Toast.makeText(RenterStatus.this, "Bid Accepted ... ", Toast.LENGTH_LONG).show();
                        databaseReference.child(arrayListUid.get(index)).removeValue();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        // No button clicked
                        // do nothing
                        Toast.makeText(RenterStatus.this, "Bid Not Accepted ... ", Toast.LENGTH_LONG).show();

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

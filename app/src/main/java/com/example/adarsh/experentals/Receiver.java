package com.example.adarsh.experentals;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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
import java.util.List;

public class Receiver extends AppCompatActivity  {

    Spinner spinnerCategory, spinnerLocation;
    Button buttonQuery;
    ListView listViewItems;

    List <String> itemNames;
    ArrayAdapter<String> arrayAdapter;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver);

        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerLocation = findViewById(R.id.spinnerLocations);

        buttonQuery = findViewById(R.id.buttonQuery);
        listViewItems = findViewById(R.id.listViewItems);

        itemNames = new ArrayList<String>();


        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            //user is not logged in
            finish();
            startActivity(new Intent(this,AuthActivity.class));

        }

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        firebaseUser=firebaseAuth.getCurrentUser();


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                itemNames.clear();
                for ( DataSnapshot ds: dataSnapshot.getChildren() ){
                    Advert advert = ds.getValue(Advert.class);
                    itemNames.add(advert.itemName);
                }

                arrayAdapter = new ArrayAdapter<String>(Receiver.this, android.R.layout.simple_list_item_1, itemNames);
                listViewItems.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }




    void singleQuery(String string, int type){

        final Query query;
        if ( type == 0 ){
            query = databaseReference;
        }
        else if ( type == 1) {
            query = databaseReference.orderByChild("location").equalTo(string);
        }
        else {
            query = databaseReference.orderByChild("category").equalTo(string);
        }

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                itemNames.clear();
                for ( DataSnapshot ds: dataSnapshot.getChildren() ){
                    Advert advert = ds.getValue(Advert.class);
                    itemNames.add(advert.itemName);
                }

                arrayAdapter = new ArrayAdapter<String>(Receiver.this, android.R.layout.simple_list_item_1, itemNames);
                listViewItems.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    void locationFoodTypeQuery(String location, final String category){

        final Query querylocationfoodtype = databaseReference.orderByChild("location").equalTo(location);


        querylocationfoodtype.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(getApplicationContext(), "" + category + category.length(), Toast.LENGTH_LONG).show();
                itemNames.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    Advert advert = ds.getValue(Advert.class);

                    if ( advert.category.equals(category) ) {
                        itemNames.add(advert.itemName);

                        Toast.makeText(getApplicationContext(), "locationFood" + advert.itemName, Toast.LENGTH_LONG).show();

                        //arrayListUid.add(ds.getKey());
                    }

                }


                arrayAdapter =new ArrayAdapter<String>(Receiver.this,android.R.layout.simple_list_item_1, itemNames);
                listViewItems.setAdapter(arrayAdapter);

                /*
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        String info_uid=  arrayListUid.get(i) ;
                        //Toast.makeText(Receiver_retrive_information.this,info_uid,Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Receiver_retrive_information.this,Receiver_click_activity.class).putExtra("uid",info_uid));

                    }
                });
                */

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public void QueryItems ( View view ){
        String location = spinnerLocation.getSelectedItem().toString();
        String category = spinnerCategory.getSelectedItem().toString();

        //arrayListUid.clear();
        itemNames.clear();

        if (location != "All Locations" && category == "All Categories") {
            singleQuery( location, 1);
        }

        else if (location != "All Locations" && category != "All Categories"){
            locationFoodTypeQuery( location, category);
        }

        else if (location == "All Locations" && category != "All Categories"){
            singleQuery( category , 2);
        }

        else {
            singleQuery( "Get_All", 0);
        }
    }


}
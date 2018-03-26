package com.example.adarsh.experentals;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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

public class Receiver extends AppCompatActivity  {

    Spinner spinnerCategory, spinnerLocation;
    Button buttonQuery;
    ListView listViewItems;

    List <String> itemNames;
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
        setContentView(R.layout.activity_receiver);

        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerLocation = findViewById(R.id.spinnerLocations);
        arrayListUid= new ArrayList<>();
        buttonQuery = findViewById(R.id.buttonQuery);
        listViewItems = findViewById(R.id.listViewItems);
        advertArrayList = new ArrayList<>();
        itemNames = new ArrayList<String>();


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
                itemNames.clear();
                String[] from = { "flag","txt","cur" };
                int[] to = { R.id.flag,R.id.txt,R.id.cur};


                for ( DataSnapshot ds: dataSnapshot.getChildren() ){

                    Advert advert = ds.getValue(Advert.class);
                    itemNames.add(advert.itemName);
                    arrayListUid.add(ds.getKey());
                    //Toast.makeText(getApplicationContext(), "locationFood" + ds.getKey(), Toast.LENGTH_LONG).show();
                    advertArrayList.add(advert);

                    byte[] decode_image= Base64.decode(advert.image,Base64.DEFAULT);
                    Bitmap bitmap_image= BitmapFactory.decodeByteArray(decode_image,0,decode_image.length);

                    //food_image.setImageBitmap(bitmap_image);

                    Drawable d = new BitmapDrawable(getResources(), bitmap_image);

                    HashMap<String, String> hm = new HashMap<String,String>();
                    hm.put("txt", "Name : " + advert.itemName);
                    hm.put("cur","Monthly Rent : " + advert.monthlyRent);
                    hm.put("flag", bitmap_image.toString());
                    aList.add(hm);

                }

                SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.listing_for_receiver, from, to);
                ExtendedSimpleAdapter  adapter1 = new ExtendedSimpleAdapter( getBaseContext(), aList, R.layout.listing_for_receiver, from, to);

                arrayAdapter = new ArrayAdapter<String>(Receiver.this, android.R.layout.simple_list_item_1, itemNames);
                listViewItems.setAdapter(adapter1);



                listViewItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        Advert advert = advertArrayList.get(i);
                        String info_uid=  arrayListUid.get(i) ;
                        //Toast.makeText(Receiver.this,info_uid,Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Receiver.this,ReceiverItemSelect.class).putExtra("advertObj",advert));

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //private FirebaseAuth firebaseAuth;

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


                List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();
                String[] from = { "flag","txt","cur" };
                int[] to = { R.id.flag,R.id.txt,R.id.cur};

                advertArrayList.clear();


                for ( DataSnapshot ds: dataSnapshot.getChildren() ){
                    Advert advert = ds.getValue(Advert.class);
                    advertArrayList.add(advert);
                    itemNames.add(advert.itemName);
                    arrayListUid.add(ds.getKey());
                    byte[] decode_image= Base64.decode(advert.image,Base64.DEFAULT);
                    Bitmap bitmap_image= BitmapFactory.decodeByteArray(decode_image,0,decode_image.length);

                    //food_image.setImageBitmap(bitmap_image);

                    Drawable d = new BitmapDrawable(getResources(), bitmap_image);

                    HashMap<String, String> hm = new HashMap<String,String>();
                    hm.put("txt", "Name : " + advert.itemName);
                    hm.put("cur","Monthly Rent : " + advert.monthlyRent);
                    hm.put("flag", bitmap_image.toString());
                    aList.add(hm);

                   // Toast.makeText(getApplicationContext(), "locationFood" + ds.getKey(), Toast.LENGTH_LONG).show();
                }

                ExtendedSimpleAdapter  adapter1 = new ExtendedSimpleAdapter( getBaseContext(), aList, R.layout.listing_for_receiver, from, to);

                arrayAdapter = new ArrayAdapter<String>(Receiver.this, android.R.layout.simple_list_item_1, itemNames);
                listViewItems.setAdapter(adapter1);

                listViewItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        Advert advert = advertArrayList.get(i);
                        //String info_uid=  arrayListUid.get(i) ;
                        //Toast.makeText(Receiver.this,info_uid,Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Receiver.this,ReceiverItemSelect.class).putExtra("advertObj",advert));

                    }
                });

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
                //Toast.makeText(getApplicationContext(), "" + category + category.length(), Toast.LENGTH_LONG).show();
                itemNames.clear();


                List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();
                String[] from = { "flag","txt","cur" };
                int[] to = { R.id.flag,R.id.txt,R.id.cur};

                advertArrayList.clear();



                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    Advert advert = ds.getValue(Advert.class);

                    if ( advert.category.equals(category) ) {

                        itemNames.add(advert.itemName);

                        advertArrayList.add(advert);

                        byte[] decode_image= Base64.decode(advert.image,Base64.DEFAULT);
                        Bitmap bitmap_image= BitmapFactory.decodeByteArray(decode_image,0,decode_image.length);

                        //food_image.setImageBitmap(bitmap_image);

                        Drawable d = new BitmapDrawable(getResources(), bitmap_image);

                        //Toast.makeText(getApplicationContext(), "locationFood" + ds.getKey(), Toast.LENGTH_LONG).show();

                        HashMap<String, String> hm = new HashMap<String,String>();
                        hm.put("txt", "Name : " + advert.itemName);
                        hm.put("cur","Monthly Rent : " + advert.monthlyRent);
                        hm.put("flag", bitmap_image.toString());
                        aList.add(hm);

                        //arrayListUid.add(ds.getKey());
                    }

                }


                SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.listing_for_receiver, from, to);
                ExtendedSimpleAdapter  adapter1 = new ExtendedSimpleAdapter( getBaseContext(), aList, R.layout.listing_for_receiver, from, to);

                arrayAdapter = new ArrayAdapter<String>(Receiver.this, android.R.layout.simple_list_item_1, itemNames);
                listViewItems.setAdapter(adapter1);



                listViewItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        Advert advert = advertArrayList.get(i);
                        //String info_uid=  arrayListUid.get(i) ;
                        //Toast.makeText(Receiver.this,info_uid,Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Receiver.this,ReceiverItemSelect.class).putExtra("advertObj",advert));

                    }
                });


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public void QueryItems ( View view ){
        String location = spinnerLocation.getSelectedItem().toString();
        String category = spinnerCategory.getSelectedItem().toString();

        arrayListUid.clear();
        itemNames.clear();
        advertArrayList.clear();

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
    @Override
    public void onBackPressed() {
        // do something on back.

        startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        finish();
    }

}
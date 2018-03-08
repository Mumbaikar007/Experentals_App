package com.example.adarsh.experentals;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReceiverItemSelect extends AppCompatActivity {


    // TextView donor_uid;
    FirebaseAuth firebaseAuth;
    ArrayList<String> arrayList_donor_info;
    ArrayAdapter<String> adapter;
    ListView listView_donor_info;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    String longitude3;
    String latitude3;
    String uid;
    String food_pic;
    String address_for_location;
    // FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver_item_select);

        arrayList_donor_info=new ArrayList<>();
        listView_donor_info=(ListView)findViewById(R.id.listview_donor_info) ;


        Intent x=getIntent();
        uid= x.getStringExtra("uid");
        Toast.makeText(getApplicationContext(), "locationFood" + uid, Toast.LENGTH_LONG).show();
        //   String name=x.getStringExtra("name");
        // donor_uid.setText(uid);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference().child(uid);

        // Toast.makeText(this,uid,Toast.LENGTH_SHORT).show();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {

                Advert advert = ds.getValue(Advert.class);

                /*
                arrayList_donor_info.add("Name: "+ advert.itemName);
                //arrayList_donor_info.add("Address: "+ advert.address);
                arrayList_donor_info.add("Category: " + advert.category);
                arrayList_donor_info.add("Description: " + advert.description);
                arrayList_donor_info.add("Monthly Rent: " + advert.monthlyRent);
                arrayList_donor_info.add("Location: " +advert.location);




                address_for_location= advert.address;

                food_pic= advert.image;
                latitude3= advert.latitude;
                longitude3= advert.longitude;


                arrayList_donor_info.add("check food pic");

                adapter=new ArrayAdapter<String>(ReceiverItemSelect.this,android.R.layout.simple_list_item_1,arrayList_donor_info);
                listView_donor_info.setAdapter(adapter);
                */

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /*
        listView_donor_info.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(i==9)
                {
                    Toast.makeText(ReceiverItemSelect.this,"check",Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(ReceiverItemSelect.this,Receiver_food_image.class).putExtra("image1",food_pic));
                }



            }
        });
        */

    }

    public  void  getLocation(View v) {

        Toast.makeText(this,latitude3+" done "+longitude3,Toast.LENGTH_SHORT).show();
        //startActivity(new Intent(this,MapsActivity.class).putExtra("longitude",longitude3).putExtra("latitude",latitude3).putExtra("address",address_for_location));

        //  startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("geo:" + longitude3 + "," + latitude3)));

    }
}

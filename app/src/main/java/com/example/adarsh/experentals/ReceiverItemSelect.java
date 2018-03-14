package com.example.adarsh.experentals;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
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

    TextView textViewName, textViewAmount, textViewDescription;
    TextView textViewBid;

    EditText editTextBidded;
    Button buttonPlaceBid;

    ImageView imageView;
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
        //advertArrayList = new ArrayList<>();

        final Advert advert = getIntent().getExtras().getParcelable("advertObj");

        textViewName = findViewById(R.id.textViewName);
        textViewAmount = findViewById(R.id.textViewAmount);
        textViewDescription = findViewById(R.id.textViewDescription);
        imageView = findViewById( R.id.imageViewImage);
        textViewBid = findViewById(R.id.textViewBid);
        editTextBidded = findViewById(R.id.editTextBidded);
        buttonPlaceBid = findViewById(R.id.buttonPlaceBid);



        //Intent x=getIntent();
        //uid= x.getStringExtra("uid");
        //Toast.makeText(getApplicationContext(), "locationFood" + uid, Toast.LENGTH_LONG).show();
        //String name=x.getStringExtra("name");
        //donor_uid.setText(uid);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference("Advert").child(advert.id);

        byte[] decode_image= Base64.decode(advert.image,Base64.DEFAULT);
        Bitmap bitmap_image= BitmapFactory.decodeByteArray(decode_image,0,decode_image.length);


        imageView.setImageBitmap(bitmap_image);
        textViewDescription.setText("Description: " + advert.description);
        textViewAmount.setText("Amount: " + advert.monthlyRent);
        textViewName.setText("Name: " + advert.itemName);
        textViewBid.setText("Current Maximum Bid: " + advert.bid);

        buttonPlaceBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int userBid = Integer.parseInt(editTextBidded.getText().toString());
                int previousBid = Integer.parseInt(advert.bid);
                if ( userBid > previousBid ){

                    advert.bid = Integer.toString(userBid);
                    advert.bidder = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    databaseReference.setValue(advert);
                    textViewBid.setText("Current Maximum Bid: " + advert.bid);
                    Toast.makeText(ReceiverItemSelect.this, "Bid Placed !", Toast.LENGTH_LONG).show();

                }
            }
        });

    }

    public  void  getLocation(View v) {

        Toast.makeText(this,latitude3+" done "+longitude3,Toast.LENGTH_SHORT).show();
        //startActivity(new Intent(this,MapsActivity.class).putExtra("longitude",longitude3).putExtra("latitude",latitude3).putExtra("address",address_for_location));

        //  startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("geo:" + longitude3 + "," + latitude3)));

    }
}

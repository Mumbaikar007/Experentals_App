package com.example.adarsh.experentals;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class PlaceAdActivity extends AppCompatActivity implements View.OnClickListener, LocationListener {

    private static final int PICK_IMAGE_REQUEST = 234;

    private Button buttonupload, upload, placeAd, buttonLongLat;
    LocationManager locationManager;
    private ImageView imageView;

    public String Latitudeee, Longitudee, address;
    private Uri filePath;
    private StorageReference storageReference;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabaseReference = database.getReference();

    private EditText itemName, category, description, monthlyRent;
    private Spinner spinnerLocation;
    private String imageString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_ad);



        storageReference = FirebaseStorage.getInstance().getReference();

        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            //user is not logged in
            finish();
            startActivity(new Intent(this,AuthActivity.class));

        }

        buttonupload = (Button) findViewById(R.id.buttonupload);
        upload = (Button) findViewById(R.id.upload2);
        imageView = (ImageView) findViewById(R.id.imageView5);
        placeAd = (Button) findViewById(R.id.button2);

        itemName = (EditText) findViewById(R.id.editText);
        category = (EditText) findViewById(R.id.editText2);
        description = (EditText) findViewById(R.id.editText4);
        monthlyRent = (EditText) findViewById(R.id.editText3);
        spinnerLocation =  findViewById(R.id.spinnerLocations);
        buttonLongLat = findViewById( R.id.buttonLongLat);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }



        buttonupload.setOnClickListener(this);
        upload.setOnClickListener(this);
        placeAd.setOnClickListener(this);
        buttonLongLat.setOnClickListener(this);

    }
    private FirebaseAuth firebaseAuth;

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

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select an Image"), PICK_IMAGE_REQUEST);
    }

    private void uploadFile() {
        if (filePath != null) {

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading....");
            progressDialog.show();
            StorageReference riversRef = storageReference.child("images/profile.jpg");

            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get a URL to the uploaded content
                            //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "File uploaded successfully", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage(((int) progress) + "% Uploaded...");
                        }
                    });
        } else {
            //error TOAST
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                // lz compression
                byte[] b = byteArrayOutputStream.toByteArray();

                imageString = Base64.encodeToString(b, Base64.DEFAULT);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == 101) {
            Bitmap image1 = data.getParcelableExtra("image_food");
            imageView.setImageBitmap(image1);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            image1.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            // lz compression
            byte[] b = byteArrayOutputStream.toByteArray();

            imageString = Base64.encodeToString(b, Base64.DEFAULT);
            //        Toast.makeText(Donor_user_info2.this,image_string,Toast.LENGTH_SHORT).show();

            // Toast.makeText(Donor_user_info2.this,data.getStringExtra("image_food"),Toast.LENGTH_SHORT).show();


        }

    }

    public  void takepic(View view)
    {

        startActivityForResult(new Intent(PlaceAdActivity.this,ItemImage.class),101);

    }


    @Override
    public void onClick(View view) {

        if (view == buttonupload) {
            showFileChooser();
        }

        if (view==upload){
            takepic(view);
        }

        if ( view == buttonLongLat ){
            getLocation();
        }

        if (view == placeAd) {
          //  try {
                Advert advert = new Advert(itemName.getText().toString(),
                        category.getText().toString(), description.getText().toString(),
                        monthlyRent.getText().toString(), spinnerLocation.getSelectedItem().toString(),
                        imageString, FirebaseAuth.getInstance().getCurrentUser().getUid(), Latitudeee,
                        Longitudee, address);
                //uploadFile();
                mDatabaseReference.child("Advert").push().setValue(advert);
                Toast.makeText(getApplicationContext(), "Advertisement placed Successfully ", Toast.LENGTH_LONG).show();

            // } catch (Exception e) {
                //e.printStackTrace();            }
        //    }
        }
    }

    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        //locationText.setText("Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude());
        Latitudeee = Double.toString(location.getLatitude());
        Latitudeee = Double.toString(location.getLongitude());

        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            address = addresses.get(0).getAddressLine(0)+", "+
                    addresses.get(0).getAddressLine(1)+", "+addresses.get(0).getAddressLine(2);
            Latitudeee = Double.toString(location.getLatitude());
            Latitudeee = Double.toString(location.getLongitude());


        }catch(Exception e)
        {

        }

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(PlaceAdActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onBackPressed() {
        // do something on back.
        startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        finish();
    }


}

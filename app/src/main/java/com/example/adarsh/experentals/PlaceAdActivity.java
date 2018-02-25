package com.example.adarsh.experentals;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
<<<<<<< HEAD
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
=======
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
>>>>>>> cc8ee297bac9c1fad1fb5e2c72f077e83a49c264
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
<<<<<<< HEAD
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
=======
>>>>>>> cc8ee297bac9c1fad1fb5e2c72f077e83a49c264
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class PlaceAdActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int PICK_IMAGE_REQUEST = 234;
<<<<<<< HEAD
    private Button buttonupload, upload, placeAd;
=======
    private Button buttonupload,upload;
>>>>>>> cc8ee297bac9c1fad1fb5e2c72f077e83a49c264
    private ImageView imageView;

    private Uri filePath;
    private StorageReference storageReference;
<<<<<<< HEAD
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabaseReference = database.getReference();

    private EditText itemName, category, description, monthlyRent, location;
=======
>>>>>>> cc8ee297bac9c1fad1fb5e2c72f077e83a49c264

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_ad);

        storageReference = FirebaseStorage.getInstance().getReference();

<<<<<<< HEAD

        buttonupload = (Button) findViewById(R.id.buttonupload);
        upload = (Button) findViewById(R.id.upload2);
        imageView = (ImageView) findViewById(R.id.imageView5);
        placeAd = (Button) findViewById(R.id.button2);

        itemName = (EditText) findViewById(R.id.editText);
        category = (EditText) findViewById(R.id.editText2);
        description = (EditText) findViewById(R.id.editText4);
        monthlyRent = (EditText) findViewById(R.id.editText3);
        location = (EditText) findViewById(R.id.editText5);

        buttonupload.setOnClickListener(this);
        upload.setOnClickListener(this);
        placeAd.setOnClickListener(this);

    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select an Image"), PICK_IMAGE_REQUEST);
    }

    private void uploadFile() {
        if (filePath != null) {
=======
        buttonupload = (Button) findViewById(R.id.buttonupload);
        upload = (Button) findViewById(R.id.upload2);
        imageView = (ImageView) findViewById(R.id.imageView5);

        buttonupload.setOnClickListener(this);
        upload.setOnClickListener(this);

    }

    private void showFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select an Image"),PICK_IMAGE_REQUEST);
    }

    private void uploadFile() {
        if(filePath != null){
>>>>>>> cc8ee297bac9c1fad1fb5e2c72f077e83a49c264

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading....");
            progressDialog.show();
<<<<<<< HEAD
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
=======
        StorageReference riversRef = storageReference.child("images/profile.jpg");

        riversRef.putFile(filePath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"File uploaded successfully",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),exception.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                        progressDialog.setMessage(((int) progress)+ "% Uploaded...");
                    }
                });
    }
    else{
>>>>>>> cc8ee297bac9c1fad1fb5e2c72f077e83a49c264
            //error TOAST
        }
    }

<<<<<<< HEAD

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
=======
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null &&  data.getData()!=null){
            filePath = data.getData();
            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
>>>>>>> cc8ee297bac9c1fad1fb5e2c72f077e83a49c264
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View view) {
<<<<<<< HEAD
        if (view == buttonupload) {
            showFileChooser();
        }
        if (view == upload) {
            uploadFile();
        }
        if (view == placeAd) {
          //  try {
                Advert advert = new Advert(itemName.getText().toString(), category.getText().toString(), description.getText().toString(), monthlyRent.getText().toString(), location.getText().toString());

                mDatabaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).push().setValue(advert);
            Toast.makeText(getApplicationContext(), "Advertisement placed Successfully ", Toast.LENGTH_LONG).show();

            // } catch (Exception e) {
                //e.printStackTrace();            }
        //    }
        }
    }
}
=======
        if(view == buttonupload){
            showFileChooser();
        }
        if(view == upload){
            uploadFile();
        }
    }
}
>>>>>>> cc8ee297bac9c1fad1fb5e2c72f077e83a49c264

package com.example.adarsh.experentals;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ItemImage extends AppCompatActivity {


    ImageView imageView_food_pic;

    //FirebaseAuth firebaseAuth;
    //FirebaseUser firebaseUser;
    //DatabaseReference databaseReference;
    //Donor_Info_storage donor_info_storage;

    Bitmap image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_image);

        imageView_food_pic=(ImageView)findViewById(R.id.imageView_food_pic);
    }

    public  void clicktopic(View view)
    {

        Intent in=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(in,10);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==10)
        {

            image= (Bitmap) data.getExtras().get("data");
            imageView_food_pic.setImageBitmap(image);


        }

    }

    public  void onSave(View v)
    {


        Intent in1=getIntent();

        in1.putExtra("image_food",image);

        setResult(Activity.RESULT_OK,in1);
        finish();

    }

}

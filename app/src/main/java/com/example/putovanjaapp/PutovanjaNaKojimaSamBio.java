package com.example.putovanjaapp;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PutovanjaNaKojimaSamBio extends AppCompatActivity {

    ImageView image;
    Button btnImage;
    Button btnSpremi;
    ActivityResultLauncher<Intent> rez;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_putovanja_na_kojima_sam_bio);


        btnImage=findViewById(R.id.btnSlika);
        btnSpremi=findViewById(R.id.spremi);
       image= findViewById(R.id.slika);
       rezult();

btnImage.setOnClickListener(view->pickImmage());

    }
    private  void pickImmage()
    {
        Intent intent =new Intent(MediaStore.ACTION_PICK_IMAGES);
       rez.launch(intent);
    }
    private  void rezult()
    {
        rez=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {
                Uri imgUrl= o.getData().getData();
                image.setImageURI(imgUrl);

            }
        });
    }
}
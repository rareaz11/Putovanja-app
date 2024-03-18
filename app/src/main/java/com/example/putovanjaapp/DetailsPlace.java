package com.example.putovanjaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;

public class DetailsPlace extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_place);

        TextView textView = findViewById(R.id.CityDetails);
        Intent intent= getIntent();
            int x= intent.getIntExtra("ID",0);




            final Handler handler = new Handler();
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    PutovanjaDataBase db = PutovanjaDataBase.getInstance(DetailsPlace.this);

                    Putovanja myId = db.putovanjaIDao().getByID(x);

                    handler.post(() -> onGetId(myId));
                }
            });
        }



    private void onGetId(Putovanja putovanja)
    {

        TextView city = findViewById(R.id.CityDetails);
        TextView datum= findViewById(R.id.datumPosjete);
        ImageView img = findViewById(R.id.pictureCity);
        TextView desc = findViewById(R.id.DescriptionPlace);
            if(putovanja.getByteSlika()==null)
            {

                city.setText(putovanja.getName());
                desc.setText(putovanja.getOpis());

                img.setImageResource(R.drawable.baseline_error_24);

                datum.setText("Ovdje jos niste bili....");

            }

            else
            {
        byte[] slika= Base64.getDecoder().decode(putovanja.getByteSlika());
        Bitmap bitmap= BitmapFactory.decodeByteArray(slika,0,slika.length);

        img.setImageBitmap(bitmap);
        datum.setText(putovanja.getVrijeme());
        city.setText(putovanja.getName());
        desc.setText(putovanja.getOpis());
            }
    }


           /*
           OVO MI JE ZA PROVJERIT KOJI JE ID U BAZI
           String xIn= String.valueOf(x);
            textView.setText(xIn);*/

     /*   try {

            final Handler handler = new Handler();
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    PutovanjaDataBase db = PutovanjaDataBase.getInstance(DetailsPlace.this);
                    Putovanja myId = db.putovanjaIDao().getByID(1);

                    handler.post(() -> onGetId(myId));
                }
            });
        }
        catch (Exception e)
        {
            textView.setText("GRESKA SLIKA NIJE PRONADENA!!: " + e);
        }
    }

    private void onGetId(Putovanja putovanja)
    {

        TextView city= findViewById(R.id.CityDetails);

        ImageView img= findViewById(R.id.pictureCity);
        TextView desc= findViewById(R.id.DescriptionPlace);

        city.setText(putovanja.getName());
        desc.setText(putovanja.getOpis());

    }*/

    public void BackToList(View v)
    {
        Intent intent= new Intent(this, AllTrips.class);
        startActivity(intent);

    }

}



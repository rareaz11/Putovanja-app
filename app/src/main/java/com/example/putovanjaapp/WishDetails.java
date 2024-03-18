package com.example.putovanjaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Base64;
import java.util.concurrent.Executors;

public class WishDetails extends AppCompatActivity
{
    int idIzmjena;
    Dialog dialog1;
    Button btnCancel;
    Button btnDelete1;
    EditText noviUnos;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_details);

        Intent intent= getIntent();
        idIzmjena= intent.getIntExtra("ID",0);




        final Handler handler = new Handler();
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                PutovanjaDataBase db = PutovanjaDataBase.getInstance(WishDetails.this);

                Putovanja myId = db.putovanjaIDao().getByID(idIzmjena);

                handler.post(() -> onGetId(myId));
            }
        });

        Button buttonBack= findViewById(R.id.button1111);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent1= new Intent(WishDetails.this,Wish.class);
                startActivity(intent1);

            }
        });

        //tipka otvara dialog nakon tog ovisno tipki biranje ili odustajnje
        Button buttonDel= findViewById(R.id.izmjena);
        dialog1=new Dialog(WishDetails.this);
        dialog1.setContentView(R.layout.citydialog);
        dialog1.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog1.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_bg_dialog));
        dialog1.setCancelable(false);
        noviUnos=dialog1.findViewById(R.id.Opet123);
        btnDelete1=dialog1.findViewById(R.id.deleteTrip123);
        btnCancel=dialog1.findViewById(R.id.dontDelete);



        buttonDel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog1.show();

//zatovri dialog
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        dialog1.dismiss();

                    }
                });
                //brisanje
                btnDelete1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        String recenica= noviUnos.getText().toString();

                        if(recenica.length()<3)
                        {
                            Toast.makeText(WishDetails.this,"Polje Prazno",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        final Handler handler = new Handler();
                        Executors.newSingleThreadExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                PutovanjaDataBase db = PutovanjaDataBase.getInstance(WishDetails.this);

                                db.putovanjaIDao().updateTrip(idIzmjena,recenica);


                            }
                        });

                        Toast.makeText(WishDetails.this,"Izmjena id: "+ idIzmjena,Toast.LENGTH_SHORT).show();
                        Intent i= new Intent(WishDetails.this,MainActivity.class);
                        startActivity(i);

                    }
                });


            }
        });
    }



    private void onGetId(Putovanja putovanja)
    {

        TextView city = findViewById(R.id.CityDetails1);
        TextView datum= findViewById(R.id.datumPosjete1);
        ImageView img = findViewById(R.id.pictureCity1);
        TextView desc = findViewById(R.id.DescriptionPlace1);
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
}
package com.example.putovanjaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void MenuShow(View view)
    {


    }
    public  void DodajPutovanje(View v)
    {

        Intent intent= new Intent(this, AddTrip.class);
        startActivity(intent);
    }

    public  void PrikaziSve(View v)
    {

        Intent intent= new Intent(this, AllTrips.class);
        startActivity(intent);
    }

    public void MyWishes(View v)

    {
        Intent intent= new Intent(MainActivity.this, Wish.class);
        startActivity(intent);
    }

    public  void DeleteTrip(View c)
    {
        Intent intent =new Intent(this, DeleteTrip.class);
        startActivity(intent);
    }
}
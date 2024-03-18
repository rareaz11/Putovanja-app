package com.example.putovanjaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;



import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.Executors;

public class AllTrips extends AppCompatActivity {




    Putovanja abca;
Bitmap bitmap1;
byte[] img;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_trips);



        final Handler handler = new Handler();
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                PutovanjaDataBase db = PutovanjaDataBase.getInstance(AllTrips.this);
                List<Putovanja> mylist = db.putovanjaIDao().getAlltrips();
                if(mylist.isEmpty())
                {

                    Intent i= new Intent(AllTrips.this, Prazno.class);
                    startActivity(i);

                }

                else {
                handler.post(() -> onGetMeal(mylist));}
            }
        });

        ImageView btnBack= findViewById(R.id.back11);

        btnBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i= new Intent(AllTrips.this,MainActivity.class);
                startActivity(i);

            }
        });
    }
    private void onGetMeal(List<Putovanja> abc)
    {
        ArrayList<Putovanja> arrayList=new ArrayList<>();


        for (Putovanja x: abc)
        {
           arrayList.add(x);
        }
        ListView list=findViewById(R.id.listTravels);

        ListAdapter listAdapter= new ListAdapter(this,R.layout.all_in_list,arrayList);
        list.setAdapter(listAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {


                        Intent intent = new Intent(AllTrips.this, DetailsPlace.class);
                       intent.putExtra("ID", arrayList.get(position).getId());
                        startActivity(intent);

            }
        });






       /* ArrayAdapter<String> adapter1= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,datum1);

        ListView lv= findViewById(R.id.listTravels);


        lv.setAdapter(adapter1);*/



       /* bitmap1=BitmapFactory.decodeByteArray(img,0,img.length);

        imageView.setImageBitmap(bitmap1);


    }







        /*
       List<String> imena= new ArrayList<>();


        for (Putovanja x: abc)
        {
         imena.add(x.getName()+"              " +x.getVrijeme());
        }

        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,imena);

        ListView lv= findViewById(R.id.listTravels);

        lv.setAdapter(adapter);
         ImageView imageView= findViewById(R.id.slike);
        List<String> imena= new ArrayList<>();
     ListView listView=findViewById(R.id.listUsers);
    for (Putovanja x: abc)
       {
         imena.add(x.getByteSlika());
      img= Base64.getDecoder().decode(x.getByteSlika());

       }

       /* bitmap1=BitmapFactory.decodeByteArray(img,0,img.length);

        imageView.setImageBitmap(bitmap1);*/


    }
}
package com.example.putovanjaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class Wish extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish);

        final Handler handler = new Handler();
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                PutovanjaDataBase db = PutovanjaDataBase.getInstance(Wish.this);
                List<Putovanja> mylist = db.putovanjaIDao().getMyComplitedTrips();

                if(mylist.isEmpty())
                {

                    Intent i= new Intent(Wish.this, Prazno.class);
                    startActivity(i);

                }

                else {
                    handler.post(() -> onGetMeal(mylist));}

            }
        });

       ImageView btn123= findViewById(R.id.back123);

        btn123.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i= new Intent(Wish.this,MainActivity.class);
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


                Intent intent = new Intent(Wish.this, WishDetails.class);
                intent.putExtra("ID", arrayList.get(position).getId());
                startActivity(intent);

            }
        });

    }
}
package com.example.putovanjaapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<Putovanja>
{
   private  Context mycontext;

   private int mResl;

   public  ListAdapter(@NonNull Context context, int res, @NonNull ArrayList<Putovanja>putovnjas)
   {
       super(context,res,putovnjas);
       this.mycontext=context;
       this.mResl=res;


   }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       LayoutInflater layoutInflater=LayoutInflater.from(mycontext);

       convertView=layoutInflater.inflate(mResl,parent,false);
        TextView city= convertView.findViewById(R.id.City);

        TextView description=convertView.findViewById(R.id.descriptionCity);

        city.setText(getItem(position).getName());
        description.setText(getItem(position).getOpis());


       return convertView;
    }
}

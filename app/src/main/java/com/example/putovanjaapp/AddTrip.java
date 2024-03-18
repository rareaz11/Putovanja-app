package com.example.putovanjaapp;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

public class AddTrip extends AppCompatActivity
{
    List<Putovanja> mojaLista;

    Bitmap bitmapAdd;
    String date;
    TextView tvPoruka;
    Button spinner;
    ActivityResultLauncher<Intent> rez;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
      spinner= findViewById(R.id.spinner);
        Button btnDodajSlikuKamera= findViewById(R.id.btnDodajSliku);
        Button btnDodajSliku2= findViewById(R.id.btnGalerija2);
      tvPoruka=findViewById(R.id.texPosjet);
         imageView=findViewById(R.id.slika);
        btnDodajSlikuKamera.setVisibility(View.INVISIBLE);
        imageView.setVisibility(View.INVISIBLE);
        btnDodajSliku2.setVisibility(View.INVISIBLE);
        tvPoruka.setVisibility(View.INVISIBLE);
        spinner.setVisibility(View.INVISIBLE);
        Button btnOdustani=findViewById(R.id.odustani);

        final Handler handler = new Handler();
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                PutovanjaDataBase db = PutovanjaDataBase.getInstance(AddTrip.this);
               mojaLista = db.putovanjaIDao().getAlltrips();

            }
        });

        btnOdustani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AddTrip.this, MainActivity.class);
                startActivity(intent);


            }
        });


        spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDatePicker<Long> materialDatePicker=MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select Date").setSelection(MaterialDatePicker.todayInUtcMilliseconds()).build();
                    materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                        @Override
                        public void onPositiveButtonClick(Long selection) {
                           date = new SimpleDateFormat("dd-MM-yyy", Locale.GERMAN)
                                    .format(new Date(selection));
                            tvPoruka.setText("Selcted: date: "+ date);
                        }
                    });
                    materialDatePicker.show(getSupportFragmentManager(), "tag");
            }
        });






        CheckBox ch= findViewById(R.id.check);

        rezult();
        btnDodajSliku2.setOnClickListener(view->pickImmage());

        btnDodajSlikuKamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,100);

            }
        });


        ch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    btnDodajSlikuKamera.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.VISIBLE);
                    btnDodajSliku2.setVisibility(View.VISIBLE);
                    tvPoruka.setVisibility(View.VISIBLE);
                    spinner.setVisibility(View.VISIBLE);
                }
                else
                {
                    btnDodajSlikuKamera.setVisibility(View.INVISIBLE);
                    imageView.setVisibility(View.INVISIBLE);
                    btnDodajSliku2.setVisibility(View.INVISIBLE);
                    tvPoruka.setVisibility(View.INVISIBLE);
                    spinner.setVisibility(View.INVISIBLE);

                }

            }
        });
    }

    public  void  AddButton(View v)
    {

        EditText dodajlokaciju=findViewById(R.id.lokacija);

        EditText opisLokacije=findViewById(R.id.opisLokacija);
        int prva= dodajlokaciju.getText().toString().length();

        int druga= opisLokacije.getText().toString().length();

        CheckBox ch= findViewById(R.id.check);
        boolean isCheck= ch.isChecked();
        String a=dodajlokaciju.getText().toString();
//provjera jel vec postoji u bazi
        ArrayList<String> abc= new ArrayList<>();


        for (Putovanja c: mojaLista)
        {

            abc.add(c.getName().toString());

        }




        if(prva<3)
        {
            Toast.makeText(this,"Unesno ime  lokacije je kratko\n pokusajte opet" ,Toast.LENGTH_SHORT).show();
            return;
        } else if (abc.contains(a))
        {
            Toast.makeText(this,"vec postoji unesena lokacija: " +a ,Toast.LENGTH_SHORT).show();
            return;
        }
        else if(druga<10)
        {
            Toast.makeText(this,"Opis lokacije prekratko\n pokusajte opet" ,Toast.LENGTH_SHORT).show();
            return;
        }

        //provjera jel jel botun oznacen ako je idi u slikat ako ne sprema bez slike --ne zab provjeru datuma i slike
        else if (isCheck==false)
        {
            String lokMjesto=dodajlokaciju.getText().toString();
            String lokOpis=opisLokacije.getText().toString();
            boolean test=false;

            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run()
                {
                    PutovanjaDataBase putovanjaDataBase=PutovanjaDataBase
                            .getInstance(AddTrip.this);

                    Putovanja putovanja = new Putovanja(lokMjesto,lokOpis,test);

                    putovanjaDataBase.putovanjaIDao().insertTrip(putovanja);

                }
            });

            Toast.makeText(this,"User successfully added in db" + test ,Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);



        }




         else
        {


            String lokMjesto=dodajlokaciju.getText().toString();
            String lokOpis=opisLokacije.getText().toString();
            boolean test=true;
            String putanjaSlike;
            String datumlokacije= date;

            if (date== null)
            {
                Toast.makeText(this, "Datum nije umetnut", Toast.LENGTH_SHORT).show();
                return;

            }


            else
            {


                byte[] slika = getBytes(imageView);
                if(slika==null)
                {
                    Toast.makeText(this, "Slika nije postavljena", Toast.LENGTH_SHORT).show();
                    return;
                }

                putanjaSlike = Base64.getEncoder().encodeToString(slika);


                //ode ide za bazu i umetanje kod
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        PutovanjaDataBase putovanjaDataBase = PutovanjaDataBase
                                .getInstance(AddTrip.this);

                        Putovanja putovanja = new Putovanja(lokMjesto, lokOpis, test, putanjaSlike, datumlokacije);

                        putovanjaDataBase.putovanjaIDao().insertTrip(putovanja);


                    }
                });


                Toast.makeText(this, "User successfully added in db" + putanjaSlike, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);

            }

        }

    }
    private  void pickImmage()
    {
        Intent intent =new Intent(MediaStore.ACTION_PICK_IMAGES);
        if (intent==null)
        {
            Toast.makeText(AddTrip.this,"Sliku niste stavili",Toast.LENGTH_SHORT).show();
            return;
        }
        else {
        rez.launch(intent);}
    }
    private  void rezult()
    {
        rez=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o)
            {
                try
                {
                    Uri imgUrl= o.getData().getData();
                    imageView.setImageURI(imgUrl);

                }
                catch (NullPointerException e)
                {
                    Toast.makeText(AddTrip.this,"Sliku niste stavili",Toast.LENGTH_SHORT).show();

                    return;
                }



            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {

        super.onActivityResult(requestCode,resultCode,data);

        try {


        if(requestCode==100)
        {
            Bitmap bitmap=(Bitmap) data.getExtras().get("data");
            bitmapAdd=bitmap;
            imageView.setImageBitmap(bitmap);


        }}

        catch (NullPointerException e)
        {
            Toast.makeText(this,"Sliku niste stavili",Toast.LENGTH_SHORT).show();
        }
    };
    public byte[] getBytes(ImageView imageView) {
        try {
            Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, stream);
            byte[] bytesData = stream.toByteArray();
            stream.close();
            return bytesData;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    }


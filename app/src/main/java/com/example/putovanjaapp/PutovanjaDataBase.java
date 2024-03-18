package com.example.putovanjaapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;

@Database(entities = {Putovanja.class}, exportSchema = false, version = 1)

public abstract class PutovanjaDataBase extends RoomDatabase
{

    private static final String DATABASE_NAME = "db_Putovanja";
    private static PutovanjaDataBase putovanjaDataBase;

    public static synchronized PutovanjaDataBase getInstance(final Context context) {
        if(putovanjaDataBase == null) {
            putovanjaDataBase = Room
                    .databaseBuilder(context.getApplicationContext(), PutovanjaDataBase.class, DATABASE_NAME)
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            Executors.newSingleThreadExecutor().execute(new Runnable()
                            {
                                @Override
                                public void run() {
                                    getInstance(context).putovanjaIDao().insertAll();
                                }
                            });
                        }
                    })
                    .build();
        }

        return putovanjaDataBase;
    }

    public abstract PutovanjaDao putovanjaIDao();

}

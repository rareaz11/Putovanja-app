package com.example.putovanjaapp;

import androidx.core.location.LocationRequestCompat;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PutovanjaDao
{
    @Query("SELECT*FROM putovanja")
    List<Putovanja> getAlltrips();

    @Query("SELECT*FROM putovanja WHERE id= :idSelect")
  public Putovanja getByID(int idSelect);

    @Query("SELECT*FROM putovanja Where isTravel= 0")
    List<Putovanja> getMyWish();

    @Query("SELECT *FROM putovanja where isTravel=1")
    List<Putovanja> getMyComplitedTrips();

    @Insert
    void insertTrip(Putovanja putovanja);

    @Query("DELETE FROM putovanja where id= :id")
    void deleteTrip(int id);

    @Query("UPDATE putovanja SET name= :name WHERE id= :id ")

    void  updateTrip(int id, String name);
    @Insert
    void insertAll(Putovanja... users);



}

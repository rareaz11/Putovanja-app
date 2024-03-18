package com.example.putovanjaapp;

import androidx.room.Entity;

import androidx.room.PrimaryKey;

@Entity(tableName = "putovanja")
public class Putovanja
{
    @PrimaryKey(autoGenerate = true)
    private  int id;
    private String name;
    private String opis;
    private String byteSlika;
    //neprepoznaje byte pokusao sa column info
    private String vrijeme;
    private boolean isTravel;




    public Putovanja(String name, String opis, boolean isTravel, String picture, String vrijeme)
    {

        this.name=name;
        this.opis=opis;
        this.isTravel=isTravel;
        this.byteSlika=picture;
        this.vrijeme=vrijeme;


    }

    public  Putovanja(String name, String opis ,boolean isTravel)
    {
        this.name=name;
        this.opis=opis;
        this.isTravel=isTravel;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public boolean isTravel() {
        return isTravel;
    }

    public void setTravel(boolean travel) {
        isTravel = travel;
    }


    public String getVrijeme() {
        return vrijeme;
    }

    public void setVrijeme(String vrijeme) {
        this.vrijeme = vrijeme;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getByteSlika() {
        return byteSlika;
    }

    public void setByteSlika(String byteSlika) {
        this.byteSlika = byteSlika;
    }
}





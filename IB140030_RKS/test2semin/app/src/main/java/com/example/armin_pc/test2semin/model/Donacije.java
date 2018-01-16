package com.example.armin_pc.test2semin.model;

import android.support.annotation.Nullable;

import java.util.Date;

/**
 * Created by Armin_pc on 30.11.2017.
 */

public class Donacije {
    public int DonacijaId;
    public
    @Nullable
    int TransfuzijskiCentarId;
    public
    @Nullable
    int DonatorId;
    public
    @Nullable
    int KorisnikId;
    public Date DatumDonacije;
    public int BrojDoza;
    public int Kolicina;
    public Boolean UspjesnoRealizovana;
    public Boolean Odbijena;
    public String Napomena;
}

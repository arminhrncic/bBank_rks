package com.example.armin_pc.test2semin.model;

/**
 * Created by Armin_pc on 28.11.2017.
 */

public class KrvnaGrupaPair {

    public int KrvnaGrupaIndex;
    public String Naziv;

    @Override
    public String toString() {
        return this.Naziv;
    }

    public KrvnaGrupaPair(int index, String naziv){
        this.KrvnaGrupaIndex = index;
        this.Naziv = naziv;
    }

}

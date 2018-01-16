package com.example.armin_pc.test2semin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.armin_pc.test2semin.api.DonatoriApi;
import com.example.armin_pc.test2semin.helper.MyRunnable;
import com.example.armin_pc.test2semin.helper.Sesija;
import com.example.armin_pc.test2semin.helper.Validacija;
import com.example.armin_pc.test2semin.model.Donatori;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Armin_pc on 18.11.2017.
 */

public class MojiPodaciFragment extends android.app.Fragment {

    private static final int RESULT_LOAD_IMAGE = 1;
    private ImageView imageDonator;
    private  TextView spasi;
    View myView;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null)
        {
            Uri selectedImage = data.getData();
            imageDonator.setImageURI(selectedImage);
            spasi.setVisibility(View.VISIBLE);

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.moji_podaci_layout,container,false);
        getActivity().setTitle("Moji podaci");

        String stringDate = DateFormat.getDateInstance().format(Sesija.logiraniDonator.DatumRodjenja);
        spasi = (TextView)myView.findViewById(R.id.textViewSpasi);
        ((TextView)myView.findViewById(R.id.datumRodjValue)).setText(stringDate);
        ((TextView)myView.findViewById(R.id.emailValue)).setText(Sesija.logiraniDonator.Email);
        ((TextView)myView.findViewById(R.id.adresaValue)).setText(Sesija.logiraniDonator.Adresa);
        ((TextView)myView.findViewById(R.id.telefonValue)).setText(Sesija.logiraniDonator.Telefon);
        ((TextView)myView.findViewById(R.id.gradValue)).setText(Sesija.logiraniDonator.Grad);
        ((TextView)myView.findViewById(R.id.txtImePrezimePodaci)).setText(Sesija.logiraniDonator.Ime + " " + Sesija.logiraniDonator.Prezime);
        imageDonator = (ImageView)myView.findViewById(R.id.imageDonator);

        if (!Validacija.isNullOrEmpty(Sesija.logiraniDonator.Slika))
        {
            try{
                byte [] encodeByte=Base64.decode(Sesija.logiraniDonator.Slika,Base64.DEFAULT);
                Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                if ( bitmap != null) {
                    imageDonator.setImageBitmap(bitmap);
                }
            }catch(Exception e){
                e.getMessage();
            }
        }




        imageDonator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent,RESULT_LOAD_IMAGE);
            }
        });

        spasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap image = ((BitmapDrawable)imageDonator.getDrawable()).getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                final String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(),Base64.DEFAULT);

                final Donatori donator = new Donatori();
                donator.Slika = encodedImage;
                donator.DonatorId = Sesija.logiraniDonator.DonatorId;
                donator.Ime = Sesija.logiraniDonator.Ime;
                donator.Prezime = Sesija.logiraniDonator.Prezime;
                donator.Adresa = Sesija.logiraniDonator.Adresa;
                donator.Email = Sesija.logiraniDonator.Email;
                donator.Telefon = Sesija.logiraniDonator.Telefon;
                donator.DatumRodjenja = Sesija.logiraniDonator.DatumRodjenja;
                donator.Aktivan = Sesija.logiraniDonator.Aktivan;
                donator.KrvnaGrupa = Sesija.logiraniDonator.KrvnaGrupa;
                donator.Spol = Sesija.logiraniDonator.Spol;
                donator.DatumRegistracije = Sesija.logiraniDonator.DatumRegistracije;

                DonatoriApi.IzmjenaSlike(getActivity(), String.valueOf(donator.DonatorId), donator, new MyRunnable<Donatori>() {
                    @Override
                    public void run(Donatori result) {
                        Toast.makeText(MyApp.getContext(), "Uspjesno dodana slika", Toast.LENGTH_LONG).show();
                    }
                });


            }
        });


        ((CheckBox)myView.findViewById(R.id.checkBoxDonator)).setEnabled(false);
        if (Sesija.logiraniDonator.Aktivan == true)
        {
            ((CheckBox)myView.findViewById(R.id.checkBoxDonator)).setChecked(true);
        }
        ((Button)myView.findViewById(R.id.btnKrvnaGrupaPodaci)).setText(Sesija.logiraniDonator.KrvnaGrupa);


        Button btnUredi = (Button)myView.findViewById(R.id.btnUrediPodatke);
        btnUredi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, new UrediPodatkeFragment()).commit();
            }
        });


        return myView;
    }
}

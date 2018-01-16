package com.example.armin_pc.test2semin;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.armin_pc.test2semin.api.DonacijeApi;
import com.example.armin_pc.test2semin.api.DonatoriApi;
import com.example.armin_pc.test2semin.helper.MyRunnable;
import com.example.armin_pc.test2semin.helper.Sesija;
import com.example.armin_pc.test2semin.helper.Validacija;
import com.example.armin_pc.test2semin.model.KrvnaGrupaPair;
import com.example.armin_pc.test2semin.model.bsp_Donacije_GetByDonatorIdHCI_Result;
import com.example.armin_pc.test2semin.model.bsp_Donatori_GetByKrvnaGrupaIndexHCI_Result;
import com.example.armin_pc.test2semin.model.bsp_TransfuzijskiCentri_SelectAll_Result;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Armin_pc on 18.11.2017.
 */

public class PretragaDonatoraFragment extends android.app.Fragment {

    View myView;
    private KrvnaGrupaPair[] krvnaGrupaPairs = {
            new KrvnaGrupaPair(-1,"Sve grupe"),
            new KrvnaGrupaPair(0,"A+"),new KrvnaGrupaPair(1,"A-"),
            new KrvnaGrupaPair(2,"B+"),new KrvnaGrupaPair(3,"B-"),
            new KrvnaGrupaPair(4,"AB+"),new KrvnaGrupaPair(5,"AB-"),
            new KrvnaGrupaPair(6,"0+"),new KrvnaGrupaPair(7,"0-")

    };
    Spinner spinner;
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.pretraga_layout,container,false);
        getActivity().setTitle("Pretraga donatora");


        spinner = (Spinner)myView.findViewById(R.id.spinnerKrvnaGrupa);
        ArrayAdapter<KrvnaGrupaPair> adapter = new ArrayAdapter<KrvnaGrupaPair>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, krvnaGrupaPairs);
        spinner.setAdapter(adapter);


        do_UcitajDonatore(((KrvnaGrupaPair)spinner.getSelectedItem()).KrvnaGrupaIndex);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                do_UcitajDonatore(((KrvnaGrupaPair)spinner.getSelectedItem()).KrvnaGrupaIndex);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                do_UcitajDonatore(((KrvnaGrupaPair)spinner.getSelectedItem()).KrvnaGrupaIndex);

            }
        });

        return myView;
    }

    private void do_UcitajDonatore(int krvnaGrupaIndex) {
        DonatoriApi.PretragaDonatora(getActivity(),String.valueOf(krvnaGrupaIndex),
                new MyRunnable<bsp_Donatori_GetByKrvnaGrupaIndexHCI_Result[]>(){
                    @Override
                    public void run(final bsp_Donatori_GetByKrvnaGrupaIndexHCI_Result[] result) {
                        if (result != null ) {

                            ListView lista = (ListView)myView.findViewById(R.id.listViewdonatori);
                            lista.setAdapter(new BaseAdapter() {
                                @Override
                                public int getCount() {
                                    return result.length;
                                }

                                @Override
                                public Object getItem(int position) {
                                    return null;
                                }

                                @Override
                                public long getItemId(int position) {
                                    return 0;
                                }

                                @Override
                                public View getView(int position, View convertView, ViewGroup parent) {
                                    if (convertView == null) {
                                        LayoutInflater inflater1 = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        convertView = inflater1.inflate(R.layout.pretraga_item, parent, false);
                                    }
                                    TextView text = (TextView)convertView.findViewById(R.id.txtImeIPrezimeDonatora);
                                    text.setText(result[position].Ime + " " + result[position].Prezime);
                                    return  convertView;
                                }
                            });

                            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                                    // custom dialog
                                    final Dialog dialog = new Dialog(getActivity());
                                    dialog.setContentView(R.layout.donator_dialog_layout);
                                    Date currentTime = Calendar.getInstance().getTime();
                                    int Godine =  currentTime.getYear() - result[position].DatumRodjenja.getYear();
                                    String title = result[position].Ime + " " + result[position].Prezime + " (" + Godine + ")";
                                    dialog.setTitle(title);

                                    // set the custom dialog components - text, image and button
                                    TextView text = (TextView) dialog.findViewById(R.id.textPodaci);
                                    String alert1 = result[position].Email;
                                    String alert2 = result[position].Adresa;
                                    String alert3 = result[position].Grad;
                                    text.setText(alert1 +"\n"+ alert2 +"\n"+ alert3);
                                    final TextView text3 = (TextView)dialog
                                            .findViewById(R.id.textNaslov);
                                    text3.setText(title);

                                    text3.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                        }
                                    });
                                    TextView textTelefon = (TextView) dialog.findViewById(R.id.textTelefon);
                                    textTelefon.setText(result[position].Telefon);
                                    // text2.setPaintFlags(text2.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                                    ImageView image = (ImageView) dialog.findViewById(R.id.imageDialog);
                                    image.setImageResource(R.drawable.user_manja);
                                    if (!Validacija.isNullOrEmpty(result[position].Slika))
                                    {
                                        try{
                                            byte [] encodeByte= Base64.decode(result[position].Slika,Base64.DEFAULT);
                                            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                                            if ( bitmap != null) {
                                                image.setImageBitmap(bitmap);
                                            }
                                        }catch(Exception e){
                                            e.getMessage();
                                        }
                                    }

                                    dialog.setCancelable(true);

                                    dialog.show();


                                }
                            });

                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Pogreška u komunikaciji", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}

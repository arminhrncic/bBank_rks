package com.example.armin_pc.test2semin;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.armin_pc.test2semin.api.DonacijeApi;
import com.example.armin_pc.test2semin.api.ZahtjevApi;
import com.example.armin_pc.test2semin.helper.MyRunnable;
import com.example.armin_pc.test2semin.helper.Sesija;
import com.example.armin_pc.test2semin.model.bsp_Donacije_GetByDonatorIdHCI_Result;
import com.example.armin_pc.test2semin.model.bsp_Zahtjevi_GetByDonatorId_Result;

import java.text.DateFormat;

/**
 * Created by Armin_pc on 18.11.2017.
 */

public class MojeDonacijeFragment extends android.app.Fragment {

    private  ListView lista;
    View myView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.moje_donacije_layout,container,false);
        getActivity().setTitle("Moje donacije");

        if (Sesija.logiraniDonatorPuniPodaci.DatumZadnjeDonacije != null) {
            String strDate = DateFormat.getDateInstance().format(Sesija.logiraniDonatorPuniPodaci.DatumZadnjeDonacije);
            String sKolicina = Sesija.logiraniDonatorPuniPodaci.UkupnaKolicina + "ml";

            ((TextView) myView.findViewById(R.id.txtPosljednjaDonacija)).
                    setText(strDate);
            ((TextView) myView.findViewById(R.id.txtUkupnoDonirano)).
                    setText(sKolicina);

            do_getDonacije();
        }


        Button btn = (Button)myView.findViewById(R.id.button2donacija);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, new NovaDonacijaFragment()).commit();
            }
        });



        return myView;
    }

    private void do_getDonacije() {
        DonacijeApi.ProvjeraDonacije(getActivity(),String.valueOf(Sesija.logiraniDonator.DonatorId),
                new MyRunnable<bsp_Donacije_GetByDonatorIdHCI_Result[]>(){
                    @Override
                    public void run(final bsp_Donacije_GetByDonatorIdHCI_Result[] result) {
                        if (result != null ) {
                            lista = (ListView)myView.findViewById(R.id.listViewDonacije);
                            lista.setAdapter(new BaseAdapter() {
                                @Override
                                public int getCount() {
                                    return  result.length;
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
                                        convertView = inflater1.inflate(R.layout.donacija_item, parent, false);
                                    }

                                    String stringDate = DateFormat.getDateInstance().format(result[position].DatumDonacije);
                                    String strKolicina = result[position].Kolicina + "ml";
                                    String strPotvrdio = "Potvrdio dr. : " + result[position].Zaposlenik;

                                    ((TextView)convertView.findViewById(R.id.txtDatumDonacija)).
                                            setText(stringDate);
                                    ((TextView)convertView.findViewById(R.id.txtKolicinaDonacije)).
                                            setText(strKolicina);
                                    ((TextView)convertView.findViewById(R.id.txtPotvrdioKorisnik)).
                                            setText(strPotvrdio);

                                    return  convertView;
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

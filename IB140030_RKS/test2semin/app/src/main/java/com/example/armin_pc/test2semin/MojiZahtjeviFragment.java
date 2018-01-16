package com.example.armin_pc.test2semin;

import android.content.Context;
import android.content.Intent;
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

import com.example.armin_pc.test2semin.api.LoginApi;
import com.example.armin_pc.test2semin.api.ZahtjevApi;
import com.example.armin_pc.test2semin.helper.MyRunnable;
import com.example.armin_pc.test2semin.helper.Sesija;
import com.example.armin_pc.test2semin.model.bsp_Donatori_GetByEmailHCI_Result;
import com.example.armin_pc.test2semin.model.bsp_Zahtjevi_GetByDonatorId_Result;

import java.text.DateFormat;

import static com.example.armin_pc.test2semin.R.id.txtPassword;
import static com.example.armin_pc.test2semin.R.id.view_offset_helper;

/**
 * Created by Armin_pc on 18.11.2017.
 */

public class MojiZahtjeviFragment extends android.app.Fragment {

    View myView;
    private  ListView lista;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.moji_zahtjevi_layout,container,false);
        getActivity().setTitle("Moji zahtjevi");
        do_getZahtjevi();

        Button btn = (Button)myView.findViewById(R.id.NoviZahtjev);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, new NoviZahtjevFragment()).commit();

            }
        });

        return myView;
    }

    private void do_getZahtjevi() {
        ZahtjevApi.ProvjeraZahtjevi(getActivity(),String.valueOf(Sesija.logiraniDonator.DonatorId),
                new MyRunnable<bsp_Zahtjevi_GetByDonatorId_Result[]>(){
            @Override
            public void run(final bsp_Zahtjevi_GetByDonatorId_Result[] result) {
                if (result != null ) {
                    lista = (ListView)myView.findViewById(R.id.ListViewZahtjev);
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
                                convertView = inflater1.inflate(R.layout.zahtjev_item, parent, false);
                            }
                            String stringDate = DateFormat.getDateInstance().format(result[position].DatumZahtjeva);
                            String strStatus;
                            String strKolicina = String.valueOf(result[position].Kolicina) + "ml";
                            if (result[position].StatusZahtjevaId == 2)
                            strStatus = "Potvrđeno : Da";
                            else
                                strStatus = "Potvrđeno : Ne";

                            ((TextView)convertView.findViewById(R.id.txtDatumZahtjeva)).
                                    setText(stringDate);
                            ((TextView)convertView.findViewById(R.id.txtTransfCentar)).setText(result[position].TransfuzijskiCentar);
                            ((TextView)convertView.findViewById(R.id.txtKolicina)).setText(strKolicina);
                            ((TextView)convertView.findViewById(R.id.txtPotvrda)).setText(strStatus);
                             ((Button)convertView.findViewById(R.id.btnKrvnaGrupaZahtjev)).setText(result[position].NazivKrvneGrupe);

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

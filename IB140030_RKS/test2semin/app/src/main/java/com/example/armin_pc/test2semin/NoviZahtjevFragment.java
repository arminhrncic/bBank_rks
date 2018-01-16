package com.example.armin_pc.test2semin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.armin_pc.test2semin.api.CentriApi;
import com.example.armin_pc.test2semin.api.DonacijeApi;
import com.example.armin_pc.test2semin.api.GradoviApi;
import com.example.armin_pc.test2semin.api.LoginApi;
import com.example.armin_pc.test2semin.api.StanjaApi;
import com.example.armin_pc.test2semin.api.ZahtjevApi;
import com.example.armin_pc.test2semin.helper.MyRunnable;
import com.example.armin_pc.test2semin.helper.Sesija;
import com.example.armin_pc.test2semin.helper.Validacija;
import com.example.armin_pc.test2semin.model.ZahtjevZaKrv;
import com.example.armin_pc.test2semin.model.bsp_Donacije_GetByDonatorIdHCI_Result;
import com.example.armin_pc.test2semin.model.bsp_Donatori_GetByEmailHCI_Result;
import com.example.armin_pc.test2semin.model.bsp_Gradovi_SelectAll_Result;
import com.example.armin_pc.test2semin.model.bsp_Stanja_SelectByCentarId_Result;
import com.example.armin_pc.test2semin.model.bsp_TransfuzijskiCentri_SelectAll_Result;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static com.example.armin_pc.test2semin.R.drawable.roundbutton;
import static com.example.armin_pc.test2semin.R.id.txtPassword;
import static com.example.armin_pc.test2semin.R.id.view_offset_helper;

/**
 * Created by Armin_pc on 18.11.2017.
 */

public class NoviZahtjevFragment extends android.app.Fragment {

    View myView;
    private  Spinner spinner;
    private Spinner s;
    private String[] arraySpinner;
    private  String odabranaKrvnagrupa="";
    private Button btnApoz;
    private Button btnAneg;
    private Button btnBpoz;
    private Button btnBneg;
    private Button btnABpoz;
    private Button btnABneg;
    private Button btn0poz;
    private Button btn0neg;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.novi_zahtjev_layout,container,false);
        getActivity().setTitle("Novi zahtjev");
        do_getCentre();
        do_getDoze();

        btnApoz = (Button)myView.findViewById(R.id.buttonApozitivna);
        btnAneg = (Button)myView.findViewById(R.id.buttonAnegativna);
        btnBpoz = (Button)myView.findViewById(R.id.buttonBpozitivna);
        btnBneg = (Button)myView.findViewById(R.id.buttonBnegativna);
        btnABpoz = (Button)myView.findViewById(R.id.buttonABpozitivna);
        btnABneg = (Button)myView.findViewById(R.id.buttonABnegativna);
        btn0poz = (Button)myView.findViewById(R.id.button0poziivna);
        btn0neg = (Button)myView.findViewById(R.id.button0negativna);

        do_setButtonClicks();




        Button btnZatraziKrv = (Button)myView.findViewById(R.id.btnZatraziKrv);

        btnZatraziKrv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!Validacija.isNullOrEmpty(odabranaKrvnagrupa)) {
                    bsp_TransfuzijskiCentri_SelectAll_Result centar = (bsp_TransfuzijskiCentri_SelectAll_Result) spinner.getSelectedItem();

                    ZahtjevZaKrv zahtjev = new ZahtjevZaKrv();
                    zahtjev.TransfuzijskiCentarId = centar.TransfuzijskiCentarId;
                    zahtjev.BrojDoza = Integer.parseInt(s.getSelectedItem().toString());
                    zahtjev.Kolicina = 450 * zahtjev.BrojDoza;
                    zahtjev.DonatorId = Sesija.logiraniDonator.DonatorId;
                    zahtjev.DatumZahtjeva = Calendar.getInstance().getTime();
                    zahtjev.NazivKrvneGrupe = odabranaKrvnagrupa;
                    zahtjev.StatusZahtjevaId = 1;
                    ZahtjevApi.SlanjeZahtjeva(getActivity(), zahtjev, new MyRunnable<ZahtjevZaKrv>() {
                        @Override
                        public void run(ZahtjevZaKrv result) {
                            Toast.makeText(MyApp.getContext(), "Uspjesno", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else {
                    Toast.makeText(getActivity(),"Odaberite krvnu grupu ", Toast.LENGTH_LONG).show();
                }
            }
        });



        return myView;
    }

    private void do_setButtonClicks() {
        btnApoz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_uncheckButtons();
                odabranaKrvnagrupa = "A+";
                btnApoz.setBackground(getActivity().getResources().getDrawable(R.drawable.roundbutton_gray));

            }
        });

        btnAneg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_uncheckButtons();
                odabranaKrvnagrupa = "A-";
                btnAneg.setBackground(getActivity().getResources().getDrawable(R.drawable.roundbutton_gray));

            }
        });

        btnBpoz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_uncheckButtons();
                odabranaKrvnagrupa = "B+";
                btnBpoz.setBackground(getActivity().getResources().getDrawable(R.drawable.roundbutton_gray));

            }
        });

        btnBneg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_uncheckButtons();
                odabranaKrvnagrupa = "B-";
                btnBneg.setBackground(getActivity().getResources().getDrawable(R.drawable.roundbutton_gray));

            }
        });

        btnABpoz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_uncheckButtons();
                odabranaKrvnagrupa = "AB+";
                btnABpoz.setBackground(getActivity().getResources().getDrawable(R.drawable.roundbutton_gray));

            }
        });

        btnABneg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_uncheckButtons();
                odabranaKrvnagrupa = "AB-";
                btnABneg.setBackground(getActivity().getResources().getDrawable(R.drawable.roundbutton_gray));

            }
        });

        btn0poz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_uncheckButtons();
                odabranaKrvnagrupa = "0+";
                btn0poz.setBackground(getActivity().getResources().getDrawable(R.drawable.roundbutton_gray));

            }
        });

        btn0neg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_uncheckButtons();
                odabranaKrvnagrupa = "0-";
                btn0neg.setBackground(getActivity().getResources().getDrawable(R.drawable.roundbutton_gray));

            }
        });

    }

    private void do_uncheckButtons() {
        btnApoz.setBackground(getActivity().getResources().getDrawable(R.drawable.roundbutton));
        btnAneg.setBackground(getActivity().getResources().getDrawable(R.drawable.roundbutton));
        btnBpoz.setBackground(getActivity().getResources().getDrawable(R.drawable.roundbutton));
        btnBneg.setBackground(getActivity().getResources().getDrawable(R.drawable.roundbutton));
        btnABpoz.setBackground(getActivity().getResources().getDrawable(R.drawable.roundbutton));
        btnABneg.setBackground(getActivity().getResources().getDrawable(R.drawable.roundbutton));
        btn0poz.setBackground(getActivity().getResources().getDrawable(R.drawable.roundbutton));
        btn0neg.setBackground(getActivity().getResources().getDrawable(R.drawable.roundbutton));
    }


    private void do_getDoze() {
        this.arraySpinner = new String[] {
                "1", "2", "3"
        };
        s = (Spinner) myView.findViewById(R.id.spinnerDoza);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, arraySpinner);
        s.setAdapter(adapter);
    }


    private void do_getCentre() {

        CentriApi.ProvjeraCentri(getActivity(), new MyRunnable<bsp_TransfuzijskiCentri_SelectAll_Result[]>() {
            @Override
            public void run(bsp_TransfuzijskiCentri_SelectAll_Result[] result) {
                if (result != null) {
                    /*listaCentri = new bsp_TransfuzijskiCentri_SelectAll_Result[result.length];
                    listaCentri = result.clone();
                    Toast.makeText(getActivity(), listaCentri[1].Naziv, Toast.LENGTH_LONG).show();*/
                    spinner = (Spinner)myView.findViewById(R.id.spinnerCentar);
                    ArrayAdapter<bsp_TransfuzijskiCentri_SelectAll_Result> adapter = new ArrayAdapter<bsp_TransfuzijskiCentri_SelectAll_Result>(getActivity(),
                            android.R.layout.simple_spinner_dropdown_item, result);
                    spinner.setAdapter(adapter);

                    do_GetStanja();

                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            do_GetStanja();

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            do_GetStanja();

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

    private void do_GetStanja() {
        StanjaApi.ProvjeraStanja(getActivity(),String.valueOf(((bsp_TransfuzijskiCentri_SelectAll_Result)spinner.getSelectedItem()).TransfuzijskiCentarId),
                new MyRunnable<bsp_Stanja_SelectByCentarId_Result[]>(){
                    @Override
                    public void run(final bsp_Stanja_SelectByCentarId_Result[] rezultat) {
                        if (rezultat != null ) {
                            ((Button)myView.findViewById(R.id.buttonApozitivna)).setText("A+ \n" + rezultat[0].Kolicina);
                            ((Button)myView.findViewById(R.id.buttonAnegativna)).setText("A- \n" + rezultat[1].Kolicina);
                            ((Button)myView.findViewById(R.id.buttonBpozitivna)).setText("B+ \n" + rezultat[2].Kolicina);
                            ((Button)myView.findViewById(R.id.buttonBnegativna)).setText("B- \n" + rezultat[3].Kolicina);
                            ((Button)myView.findViewById(R.id.buttonABpozitivna)).setText("AB+ \n" + rezultat[4].Kolicina);
                            ((Button)myView.findViewById(R.id.buttonABnegativna)).setText("AB- \n" + rezultat[5].Kolicina);
                            ((Button)myView.findViewById(R.id.button0poziivna)).setText("0+ \n" + rezultat[6].Kolicina);
                            ((Button)myView.findViewById(R.id.button0negativna)).setText("0- \n" + rezultat[7].Kolicina);

                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Pogreška u komunikaciji", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


}

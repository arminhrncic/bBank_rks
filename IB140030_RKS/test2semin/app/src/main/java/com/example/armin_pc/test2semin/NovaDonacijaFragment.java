package com.example.armin_pc.test2semin;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.armin_pc.test2semin.api.CentriApi;
import com.example.armin_pc.test2semin.api.DonacijeApi;
import com.example.armin_pc.test2semin.api.DonatoriApi;
import com.example.armin_pc.test2semin.api.ZahtjevApi;
import com.example.armin_pc.test2semin.helper.MyRunnable;
import com.example.armin_pc.test2semin.helper.Sesija;
import com.example.armin_pc.test2semin.helper.Validacija;
import com.example.armin_pc.test2semin.model.Donacije;
import com.example.armin_pc.test2semin.model.ZahtjevZaKrv;
import com.example.armin_pc.test2semin.model.bsp_TransfuzijskiCentri_SelectAll_Result;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Armin_pc on 18.11.2017.
 */

public class NovaDonacijaFragment extends android.app.Fragment {

    private Spinner spinner;
    View myView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.nova_donacija_layout,container,false);
        getActivity().setTitle("Nova donacija");

        final EditText DatumDonacije = (EditText)myView.findViewById(R.id.txtDatumDonacije);


        do_UcitajCentre();

        DatumDonacije.setOnClickListener(new android.view.View.OnClickListener() {


            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar mcurrentDate=Calendar.getInstance();
                int mYear=mcurrentDate.get(Calendar.YEAR);
                int mMonth=mcurrentDate.get(Calendar.MONTH);
                int mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                        DatumDonacije.setText(selectedday + "/" + (selectedmonth+1) + "/" + selectedyear);
                    }
                },mYear, mMonth, mDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();  }
        });

        final EditText vrijemeDolaska = (EditText)myView.findViewById(R.id.txtVrijemeDolaska);

        vrijemeDolaska.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        vrijemeDolaska.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });


        Button btnPosalji = (Button)myView.findViewById(R.id.buttonPotvrdiDonaciju);
        btnPosalji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!Validacija.isNullOrEmpty(DatumDonacije.getText().toString()) && !Validacija.isNullOrEmpty(vrijemeDolaska.getText().toString())  ) {
                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    Date dateObject;

                    bsp_TransfuzijskiCentri_SelectAll_Result centar = (bsp_TransfuzijskiCentri_SelectAll_Result) spinner.getSelectedItem();
                    Donacije donacija = new Donacije();
                    donacija.TransfuzijskiCentarId = centar.TransfuzijskiCentarId;
                    donacija.DonatorId = Sesija.logiraniDonator.DonatorId;
                    donacija.BrojDoza = 1;
                    donacija.Kolicina = 450;
                    try {
                        dateObject = formatter.parse(DatumDonacije.getText().toString() + " " + vrijemeDolaska.getText().toString());

                        donacija.DatumDonacije = dateObject;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    //     donacija.DatumDonacije.setTime(Long.parseLong(vrijemeDolaska.getText().toString()));
                    //     Toast.makeText(getActivity(),"Donacija : " + donacija.DatumDonacije,Toast.LENGTH_LONG).show();

                    DonacijeApi.SlanjeDonacije(getActivity(), donacija, new MyRunnable<Donacije>() {
                        @Override
                        public void run(Donacije result) {
                            Toast.makeText(MyApp.getContext(), "Uspjesno poslana donacija", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else
                {
                    Toast.makeText(MyApp.getContext(), "Unesite datum i vrijeme donacije", Toast.LENGTH_LONG).show();
                }
            }
        });


        return myView;
    }

    private void do_UcitajCentre() {
        CentriApi.ProvjeraCentri(getActivity(), new MyRunnable<bsp_TransfuzijskiCentri_SelectAll_Result[]>() {
            @Override
            public void run(bsp_TransfuzijskiCentri_SelectAll_Result[] result) {
                if (result != null) {

                    spinner = (Spinner)myView.findViewById(R.id.spinnerCentarDonacija);
                    ArrayAdapter<bsp_TransfuzijskiCentri_SelectAll_Result> adapter = new ArrayAdapter<bsp_TransfuzijskiCentri_SelectAll_Result>(getActivity(),
                            android.R.layout.simple_spinner_dropdown_item, result);
                    spinner.setAdapter(adapter);

                }
                else
                {
                    Toast.makeText(getActivity(), "Pogreška u komunikaciji", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

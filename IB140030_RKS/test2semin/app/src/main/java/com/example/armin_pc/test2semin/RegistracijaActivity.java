package com.example.armin_pc.test2semin;


import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.armin_pc.test2semin.api.CentriApi;
import com.example.armin_pc.test2semin.api.DonacijeApi;
import com.example.armin_pc.test2semin.api.GradoviApi;
import com.example.armin_pc.test2semin.api.RegistracijaApi;
import com.example.armin_pc.test2semin.helper.MyRunnable;
import com.example.armin_pc.test2semin.helper.Validacija;
import com.example.armin_pc.test2semin.model.Donacije;
import com.example.armin_pc.test2semin.model.Donatori;
import com.example.armin_pc.test2semin.model.bsp_Gradovi_SelectAll_Result;
import com.example.armin_pc.test2semin.model.bsp_TransfuzijskiCentri_SelectAll_Result;

import static com.example.armin_pc.test2semin.R.styleable.View;

public class RegistracijaActivity extends AppCompatActivity {

    private Spinner spinner;
    private Spinner s;
    private EditText ime;
    private EditText prezime;
    private EditText adresa;
    private EditText email;
    private EditText telefon;
    private EditText lozinka;
    private EditText DatumRodenja;
    private RadioGroup rg;
    private  RadioButton rbMusko;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registracija_layout);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);

        DatumRodenja = (EditText)findViewById(R.id.txtDatumRodjenja);
        ime = (EditText)findViewById(R.id.txtIme);
        prezime = (EditText)findViewById(R.id.txtPrezime);
        adresa = (EditText)findViewById(R.id.txtAdresa);
        email = (EditText)findViewById(R.id.txtEmail);
        telefon = (EditText)findViewById(R.id.txtKontaktTelefon);
        lozinka = (EditText)findViewById(R.id.txtPassword);
        rg = (RadioGroup) findViewById(R.id.rgSpol);
        rbMusko = (RadioButton)findViewById(R.id.rbMusko);
        rbMusko.setChecked(true);
        ((CheckBox)findViewById(R.id.chAktivan)).setChecked(true);

       do_UcitajGradove();

        DatumRodenja.setOnClickListener(new android.view.View.OnClickListener() {


            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar mcurrentDate=Calendar.getInstance();
                int mYear=mcurrentDate.get(Calendar.YEAR);
                int mMonth=mcurrentDate.get(Calendar.MONTH);
                int mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker=new DatePickerDialog(RegistracijaActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                        DatumRodenja.setText(selectedday + "/" + (selectedmonth+1) + "/" + selectedyear);
                    }
                },mYear, mMonth, mDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();  }
        });


        this.arraySpinner = new String[] {
                "A+", "A-", "B+", "B-", "AB+","AB-","0+","0-"
        };
         s = (Spinner) findViewById(R.id.spinnerKrvnaGrupaRegistracija);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        s.setAdapter(adapter);


        Button btnSacuvaj = (Button)findViewById(R.id.Save);
        btnSacuvaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean isValidated =  do_validacija();

                if (isValidated) {
                    Donatori donator = new Donatori();

                    donator.GradId = ((bsp_Gradovi_SelectAll_Result) spinner.getSelectedItem()).GradId;
                    donator.KrvnaGrupa = s.getSelectedItem().toString();

                    //spol, aktivan, datum registracije
                    donator.DatumRegistracije = Calendar.getInstance().getTime();
                    if (((CheckBox) findViewById(R.id.chAktivan)).isChecked()) {
                        donator.Aktivan = true;
                    } else {
                        donator.Aktivan = false;
                    }
                    final RadioButton rbMusko = (RadioButton) findViewById(R.id.rbMusko);


                    donator.Spol = "M";


                    final String value =
                            ((RadioButton) findViewById(rg.getCheckedRadioButtonId()))
                                    .getText().toString();

                    if (value.equals("Musko")) {
                        donator.Spol = "M";
                    } else {
                        donator.Spol = "Ž";
                    }


                    DateFormat formatter = new java.text.SimpleDateFormat("dd/MM/yyyy");
                    Date dateObject;

                    try {
                        dateObject = formatter.parse(DatumRodenja.getText().toString());

                        donator.DatumRodjenja = dateObject;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    donator.Ime = ime.getText().toString();
                    donator.Prezime = prezime.getText().toString();
                    donator.Adresa = adresa.getText().toString();
                    donator.Email = email.getText().toString();
                    donator.Telefon = telefon.getText().toString();
                    donator.Lozinka = lozinka.getText().toString();


                    RegistracijaApi.RegistracijaDonatora(RegistracijaActivity.this, donator, new MyRunnable<Donatori>() {
                        @Override
                        public void run(Donatori result) {
                            Toast.makeText(MyApp.getContext(), "Uspjesno dodan donator", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RegistracijaActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });


                }

            }
        });

    }

    private Boolean do_validacija() {
        Boolean  ok = true;

        if (Validacija.isNullOrEmpty(ime.getText().toString()))
        {
            ok = false;
            ime.setBackground(RegistracijaActivity.this.getResources().getDrawable(R.drawable.edittext_valdation));
        }
        else
        {
            ime.setBackground(RegistracijaActivity.this.getResources().getDrawable(R.drawable.edittext_border));

        }

        if (Validacija.isNullOrEmpty(prezime.getText().toString()))
        {
            ok = false;
            prezime.setBackground(RegistracijaActivity.this.getResources().getDrawable(R.drawable.edittext_valdation));
        }
        else
        {
            prezime.setBackground(RegistracijaActivity.this.getResources().getDrawable(R.drawable.edittext_border));

        }

        if (Validacija.isNullOrEmpty(adresa.getText().toString()))
        {
            ok = false;
            adresa.setBackground(RegistracijaActivity.this.getResources().getDrawable(R.drawable.edittext_valdation));
        }
        else
        {
            adresa.setBackground(RegistracijaActivity.this.getResources().getDrawable(R.drawable.edittext_border));

        }

        if (Validacija.isNullOrEmpty(email.getText().toString()))
        {
            ok = false;
            email.setBackground(RegistracijaActivity.this.getResources().getDrawable(R.drawable.edittext_valdation));
        }
        else
        {
            if (!Validacija.isValidEmail(email.getText().toString()))
            {
                ok = false;
                email.setBackground(RegistracijaActivity.this.getResources().getDrawable(R.drawable.edittext_valdation));

            }
            else
            {
                email.setBackground(RegistracijaActivity.this.getResources().getDrawable(R.drawable.edittext_border));
            }

        }


        if (Validacija.isNullOrEmpty(DatumRodenja.getText().toString()))
        {
            ok = false;
            DatumRodenja.setBackground(RegistracijaActivity.this.getResources().getDrawable(R.drawable.edittext_valdation));
        }
        else
        {
            DatumRodenja.setBackground(RegistracijaActivity.this.getResources().getDrawable(R.drawable.edittext_border));

        }


        if (Validacija.isNullOrEmpty(telefon.getText().toString()))
        {
            ok = false;
            telefon.setBackground(RegistracijaActivity.this.getResources().getDrawable(R.drawable.edittext_valdation));
        }
        else
        {
            if (!Validacija.isNumeric(telefon.getText().toString()) || (telefon.getText().toString().length() < 9 ))
            {
                ok = false;
                telefon.setBackground(RegistracijaActivity.this.getResources().getDrawable(R.drawable.edittext_valdation));
                ((TextView)findViewById(R.id.KontakTelefon)).setText("Kontakt Telefon (min 9 cifara)");
            }
            else {
                ((TextView)findViewById(R.id.KontakTelefon)).setText("Kontakt Telefon");
                telefon.setBackground(RegistracijaActivity.this.getResources().getDrawable(R.drawable.edittext_border));
            }
        }




        if (Validacija.isNullOrEmpty(lozinka.getText().toString()))
        {
            ok = false;
            lozinka.setBackground(RegistracijaActivity.this.getResources().getDrawable(R.drawable.edittext_valdation));
        }
        else
        {
            if (!Validacija.isValidPassword(lozinka.getText().toString(),false) )
            {
                ok = false;
                lozinka.setBackground(RegistracijaActivity.this.getResources().getDrawable(R.drawable.edittext_valdation));
                ((TextView)findViewById(R.id.Password)).setText("Lozinka (min 6 karaktera )");

            }
            else {
                lozinka.setBackground(RegistracijaActivity.this.getResources().getDrawable(R.drawable.edittext_border));
                ((TextView)findViewById(R.id.Password)).setText("Lozinka");

            }
        }

        return  ok;
    }


    private void do_UcitajGradove() {
        GradoviApi.ProvjeraGrada(RegistracijaActivity.this, new MyRunnable<bsp_Gradovi_SelectAll_Result[]>() {
            @Override
            public void run(bsp_Gradovi_SelectAll_Result[] result) {
                if (result != null) {

                    spinner = (Spinner)findViewById(R.id.spinnerGrad);
                    ArrayAdapter<bsp_Gradovi_SelectAll_Result> adapter = new ArrayAdapter<bsp_Gradovi_SelectAll_Result>(RegistracijaActivity.this,
                            android.R.layout.simple_spinner_dropdown_item, result);
                    spinner.setAdapter(adapter);

                }
                else
                {
                    Toast.makeText(RegistracijaActivity.this, "Pogreška u komunikaciji", Toast.LENGTH_LONG).show();
                }
            }
        });




    }

    private String[] arraySpinner;








}

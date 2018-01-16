package com.example.armin_pc.test2semin;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import com.example.armin_pc.test2semin.api.DonatoriApi;
import com.example.armin_pc.test2semin.api.GradoviApi;
import com.example.armin_pc.test2semin.helper.MyRunnable;
import com.example.armin_pc.test2semin.helper.Sesija;
import com.example.armin_pc.test2semin.helper.Validacija;
import com.example.armin_pc.test2semin.model.Donatori;
import com.example.armin_pc.test2semin.model.bsp_Gradovi_SelectAll_Result;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Armin_pc on 18.11.2017.
 */

public class UrediPodatkeFragment extends android.app.Fragment {

    private Spinner spinner;
    private Spinner s;
    private String[] arraySpinner;
    private  EditText txtIme;
    private  EditText txtPrezime;
    private EditText txtAdresa;
    private EditText txtEmail;
    private EditText txtTelefon;
    private EditText txtLozinka;
    private EditText DatumRodenja;
    View myView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.uredi_podatke_layout,container,false);
        getActivity().setTitle("Uređivanje podataka");

        txtIme = (EditText)myView.findViewById(R.id.txtImeEdit);
        txtPrezime = (EditText)myView.findViewById(R.id.txtPrezimeEdit);
        txtAdresa = (EditText)myView.findViewById(R.id.txtAdresaEdit);
        txtEmail = (EditText)myView.findViewById(R.id.txtEmailEdit);
        txtTelefon = (EditText)myView.findViewById(R.id.txtKontaktTelefonEdit);
        txtLozinka = (EditText)myView.findViewById(R.id.txtPasswordEdit);

        RadioButton rbMusko = (RadioButton)myView.findViewById(R.id.rbMuskoEdit);
        RadioButton rbZensko = (RadioButton)myView.findViewById(R.id.rbZenskoEdit);
        final CheckBox chAktivan = (CheckBox)myView.findViewById(R.id.chAktivanEdit);

        // EditText txt = (EditText)myView.findViewById(R.id.txtImeEdit);

        txtIme.setText(Sesija.logiraniDonator.Ime);
        txtPrezime.setText(Sesija.logiraniDonator.Prezime);
        txtAdresa.setText(Sesija.logiraniDonator.Adresa);
        txtEmail.setText(Sesija.logiraniDonator.Email);
        txtTelefon.setText(Sesija.logiraniDonator.Telefon);
        if (Sesija.logiraniDonator.Spol.equals("M"))
        {
            rbMusko.setChecked(true);
        }
        else
        {
            rbZensko.setChecked(true);
        }

        if(Sesija.logiraniDonator.Aktivan)
        {
            chAktivan.setChecked(true);
        }

        do_UcitajGradove();
       DatumRodenja = (EditText)myView.findViewById(R.id.txtDatumRodjenjaEdit);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String date = format.format(Sesija.logiraniDonator.DatumRodjenja);
        DatumRodenja.setText(date);

        DatumRodenja.setOnClickListener(new android.view.View.OnClickListener() {


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
                        DatumRodenja.setText(selectedday + "/" + (selectedmonth+1) + "/" + selectedyear);
                    }
                },mYear, mMonth, mDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();  }
        });


        this.arraySpinner = new String[] {
                "A+", "A-", "B+", "B-", "AB+","AB-","0+","0-"
        };
        s = (Spinner) myView.findViewById(R.id.spinnerKrvnaGrupaRegistracijaEdit);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, arraySpinner);
        s.setAdapter(adapter);
        for (int i=0;i<arraySpinner.length;i++)
        {
            if (arraySpinner[i].equals(Sesija.logiraniDonator.KrvnaGrupa))
            {
                s.setSelection(i);
            }
        }


        Button btnSaveEdit = (Button)myView.findViewById(R.id.SaveEdit);
        btnSaveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean isValidated =  do_validacija();

                if (isValidated) {
                    Donatori donator = new Donatori();
                    donator.DonatorId = Sesija.logiraniDonator.DonatorId;
                    donator.Ime = txtIme.getText().toString();
                    donator.Prezime = txtPrezime.getText().toString();
                    donator.Adresa = txtAdresa.getText().toString();
                    donator.Email = txtEmail.getText().toString();
                    donator.Telefon = txtTelefon.getText().toString();

                    if (!txtLozinka.getText().toString().isEmpty()) {
                        donator.Lozinka = txtLozinka.getText().toString();
                    } else {
                        donator.Lozinka = Sesija.logiraniDonator.Lozinka;
                    }
                    donator.GradId = ((bsp_Gradovi_SelectAll_Result) spinner.getSelectedItem()).GradId;
                    donator.KrvnaGrupa = s.getSelectedItem().toString();
                    //spol, aktivan, datum registracije
                    donator.DatumRegistracije = Sesija.logiraniDonator.DatumRegistracije;
                    if (chAktivan.isChecked()) {
                        donator.Aktivan = true;
                    } else {
                        donator.Aktivan = false;
                    }


                    RadioGroup rg = (RadioGroup) myView.findViewById(R.id.rgSpolEdit);
                    final String value =
                            ((RadioButton) myView.findViewById(rg.getCheckedRadioButtonId()))
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

                    DonatoriApi.IzmjenaPodataka(getActivity(), String.valueOf(donator.DonatorId), donator, new MyRunnable<Donatori>() {
                        @Override
                        public void run(Donatori result) {
                            Toast.makeText(MyApp.getContext(), "Uspjesno izmjenjeni podaci", Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }
        });



        return myView;
    }


    private void do_UcitajGradove() {
        GradoviApi.ProvjeraGrada(getActivity(), new MyRunnable<bsp_Gradovi_SelectAll_Result[]>() {
            @Override
            public void run(bsp_Gradovi_SelectAll_Result[] result) {
                if (result != null) {

                    spinner = (Spinner) myView.findViewById(R.id.spinnerGradEdit);
                    ArrayAdapter<bsp_Gradovi_SelectAll_Result> adapter = new ArrayAdapter<bsp_Gradovi_SelectAll_Result>(getActivity(),
                            android.R.layout.simple_spinner_dropdown_item, result);
                    spinner.setAdapter(adapter);
                    for (int i=0;i<result.length;i++)
                    {
                        if (result[i].GradId == Sesija.logiraniDonator.GradId)
                        {
                            spinner.setSelection(i);
                        }
                    }

                } else {
                    Toast.makeText(getActivity(), "Pogreška u komunikaciji", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    private Boolean do_validacija() {
        Boolean  ok = true;

        if (Validacija.isNullOrEmpty(txtIme.getText().toString()))
        {
            ok = false;
            txtIme.setBackground(getActivity().getResources().getDrawable(R.drawable.edittext_valdation));
        }
        else
        {
            txtIme.setBackground(getActivity().getResources().getDrawable(R.drawable.edittext_border));

        }

        if (Validacija.isNullOrEmpty(txtPrezime.getText().toString()))
        {
            ok = false;
            txtPrezime.setBackground(getActivity().getResources().getDrawable(R.drawable.edittext_valdation));
        }
        else
        {
            txtPrezime.setBackground(getActivity().getResources().getDrawable(R.drawable.edittext_border));

        }

        if (Validacija.isNullOrEmpty(txtAdresa.getText().toString()))
        {
            ok = false;
            txtAdresa.setBackground(getActivity().getResources().getDrawable(R.drawable.edittext_valdation));
        }
        else
        {
            txtAdresa.setBackground(getActivity().getResources().getDrawable(R.drawable.edittext_border));

        }

        if (Validacija.isNullOrEmpty(txtEmail.getText().toString()))
        {
            ok = false;
            txtEmail.setBackground(getActivity().getResources().getDrawable(R.drawable.edittext_valdation));
        }
        else
        {
            if (!Validacija.isValidEmail(txtEmail.getText().toString()))
            {
                ok = false;
                txtEmail.setBackground(getActivity().getResources().getDrawable(R.drawable.edittext_valdation));

            }
            else
            {
                txtEmail.setBackground(getActivity().getResources().getDrawable(R.drawable.edittext_border));
            }

        }


        if (Validacija.isNullOrEmpty(DatumRodenja.getText().toString()))
        {
            ok = false;
            DatumRodenja.setBackground(getActivity().getResources().getDrawable(R.drawable.edittext_valdation));
        }
        else
        {
            DatumRodenja.setBackground(getActivity().getResources().getDrawable(R.drawable.edittext_border));

        }


        if (Validacija.isNullOrEmpty(txtTelefon.getText().toString()))
        {
            ok = false;
            txtTelefon.setBackground(getActivity().getResources().getDrawable(R.drawable.edittext_valdation));
        }
        else
        {
            if (!Validacija.isNumeric(txtTelefon.getText().toString()) || (txtTelefon.getText().toString().length() < 9 ))
            {
                ok = false;
                txtTelefon.setBackground(getActivity().getResources().getDrawable(R.drawable.edittext_valdation));
                ((TextView)myView.findViewById(R.id.KontakTelefonEdit)).setText("Kontakt telefon (min 9 cifara)");


            }
            else {
                txtTelefon.setBackground(getActivity().getResources().getDrawable(R.drawable.edittext_border));
                ((TextView)myView.findViewById(R.id.KontakTelefonEdit)).setText("Kontakt telefon");

            }
        }




        if (!Validacija.isNullOrEmpty(txtLozinka.getText().toString()))
        {

            if (!Validacija.isValidPassword(txtLozinka.getText().toString(),false) )
            {
                ok = false;
                txtLozinka.setBackground(getActivity().getResources().getDrawable(R.drawable.edittext_valdation));
                ((TextView)myView.findViewById(R.id.PasswordEdit)).setText("Lozinka (min 6 karaktera)");
            }
            else {
                txtLozinka.setBackground(getActivity().getResources().getDrawable(R.drawable.edittext_border));
                ((TextView)myView.findViewById(R.id.PasswordEdit)).setText("Lozinka");

            }
        }

        return  ok;
    }

    }

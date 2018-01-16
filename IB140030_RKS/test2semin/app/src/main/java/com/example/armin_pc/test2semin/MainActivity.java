package com.example.armin_pc.test2semin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.armin_pc.test2semin.api.LoginApi;
import com.example.armin_pc.test2semin.helper.MyRunnable;
import com.example.armin_pc.test2semin.helper.Sesija;
import com.example.armin_pc.test2semin.helper.url_connection.MojApiRezultat;
import com.example.armin_pc.test2semin.model.bsp_Donatori_GetByEmailHCI_Result;
import com.example.armin_pc.test2semin.model.bsp_Donatori_GetByIdHCI_Result;

import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity {

    private EditText txtEmail;
    private  EditText txtPassword;
   // MojApiRezultat<bsp_Donatori_GetByEmailHCI_Result> rezultat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        txtEmail = (EditText)findViewById(R.id.editText2);
        txtPassword = (EditText)findViewById(R.id.editText3);

        Button  btn = (Button)findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(MainActivity.this,RegistracijaActivity.class);
                startActivity(intent);
            }
        });
        Button btn2 = (Button)findViewById(R.id.buttonLogin);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                do_getLogin();


            }
        });


    }

    private void do_getLogin() {

        LoginApi.Provjera(MainActivity.this,txtEmail.getText()
                .toString(),new MyRunnable<bsp_Donatori_GetByEmailHCI_Result>(){
            @Override
            public void run(bsp_Donatori_GetByEmailHCI_Result result) {
                if (result != null ) {

                    if (result.Lozinka.toString().equals(txtPassword.getText().toString())) {
                        Toast.makeText(MainActivity.this, "Uspjesno logiranje", Toast.LENGTH_LONG).show();
                        Sesija.logiraniDonator = result;

                        LoginApi.DodatniPodaci(MainActivity.this,String.valueOf(Sesija.logiraniDonator.DonatorId)
                                ,new MyRunnable<bsp_Donatori_GetByIdHCI_Result>(){
                            @Override
                            public void run(bsp_Donatori_GetByIdHCI_Result result) {
                                if (result != null ) {
                                        Sesija.logiraniDonatorPuniPodaci = result;
                                }
                                else
                                {
                                    Toast.makeText(MainActivity.this, "Pogreška u komunikaciji", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                        Intent intent = new Intent(MainActivity.this,NavigacijaActivity.class);
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(MainActivity.this, "Pogrešni login podaci", Toast.LENGTH_LONG).show();
                        txtEmail.setText("");
                        txtPassword.setText("");
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Pogreška u komunikaciji", Toast.LENGTH_LONG).show();
                }
            }
        });



    }


}

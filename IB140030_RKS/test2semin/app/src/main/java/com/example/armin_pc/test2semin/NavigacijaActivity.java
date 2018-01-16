package com.example.armin_pc.test2semin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.armin_pc.test2semin.helper.Sesija;

public class NavigacijaActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigacija);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header=navigationView.getHeaderView(0);
        ((TextView)header.findViewById(R.id.NavigacijaIme)).setText(Sesija.logiraniDonator.Ime + " " + Sesija.logiraniDonator.Prezime);
        navigationView.setNavigationItemSelectedListener(this);
        android.app.FragmentManager fManager = getFragmentManager();
        fManager.beginTransaction().replace(R.id.content_frame, new NoviZahtjevFragment()).commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigacija, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

      /*  if (id == R.id.action_settings) {
            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
       // FragmentManager fragmentManager = getFragmentManager();
        //FragmentManager fragmentManager = getFragmentManager();
        android.app.FragmentManager fragmentManager = getFragmentManager();
        if (id == R.id.nav_first_layout) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new PretragaDonatoraFragment()).commit();

        } else if (id == R.id.nav_second_layout) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new MojiPodaciFragment()).commit();

        } else if (id == R.id.nav_third_layout) {
            if (Sesija.logiraniDonator.Aktivan != false) {
                fragmentManager.beginTransaction().replace(R.id.content_frame, new MojeDonacijeFragment()).commit();
            }
            else
            {
                Toast.makeText(NavigacijaActivity.this, "Niste označeni kao donator", Toast.LENGTH_LONG).show();

            }

        } else if (id == R.id.nav_share) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new MojiZahtjeviFragment()).commit();
        } else if (id == R.id.nav_send) {
            Intent intent = new Intent(NavigacijaActivity.this,MainActivity.class);
            startActivity(intent);
            Sesija.logiraniDonator = null;
            Sesija.logiraniDonatorPuniPodaci = null;
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

package com.example.iit2014094.autotaskerapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.iit2014094.autotaskerapp.adapters.WifiRvAdapter;
import com.example.iit2014094.autotaskerapp.models.WifiLocations;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private WifiRvAdapter adapter;
    private Context context;
    private DatabaseHandler databaseHandler;
    private ArrayList<WifiLocations> wifiLocations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;
        databaseHandler = new DatabaseHandler(context);

        recyclerView = (RecyclerView) findViewById(R.id.wifiRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        wifiLocations = new ArrayList<>();
        wifiLocations.addAll(databaseHandler.getAllWifis());
        adapter = new WifiRvAdapter(wifiLocations,context);
        recyclerView.setAdapter(adapter);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View dialogView = View.inflate(context,R.layout.dialog_add_wifi, null);
                final AlertDialog alertDialog = new AlertDialog.Builder(context)
                        .setTitle(R.string.add_wifi_dialog_title)
                        .setView(dialogView)
 /*                               context.getResources().getDimensionPixelSize(R.dimen.spacing_left),
                                context.getResources().getDimensionPixelSize(R.dimen.spacing_top),
                                context.getResources().getDimensionPixelSize(R.dimen.spacing_right),
                                context.getResources().getDimensionPixelSize(R.dimen.spacing_bottom))*/
                        .setPositiveButton(R.string.add, null)
                        .setNegativeButton(R.string.cancel, null)
                        .create();
                final EditText etAddWifiName = (EditText)dialogView.findViewById(R.id.add_wifi_name);
                alertDialog.show();
                alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        databaseHandler.addWifi(new WifiLocations(etAddWifiName.getText().toString(),"temporary mac address"));
                        wifiLocations.clear();
                        wifiLocations.addAll(databaseHandler.getAllWifis());
                        adapter = new WifiRvAdapter(new ArrayList<WifiLocations>(databaseHandler.getAllWifis()),context);
                        adapter.notifyDataSetChanged();
                        recyclerView.smoothScrollToPosition(adapter.getItemCount());
                        alertDialog.cancel();
                    }
                });

            }
        }
        );

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

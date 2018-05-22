package com.esiea.tim.sharies;

import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private static RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d("testdebut", "onCreate: fonctionne");

        rv = (RecyclerView) findViewById(R.id.rv_series);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        rv.setAdapter(new SeriesAdapter(getSeriesFromFile()));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.content.IntentFilter intentFilter = new android.content.IntentFilter(MainActivity.SERIES_UPDATE);
        android.support.v4.content.LocalBroadcastManager.getInstance(this).registerReceiver(new SeriesUpdate(), intentFilter);

        GetSeriesServices.startActionget_all_series(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.menu_main, menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.bouttonprofile:
                startActivity(new Intent(this, ProfilActivity.class));
                return true;
            case R.id.buttoncredits:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.titrecredits);
                builder.setMessage(R.string.creditmessage);
                return true;
            case R.id.buttonparametres:
                startActivity(new Intent(this, classtest.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public static final String SERIES_UPDATE = "com.octip.cours.inf.SERIES_UPDATE";
    public class SeriesUpdate extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("oui", getIntent().getAction());


        }
    }

    public org.json.JSONArray getSeriesFromFile(){
        try{
            java.io.InputStream is = new java.io.FileInputStream(getCacheDir() + "/" +"series.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            return new org.json.JSONObject(new String(buffer, "UTF-8")).getJSONArray("data");

        }catch(IOException e){
            e.printStackTrace();
            return new JSONArray();
        }catch(JSONException e){
            e.printStackTrace();
            return new JSONArray();
        }

    }
}



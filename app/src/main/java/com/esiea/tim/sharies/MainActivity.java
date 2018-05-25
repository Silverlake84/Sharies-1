package com.esiea.tim.sharies;

import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.LocalBroadcastManager;
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
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements RecyclerViewClickListener{

    private static RecyclerView rv;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    public class SeriesUpdate extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(SERIES_UPDATE)) {
                Log.d("onReiceve", "update received");
                mAdapter = new SeriesAdapter(getSeriesFromFile());
                rv.setAdapter(mAdapter);
            }
        }
    }
    public static final String SERIES_UPDATE = "com.octip.cours.inf.SERIES_UPDATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d("test", "onCreate: debut");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter intentFilter = new IntentFilter(SERIES_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(new SeriesUpdate(),intentFilter);

        rv = (RecyclerView) findViewById(R.id.rv_series);

        rv.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(mLayoutManager);

        GetSeriesServices.startActionSeries(this);
        Log.d("test", "onCreate: fin ");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("test", "onCreateOptionsMenu: appel");
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

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
                builder.setNegativeButton(R.string.boutoncredits, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Does Nothing
                    }
                });
                builder.show();
                return true;

            case R.id.buttonparametres:
                startActivity(new Intent(this, ParametersActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public JSONArray getSeriesFromFile(){
        Log.d("test", "getSeriesFromFile: en  marche");
        try{
            InputStream is = new FileInputStream(getCacheDir() + "/" +"series.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            Log.d("test", "getSeriesFromFile "+ Arrays.toString(buffer));
            return new org.json.JSONObject(new String(buffer, "UTF-8")).getJSONArray("results");

        }catch(IOException e){
            e.printStackTrace();
            return new JSONArray();
        }catch(JSONException e){
            e.printStackTrace();
            return new JSONArray();
        }
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
    }

    public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.SeriesHolder>  {
        private JSONArray series;
        private Context context;
        private RecyclerViewClickListener itemListener;

        public class SeriesHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            public TextView name;

            public SeriesHolder(View itemView) {
                super(itemView);

                name = (TextView) itemView.findViewById(R.id.rv_series_element_name);
            }

            @Override
            public void onClick(View v) {

            }
        }

        public SeriesAdapter(JSONArray js){
            series = js;
            this.context = context;
            this.itemListener = itemListener;
        }


        @Override
        public SeriesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Log.d("test", "onCreateViewHolder: debut");

            LayoutInflater infl = LayoutInflater.from(parent.getContext());
            View view = infl.inflate(R.layout.rv_series_element, parent, false);

            Log.d("test", "onCreateViewHolder: fin");
            SeriesHolder sh = new SeriesHolder(view);
            return (sh);
        }

        @Override
        public void onBindViewHolder(SeriesHolder holder, int position) {
            Log.d("test", "onBindViewHolder: appel position :"+position);
            try {

                holder.name.setText(series.getJSONObject(position).getString("title"));

                Log.d("nameholder", "onBindViewHolder:" + holder.name);
            } catch (JSONException e) {
                Log.d("nameholder", "onBindViewHolder: bitch");
                e.printStackTrace();

            }

        }

        @Override
        public int getItemCount() {
            return series.length();
        }






    }

}
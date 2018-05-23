package com.esiea.tim.sharies;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.nfc.Tag;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class GetSeriesServices extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this

    private static final String ACTION_get_all_series = "com.esiea.tim.sheries.action.ACTION_get_all_series";


    public GetSeriesServices() {
        super("GetSeriesServices");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */


    public static void startActionSeries(Context context) {
        Log.d("test", "startActionSeries: debut");
        Intent intent = new Intent(context, GetSeriesServices.class);
        intent.setAction(ACTION_get_all_series);

        context.startService(intent);
        Log.d("test", "startActionSeries: fin");
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_get_all_series.equals(action)) {

                handleActionSeries();
            }
        }
    }



    private void handleActionSeries(){
        Log.d("handleActionService", "Thread service name:"+ Thread.currentThread().getName());
        URL url = null;
        try

        {
            url = new URL("http://files.tmdb.org/p/exports/movie_ids_04_28_2017.json.gz");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            if (HttpsURLConnection.HTTP_OK == conn.getResponseCode()) {
                copyInputStreamToFile(conn.getInputStream(),
                        new File(getCacheDir(), "series.json"));
                Log.d("handleActionSeries", "Series json downloaded !");

            }
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(MainActivity.SERIES_UPDATE));
    }
    private void copyInputStreamToFile(InputStream in, File file){
        Log.d("test", "copyInputStreamToFile: debut");
        try{
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while((len=in.read(buf))>0){
                out.write(buf,0,len);
            }
            out.close();
            in.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        Log.d("test", "copyInputStreamToFile: fin");
    }

}



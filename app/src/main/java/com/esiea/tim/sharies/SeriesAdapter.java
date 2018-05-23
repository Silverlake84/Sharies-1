package com.esiea.tim.sharies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import junit.framework.Test;

import org.json.JSONArray;
import org.json.JSONException;

import static android.content.ContentValues.TAG;

class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.SeriesHolder>  {

    private JSONArray series;


    public SeriesAdapter(JSONArray js){
        this.series = js;
    }


    @Override
    public SeriesHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        Log.d("test", "onCreateViewHolder: debut");

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(android.R.layout.activity_list_item, parent, false);
        Log.d("test", "onCreateViewHolder: fin");
        return (new SeriesHolder(view));

    }

    @Override
    public void onBindViewHolder(SeriesHolder holder, int position) {
        Log.d("test", "onBindViewHolder: debut");
        try {
            series.getJSONObject(position);
            series.getString(position);
            //holder.name.setText(series.get(position).getString("name"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("test", "onBindViewHolder: fin");
    }

    @Override
    public int getItemCount() {
        return series.length();
    }

    public void setNewSerie(JSONArray jsar){
        notifyDataSetChanged();
    }

    class SeriesHolder extends  RecyclerView.ViewHolder{

        public TextView name;

        public SeriesHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.rv_series_element_name);
        }
    }


}

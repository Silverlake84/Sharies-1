package com.esiea.tim.sharies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import junit.framework.Test;

import org.json.JSONArray;
import org.json.JSONException;

class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.SeriesHolder>  {

    private JSONArray series;


    public SeriesAdapter(JSONArray js){
        this.series = js;
    }


    @Override
    public SeriesHolder onCreateViewHolder( ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(android.R.layout.activity_list_item, parent, false);

        return new SeriesHolder(view);

    }

    @Override
    public void onBindViewHolder(SeriesHolder holder, int position) {
        try {
            series.getJSONObject(position);
            series.getString(position);
            holder.name.setText(series.getString(position));

        } catch (JSONException e) {
            e.printStackTrace();
        }
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

            name = (TextView) itemView.findViewById(R.id.rv_series);
        }
    }


}

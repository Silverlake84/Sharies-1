package com.esiea.tim.sharies;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class FilmActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);

        String title = getIntent().getExtras().getString("title");
        double vote_average = getIntent().getExtras().getDouble("vote_average");
        String poster_path = getIntent().getExtras().getString("poster_path");
        String overview = getIntent().getExtras().getString("overview");
        String release_date = getIntent().getExtras().getString("release_date");

        ImageView image = findViewById(R.id.affiche);
        TextView titre = findViewById(R.id.titre);
        TextView description = findViewById(R.id.description);
        TextView datedesortie = findViewById(R.id.datedesortie);
        TextView note = findViewById(R.id.note);

        Picasso.with(this).load("https://image.tmdb.org/t/p/w500"+poster_path).into(image);
        titre.setText(title);
        description.setText(overview);
        datedesortie.setText(release_date);
        note.setText(String.valueOf(vote_average));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("test", "onCreateOptionsMenu: appel");
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_film, menu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

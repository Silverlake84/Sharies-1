package com.esiea.tim.sharies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class FilmActivity extends AppCompatActivity {

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);

        double vote_average = getIntent().getExtras().getDouble("vote_average");

        String title = getIntent().getExtras().getString("title");
        String poster_path = getIntent().getExtras().getString("poster_path");
        String overview = getIntent().getExtras().getString("overview");
        String release_date = getIntent().getExtras().getString("release_date");
        String backdrop_path = getIntent().getExtras().getString("backdrop_path");

        this.id = String.valueOf(getIntent().getExtras().getInt("id"));


        ImageView image = findViewById(R.id.affiche);

        TextView titre = findViewById(R.id.titre);
        TextView description = findViewById(R.id.description);
        TextView datedesortie = findViewById(R.id.datedesortie);
        TextView note = findViewById(R.id.note);

        ImageButton imageButton1 = findViewById(R.id.image_dl1);
        ImageButton imageButton2 = findViewById(R.id.image_dl2);

        Picasso.with(this).load("https://image.tmdb.org/t/p/w500"+poster_path).into(image);
        titre.setText(title);
        description.setText(overview);
        description.setMovementMethod(new ScrollingMovementMethod());
        datedesortie.setText(release_date);
        note.setText(String.valueOf(vote_average));

        Picasso.with(this).load("https://image.tmdb.org/t/p/w500"+poster_path).into(imageButton1);
        Picasso.with(this).load("https://image.tmdb.org/t/p/w500"+backdrop_path).into(imageButton2);

        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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
            case R.id.buttoninfo:
                Uri webpage = Uri.parse("https://www.themoviedb.org/movie/"+ id);
                Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(webIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

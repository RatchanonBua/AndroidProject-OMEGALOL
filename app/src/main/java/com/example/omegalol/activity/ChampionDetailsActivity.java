package com.example.omegalol.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.omegalol.R;
import com.example.omegalol.view_object.ChampionDetailsObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Objects;

public class ChampionDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_champion_detail);

        ChampionDetailsObject object = new ChampionDetailsObject(this);
        object.setChampionViewObject();
        generateChampionDetails(object);
    }

    private void generateChampionDetails(ChampionDetailsObject object) {
        try {
            JSONObject champion = new JSONObject(Objects.requireNonNull(getIntent().getStringExtra("Champion")));
            object.setChampionTextDetails(champion);
            object.setChampionImageObject(champion);
            object.setChampionTipsButton(champion);
            object.setChampionSpellsToolTip(champion);
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            Log.i("ErrorDetails", sw.toString());
            finish();
        }
    }
}

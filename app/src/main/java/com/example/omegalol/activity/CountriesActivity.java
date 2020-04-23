package com.example.omegalol.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Toast;

import com.example.omegalol.R;
import com.example.omegalol.view_object.CountriesView;

public class CountriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_countries);
        generateCountriesFlag();
    }

    private void generateCountriesFlag() {
        try {
            CountriesView viewObject = new CountriesView(this);
            viewObject.setCountriesFlag();
        } catch (Exception e) {
            Toast.makeText(this, "The error occurs during load data.", Toast.LENGTH_LONG).show();
            finish();
        }
    }
}

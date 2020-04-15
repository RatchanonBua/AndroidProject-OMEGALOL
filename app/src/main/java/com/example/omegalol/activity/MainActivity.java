package com.example.omegalol.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.widget.Toast;
import com.example.omegalol.R;

public class MainActivity extends AppCompatActivity {
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press back again to exit.", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() { doubleBackToExitPressedOnce = false; }
        }, 2000);
    }

    public void onPressMatchlistButton(final View view) {
//        Toast.makeText(this, "Please Login with GarenaID", Toast.LENGTH_SHORT).show();
        view.setClickable(false);
        Intent intent = new Intent(this, MatchlistActivity.class);
        intent.putExtra("account", "Faltzner");
        intent.putExtra("uid", "497041707");
        startActivity(intent);
        view.postDelayed(new Runnable() {
            @Override
            public void run() { view.setClickable(true); }
        }, 500);
    }

    public void onPressTournamentsButton(View view) {
        Intent intent = new Intent(this, TournamentsActivity.class);
        startActivity(intent);
    }

    public void onPressChampionsButton(final View view) {
        view.setClickable(false);
        Intent intent = new Intent(this, ChampionsActivity.class);
        startActivity(intent);
        view.postDelayed(new Runnable() {
            @Override
            public void run() { view.setClickable(true); }
        }, 500);
    }

    public void onPressRunesButton(View view) {
        Intent intent = new Intent(this, RunesActivity.class);
        startActivity(intent);
    }

    public void onPressItemsButton(final View view) {
        view.setClickable(false);
        Intent intent = new Intent(this, ItemsActivity.class);
        startActivity(intent);
        view.postDelayed(new Runnable() {
            @Override
            public void run() { view.setClickable(true); }
        }, 500);
    }

    public void onPressMapsButton(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}

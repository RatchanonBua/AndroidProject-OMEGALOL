package com.example.omegalol;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
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
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    public void onPressMatchHistoryButton(View view) {
        Toast.makeText(this, "Please Login with GarenaID", Toast.LENGTH_SHORT).show();
    }

    public void onPressTournamentsButton(View view) {
        Toast.makeText(this, "Tournaments function is coming soon.", Toast.LENGTH_SHORT).show();
    }

    public void onPressChampionsButton(View view) {
        Toast.makeText(this, "Champions function is coming soon.", Toast.LENGTH_SHORT).show();
    }

    public void onPressRunesButton(View view) {
        Toast.makeText(this, "Runes function is coming soon.", Toast.LENGTH_SHORT).show();
    }

    public void onPressItemsButton(View view) {
        Toast.makeText(this, "Items function is coming soon.", Toast.LENGTH_SHORT).show();
    }

    public void onPressMapsButton(View view) {
        Toast.makeText(this, "Maps function is coming soon.", Toast.LENGTH_SHORT).show();
    }
}

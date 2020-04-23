package com.example.omegalol.view_object;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.omegalol.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CountriesView {
    private final Activity activity;
    private ArrayList<ImageView> imageViews;

    public CountriesView(Activity activity) {
        this.activity = activity;
        this.imageViews = new ArrayList<>();
    }

    public void setCountriesFlag() {
        imageViews = getImageViewInLayout();
        setFlagToImageView(imageViews);
    }

    private ArrayList<ImageView> getImageViewInLayout() {
        ArrayList<ImageView> result = new ArrayList<>();
        LinearLayout layout = activity.findViewById(R.id.countries_flag);
        for (int i = 0; i < layout.getChildCount(); i++) {
            View subView = layout.getChildAt(i);
            if (subView instanceof LinearLayout) {
                LinearLayout rowView = (LinearLayout) subView;
                for (int j = 0; j < rowView.getChildCount(); j++) {
                    View targetView = rowView.getChildAt(j);
                    if (targetView instanceof ImageView) {
                        result.add((ImageView) targetView);
                    }
                }
            }
        }
        return result;
    }

    private void setFlagToImageView(ArrayList<ImageView> list) {
        Resources res = activity.getResources();
        @SuppressLint("Recycle")
        TypedArray typedArray = res.obtainTypedArray(R.array.country_code);
        for (int index = 0; index < typedArray.length() && index < list.size(); index++) {
            int id = typedArray.getResourceId(index, 0);
            loadFlagImageUri(res.getStringArray(id), list.get(index));
            setOnClickFlag(res.getStringArray(id), list.get(index));
        }
    }

    private void loadFlagImageUri(String[] countryObject, ImageView imageView) {
        String uri = String.format(activity.getString(R.string.flag_image_uri), countryObject[0].toLowerCase());
        Picasso.get().load(uri).into(imageView);
    }

    private void setOnClickFlag(final String[] countryObject, ImageView imageView) {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = createBuilderForDialog(countryObject).create();
                dialog.show();
            }
        });
    }

    private AlertDialog.Builder createBuilderForDialog(final String[] countryObject) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(String.format("%s Language", countryObject[2]));
        builder.setMessage(String.format("%s", activity.getString(R.string.dialog_build)));
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                SharedPreferences preferences = activity.getSharedPreferences("OMEGALOL", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("language", countryObject[1]);
                editor.apply();
                Toast.makeText(activity, String.format("Change language to %s"
                        , countryObject[2]), Toast.LENGTH_SHORT).show();
                activity.finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) { dialog.cancel(); }
        });
        return builder;
    }
}

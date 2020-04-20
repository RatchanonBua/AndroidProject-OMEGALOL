package com.example.omegalol.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omegalol.R;
import com.example.omegalol.view_object.RuneObject;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RunesAdapter extends RecyclerView.Adapter<RunesAdapter.RunesHolder> {
    private ArrayList<JSONObject> dataset;
    private Context context;

    public RunesAdapter(Context context, ArrayList<JSONObject> runeList) {
        this.dataset = runeList;
        this.context = context;
    }

    @NonNull
    @Override
    public RunesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.layout_rune, parent, false);
        return new RunesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RunesHolder holder, int position) {
        try {
            holder.jsonObject = dataset.get(position);
            holder.rune_name.setText(getTypeName(position));
            holder.setSlotItemView();
            Picasso.get().load(generateUri(getImageName(position))).into(holder.rune_img);
        } catch (Exception e) {
            holder.rune_img.setImageResource(R.drawable.app_unknown);
        }
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    private String getTypeName(int position) throws JSONException {
        JSONObject nameObj = dataset.get(position);
        return nameObj.get("name").toString();
    }

    private String getImageName(int position) throws JSONException {
        JSONObject imageObj = dataset.get(position);
        return imageObj.get("icon").toString();
    }

    private String generateUri(String image) {
        String baseUri = context.getString(R.string.ddragon_uri);
        return String.format("%s%s%s", baseUri, "cdn/img/", image);
    }

    class RunesHolder extends RecyclerView.ViewHolder {
        ImageView rune_img;
        TextView rune_name;
        JSONObject jsonObject;
        RuneObject viewObject;

        RunesHolder(View itemView) {
            super(itemView);
            rune_img = itemView.findViewById(R.id.rune_image);
            rune_name = itemView.findViewById(R.id.rune_name);
        }

        private void setSlotItemView() {
            viewObject = new RuneObject(context, itemView, jsonObject);
            viewObject.setFirstSlotItemView();
            viewObject.setSecondSlotItemView();
            viewObject.setThirdSlotItemView();
            viewObject.setFourthSlotItemView();
        }
    }
}

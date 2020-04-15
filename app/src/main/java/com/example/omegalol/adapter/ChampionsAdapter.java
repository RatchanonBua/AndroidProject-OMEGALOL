package com.example.omegalol.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omegalol.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChampionsAdapter extends RecyclerView.Adapter<ChampionsAdapter.ChampionsHolder> {
    private ArrayList<JSONObject> dataset;
    private Context context;

    public ChampionsAdapter(Context context, ArrayList<JSONObject> championList) {
        this.dataset = championList;
        this.context = context;
    }

    @NonNull
    @Override
    public ChampionsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.layout_champion, parent, false);
        return new ChampionsHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ChampionsHolder holder, int position) {
        try {
            holder.jsonObject = dataset.get(position);
            setChampionText(holder, getChampionName(position), getChampionTitle(position));
            Picasso.get().load(generateUri(getImageName(position))).into(holder.champion_img);
        } catch (Exception e) {
            holder.champion_img.setImageResource(R.drawable.app_unknown);
        }
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    private void setChampionText(ChampionsHolder holder, String name, String title) {
        holder.champion_name.setText(name);
        holder.champion_title.setText(title);
    }

    private String getChampionName(int position) throws JSONException {
        JSONObject nameObj = dataset.get(position);
        return nameObj.get("name").toString();
    }

    private String getChampionTitle(int position) throws JSONException {
        JSONObject nameObj = dataset.get(position);
        return nameObj.get("title").toString();
    }

    private String getImageName(int position) throws JSONException {
        JSONObject nameObj = dataset.get(position);
        JSONObject imageObj = new JSONObject(nameObj.get("image").toString());
        return imageObj.get("full").toString();
    }

    private String generateUri(String image) {
        String baseUri = context.getString(R.string.ddragon_uri);
        String version = context.getString(R.string.version);
        return String.format("%s%s%s%s%s", baseUri, "cdn/", version, "/img/champion/", image);
    }

    class ChampionsHolder extends RecyclerView.ViewHolder {
        ImageView champion_img;
        TextView champion_name;
        TextView champion_title;
        JSONObject jsonObject;

        ChampionsHolder(View itemView) {
            super(itemView);
            champion_img = itemView.findViewById(R.id.champion_img);
            champion_name = itemView.findViewById(R.id.champion_name);
            champion_title = itemView.findViewById(R.id.champion_title);
        }
    }
}

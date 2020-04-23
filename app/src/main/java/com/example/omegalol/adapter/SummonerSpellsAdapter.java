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
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SummonerSpellsAdapter extends RecyclerView.Adapter<SummonerSpellsAdapter.SummonerSpellsHolder> {
    private final ArrayList<JSONObject> dataset;
    private final Context context;

    public SummonerSpellsAdapter(Context context, ArrayList<JSONObject> summonerSpellList) {
        this.dataset = summonerSpellList;
        this.context = context;
    }

    @NonNull
    @Override
    public SummonerSpellsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.layout_summoner_spell, parent, false);
        return new SummonerSpellsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SummonerSpellsHolder holder, int position) {
        try {
            setSummonerSpellText(holder, position);
            Picasso.get().load(generateUri(getImageName(position))).into(holder.smnspell_img);
        } catch (Exception e) {
            holder.smnspell_img.setImageResource(R.drawable.app_unknown);
        }
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    private void setSummonerSpellText(SummonerSpellsHolder holder, int position) throws Exception {
        holder.smnspell_name.setText(getSummonerSpellDetails(position, "name"));
        holder.smnspell_cd.setText(String.format("Cooldown: %s seconds"
                , getSummonerSpellDetails(position, "cooldownBurn")));
        holder.smnspell_level.setText(getSummonerSpellDetails(position, "summonerLevel"));
        holder.smnspell_desc.setText(getSummonerSpellDetails(position, "description"));
    }

    private String getSummonerSpellDetails(int position, String key) throws JSONException {
        JSONObject targetObj = dataset.get(position);
        return targetObj.get(key).toString();
    }

    private String getImageName(int position) throws JSONException {
        JSONObject nameObj = dataset.get(position);
        JSONObject imageObj = new JSONObject(nameObj.get("image").toString());
        return imageObj.getString("full");
    }

    private String generateUri(String image) {
        String baseUri = context.getString(R.string.ddragon_uri);
        String version = context.getString(R.string.version);
        return String.format("%s%s%s%s%s", baseUri, "cdn/", version, "/img/spell/", image);
    }

    class SummonerSpellsHolder extends RecyclerView.ViewHolder {
        final ImageView smnspell_img;
        final TextView smnspell_name, smnspell_cd, smnspell_level, smnspell_desc;

        SummonerSpellsHolder(View itemView) {
            super(itemView);
            smnspell_img = itemView.findViewById(R.id.smnspell_img);
            smnspell_name = itemView.findViewById(R.id.smnspell_name);
            smnspell_cd = itemView.findViewById(R.id.smnspell_cd);
            smnspell_level = itemView.findViewById(R.id.smnspell_level);
            smnspell_desc = itemView.findViewById(R.id.smnspell_desc);
        }
    }
}

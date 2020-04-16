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

public class MapsAdapter extends RecyclerView.Adapter<MapsAdapter.MapsHolder> {
    private ArrayList<JSONObject> dataset;
    private Context context;

    public MapsAdapter(Context context, ArrayList<JSONObject> mapsList) {
        this.dataset = mapsList;
        this.context = context;
    }

    @NonNull
    @Override
    public MapsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.layout_map, parent, false);
        return new MapsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MapsHolder holder, int position) {
        try {
            holder.jsonObject = dataset.get(position);
            holder.map_name.setText(getMapName(position));
            Picasso.get().load(generateUri(getImageName(position))).into(holder.map_img);
        } catch (Exception ignored) {}
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    private String getMapName(int position) throws JSONException {
        JSONObject nameObj = dataset.get(position);
        return nameObj.get("MapName").toString();
    }

    private String getImageName(int position) throws JSONException {
        JSONObject nameObj = dataset.get(position);
        JSONObject imageObj = new JSONObject(nameObj.get("image").toString());
        return imageObj.get("full").toString();
    }

    private String generateUri(String image) {
        String baseUri = context.getString(R.string.ddragon_uri);
        String version = context.getString(R.string.version);
        return String.format("%s%s%s%s%s", baseUri, "cdn/", version, "/img/map/", image);
    }

    class MapsHolder extends RecyclerView.ViewHolder {
        ImageView map_img;
        TextView map_name;
        JSONObject jsonObject;

        MapsHolder(View itemView) {
            super(itemView);
            map_img = itemView.findViewById(R.id.map_img);
            map_name = itemView.findViewById(R.id.map_name);
        }
    }
}

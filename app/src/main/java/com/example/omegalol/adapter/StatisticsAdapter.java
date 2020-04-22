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

import net.rithms.riot.api.endpoints.match.dto.MatchReference;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

public class StatisticsAdapter extends RecyclerView.Adapter<StatisticsAdapter.StatisticsHolder> {
    private ArrayList<MatchReference> dataset;
    private Map<Integer, JSONObject> championKeys;
    private Context context;

    public StatisticsAdapter(Context context, ArrayList<MatchReference> dataset, Map<Integer, JSONObject> keyList) {
        this.dataset = dataset;
        this.championKeys = keyList;
        this.context = context;
    }

    @NonNull
    @Override
    public StatisticsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.layout_statistics, parent, false);
        return new StatisticsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatisticsHolder holder, int position) {
        try {
            setMatchText(holder, getChampionName(position), getGameID(position), getTimestamp(position));
            Picasso.get().load(generateUri(getImageName(position))).into(holder.match_img);
        } catch (Exception e) {
            holder.match_img.setImageResource(R.drawable.app_unknown);
        }
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    private void setMatchText(StatisticsHolder holder, String championName, String matchID, String matchTime) {
        holder.match_champion.setText(championName);
        holder.match_id.setText(matchID);
        holder.match_time.setText(matchTime);
    }

    private String getChampionName(int position) throws JSONException {
        int championID = dataset.get(position).getChampion();
        JSONObject championObj = championKeys.get(championID);
        assert championObj != null;
        return championObj.getString("name");
    }

    private String getGameID(int position) {
        return String.format("%s", dataset.get(position).getGameId());
    }

    @SuppressLint("SimpleDateFormat")
    private String getTimestamp(int position) {
        long timestamp = dataset.get(position).getTimestamp();
        Date date = new Date(timestamp);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return df.format(date);
    }

    private String getImageName(int position) throws Exception {
        int championID = dataset.get(position).getChampion();
        JSONObject championObj = new JSONObject(Objects.requireNonNull(championKeys.get(championID)).toString());
        JSONObject imageObj = new JSONObject(championObj.get("image").toString());
        return imageObj.get("full").toString();
    }

    private String generateUri(String image) {
        String baseUri = context.getString(R.string.ddragon_uri);
        String version = context.getString(R.string.version);
        return String.format("%s%s%s%s%s", baseUri, "cdn/", version, "/img/champion/", image);
    }

    class StatisticsHolder extends RecyclerView.ViewHolder {
        ImageView match_img;
        TextView match_champion, match_id, match_time;

        StatisticsHolder(View itemView) {
            super(itemView);
            match_img = itemView.findViewById(R.id.match_img);
            match_champion = itemView.findViewById(R.id.match_champion);
            match_id = itemView.findViewById(R.id.match_id);
            match_time = itemView.findViewById(R.id.match_time);
        }
    }
}

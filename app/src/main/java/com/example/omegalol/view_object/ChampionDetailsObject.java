package com.example.omegalol.view_object;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.omegalol.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class ChampionDetailsObject {
    private Activity activity;
    // Champion Details
    private ImageView chdetails_img;
    private TextView chdetails_name, chdetails_title, chdetails_tag, chdetails_story;
    // Spell Details
    private ImageView passive_btn, first_spell, second_spell, third_spell, forth_spell;
    private Button ally_tips, enemy_tips;

    public ChampionDetailsObject(Activity activity) {
        this.activity = activity;
    }

    public void setChampionViewObject() {
        chdetails_img = activity.findViewById(R.id.chdetails_img);
        chdetails_name = activity.findViewById(R.id.chdetails_name);
        chdetails_title = activity.findViewById(R.id.chdetails_title);
        chdetails_tag = activity.findViewById(R.id.chdetails_tag);
        chdetails_story = activity.findViewById(R.id.chdetails_story);

        passive_btn = activity.findViewById(R.id.passive_btn);
        first_spell = activity.findViewById(R.id.first_spell);
        second_spell = activity.findViewById(R.id.second_spell);
        third_spell = activity.findViewById(R.id.third_spell);
        forth_spell = activity.findViewById(R.id.forth_spell);

        ally_tips = activity.findViewById(R.id.ally_tips);
        enemy_tips = activity.findViewById(R.id.enemy_tips);
    }

    public void setChampionTextDetails(JSONObject json) throws Exception {
        chdetails_name.setText(json.get("name").toString());
        chdetails_title.setText(String.format("(%s)", json.get("title").toString()));
        chdetails_tag.setText(activity.getIntent().getStringExtra("Tag"));
        chdetails_story.setText(String.format("\t\t\t\t%s", json.get("lore").toString()));
    }

    public void setChampionImageObject(JSONObject json) throws Exception {
        Picasso.get().load(generateChampionUri(json.get("id").toString())).into(chdetails_img);
        Picasso.get().load(generateImageButtonUri(json, 0, "passive")).into(passive_btn);
        Picasso.get().load(generateImageButtonUri(json, 0, "spell")).into(first_spell);
        Picasso.get().load(generateImageButtonUri(json, 1, "spell")).into(second_spell);
        Picasso.get().load(generateImageButtonUri(json, 2, "spell")).into(third_spell);
        Picasso.get().load(generateImageButtonUri(json, 3, "spell")).into(forth_spell);
    }

    public void setChampionTipsButton(final JSONObject json) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        ally_tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    builder.setTitle("Ally Tips");
                    builder.setMessage(showDialogMessage(json, "allytips"));
                    changeDialogMessageSize(builder);
                } catch (JSONException ignored) {}
            }
        });
        enemy_tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    builder.setTitle("Enemy Tips");
                    builder.setMessage(showDialogMessage(json, "enemytips"));
                    changeDialogMessageSize(builder);
                } catch (JSONException ignored) {}
            }
        });
    }

    public void setChampionSpellsToolTip(JSONObject json) {
        passive_btn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(activity, "Passive Skill", Toast.LENGTH_LONG).show();
                return false;
            }
        });
        first_spell.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(activity, "First Spell", Toast.LENGTH_LONG).show();
                return false;
            }
        });
        second_spell.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(activity, "Second Spell", Toast.LENGTH_LONG).show();
                return false;
            }
        });
        third_spell.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(activity, "Third Spell", Toast.LENGTH_LONG).show();
                return false;
            }
        });
        forth_spell.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(activity, "Forth Spell", Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }

    private String showDialogMessage(JSONObject json, String key) throws JSONException {
        String tipStr = "";
        JSONArray tipArr = new JSONArray(json.get(key).toString());
        for (int i = 0; i < tipArr.length(); i++) {
            tipStr = tipStr + "\u2022 " + tipArr.get(i).toString();
            tipStr = (i < tipArr.length() - 1)? tipStr + "\n\n" : tipStr;
        }
        return tipStr;
    }

    private void changeDialogMessageSize(AlertDialog.Builder builder) {
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Objects.requireNonNull(alertDialog.getWindow()).getAttributes();

        TextView message = alertDialog.findViewById(android.R.id.message);
        assert message != null;
        message.setTextSize(11);
    }

    private String generateChampionUri(String image) {
        String baseUri = activity.getString(R.string.lolrift_uri);
        return String.format("%s%s%s%s", baseUri, "img/champion/loading/", image, "_0.jpg");
    }

    private String generateImageButtonUri(JSONObject json, int index, String type) throws Exception {
        String imageName;
        JSONObject targetObj, imgObject;
        if (type.equals("passive")) {
            targetObj = new JSONObject(json.get("passive").toString());
        } else {
            JSONArray spellArr = new JSONArray(json.get("spells").toString());
            targetObj = new JSONObject(spellArr.get(index).toString());
        }
        imgObject = new JSONObject(targetObj.get("image").toString());
        imageName = imgObject.get("full").toString();

        String baseUri = activity.getString(R.string.ddragon_uri);
        String version = activity.getString(R.string.version);
        return String.format("%scdn/%s/%s/%s/%s", baseUri, version, "img", type, imageName);
    }
}

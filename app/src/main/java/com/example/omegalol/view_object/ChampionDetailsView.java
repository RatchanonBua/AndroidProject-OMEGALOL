package com.example.omegalol.view_object;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.omegalol.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class ChampionDetailsView {
    private Activity activity;
    private ImageView chdetails_img;
    private TextView chdetails_name, chdetails_title, chdetails_tag, chdetails_story;
    private ImageView passive_btn, first_spell, second_spell, third_spell, fourth_spell;
    private Button ally_tips, enemy_tips;

    public ChampionDetailsView(Activity activity) {
        this.activity = activity;
    }

    public void setChampionViewObject() {
        setChampionsMainDetailsViewObject();
        setChampionsSpellImageViewObject();
        setChampionsTipDetailsViewObject();
    }

    private void setChampionsMainDetailsViewObject() {
        chdetails_img = activity.findViewById(R.id.chdetails_img);
        chdetails_name = activity.findViewById(R.id.chdetails_name);
        chdetails_title = activity.findViewById(R.id.chdetails_title);
        chdetails_tag = activity.findViewById(R.id.chdetails_tag);
        chdetails_story = activity.findViewById(R.id.chdetails_story);
    }

    private void setChampionsSpellImageViewObject() {
        passive_btn = activity.findViewById(R.id.passive_spell);
        first_spell = activity.findViewById(R.id.first_spell);
        second_spell = activity.findViewById(R.id.second_spell);
        third_spell = activity.findViewById(R.id.third_spell);
        fourth_spell = activity.findViewById(R.id.fourth_spell);
    }

    private void setChampionsTipDetailsViewObject() {
        ally_tips = activity.findViewById(R.id.ally_tips);
        enemy_tips = activity.findViewById(R.id.enemy_tips);
    }

    public void setChampionMainDetails(JSONObject json) throws Exception {
        chdetails_name.setText(json.getString("name"));
        chdetails_title.setText(String.format("(%s)", json.getString("title")));
        chdetails_tag.setText(activity.getIntent().getStringExtra("Tag"));
        chdetails_story.setText(String.format("\t\t\t\t%s", json.getString("lore")));
    }

    public void setChampionImageObject(final JSONObject json) throws Exception {
        Picasso.get().load(generateChampionImageUri(json.getString("id"), "small")).into(chdetails_img);
        Picasso.get().load(generateSkillImageUri(json, 0, "passive")).into(passive_btn);
        Picasso.get().load(generateSkillImageUri(json, 0, "spell")).into(first_spell);
        Picasso.get().load(generateSkillImageUri(json, 1, "spell")).into(second_spell);
        Picasso.get().load(generateSkillImageUri(json, 2, "spell")).into(third_spell);
        Picasso.get().load(generateSkillImageUri(json, 3, "spell")).into(fourth_spell);

        chdetails_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Dialog builder = new Dialog(activity);
                    builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    Objects.requireNonNull(builder.getWindow())
                            .setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    ImageView imageView = new ImageView(activity);
                    Picasso.get().load(generateChampionImageUri(
                            json.getString("id"), "full")).into(imageView);
                    builder.setContentView(imageView, new RelativeLayout.LayoutParams(616, 1120));
                    builder.show();
                } catch (Exception ignored) {}
            }
        });
    }

    public void setChampionTipsButton(final JSONObject json) {
        ally_tips.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                try {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    TextView title = changeDialogTitleSize(new TextView(activity.getApplicationContext()));
                    title.setText("Ally Tips");
                    builder.setCustomTitle(title);
                    builder.setMessage(showDialogMessage(json, "allytips"));
                    changeDialogMessageSize(builder);
                } catch (JSONException ignored) {}
            }
        });
        enemy_tips.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                try {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    TextView title = changeDialogTitleSize(new TextView(activity.getApplicationContext()));
                    title.setText("Enemy Tips");
                    builder.setCustomTitle(title);
                    builder.setMessage(showDialogMessage(json, "enemytips"));
                    changeDialogMessageSize(builder);
                } catch (JSONException ignored) {}
            }
        });
    }

    private String showDialogMessage(JSONObject json, String key) throws JSONException {
        String tipStr = "";
        JSONArray tipArr = new JSONArray(json.get(key).toString());
        for (int i = 0; i < tipArr.length(); i++) {
            tipStr = tipStr + "\u2022 " + tipArr.getString(i);
            tipStr = (i < tipArr.length() - 1)? tipStr + "\n\n" : tipStr;
        }
        return tipStr;
    }

    private TextView changeDialogTitleSize(TextView textView) {
        textView.setTextSize(16);
        textView.setHeight(120);
        textView.setPadding(0, 30, 0, 0);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    private void changeDialogMessageSize(AlertDialog.Builder builder) {
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Objects.requireNonNull(alertDialog.getWindow()).getAttributes();

        TextView message = alertDialog.findViewById(android.R.id.message);
        assert message != null;
        message.setTextSize(11);
    }

    private String generateChampionImageUri(String image, String type) {
        if (type.equals("full")) {
            String baseUri = activity.getString(R.string.lolrift_uri);
            return String.format("%s%s%s_0.jpg", baseUri, "img/champion/loading/", image);
        } else {
            String baseUri = activity.getString(R.string.ddragon_uri);
            String version = activity.getString(R.string.version);
            return String.format("%s%s%s%s%s.png", baseUri, "cdn/", version, "/img/champion/", image);
        }
    }

    private String generateSkillImageUri(JSONObject json, int index, String type) throws Exception {
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

    public void setSkillDetailsView(JSONObject json) {
        TextView passiveTitle = activity.findViewById(R.id.passive_title);
        TextView passiveDesc = activity.findViewById(R.id.passive_desc);
        generateSpellDetailsFromJSON(json, new TextView[]{passiveTitle, passiveDesc},
                "passive", 0);

        TextView firstSpellTitle = activity.findViewById(R.id.spell_title_first);
        TextView firstSpellDesc = activity.findViewById(R.id.spell_desc_first);
        TextView firstSpellCD = activity.findViewById(R.id.spell_cd_first);
        generateSpellDetailsFromJSON(json, new TextView[]{firstSpellTitle, firstSpellDesc, firstSpellCD},
                "spell", 0);

        TextView secondSpellTitle = activity.findViewById(R.id.spell_title_second);
        TextView secondSpellDesc = activity.findViewById(R.id.spell_desc_second);
        TextView secondSpellCD = activity.findViewById(R.id.spell_cd_second);
        generateSpellDetailsFromJSON(json, new TextView[]{secondSpellTitle, secondSpellDesc, secondSpellCD},
                "spell", 1);

        TextView thirdSpellTitle = activity.findViewById(R.id.spell_title_third);
        TextView thirdSpellDesc = activity.findViewById(R.id.spell_desc_third);
        TextView thirdSpellCD = activity.findViewById(R.id.spell_cd_third);
        generateSpellDetailsFromJSON(json, new TextView[]{thirdSpellTitle, thirdSpellDesc, thirdSpellCD},
                "spell", 2);

        TextView fourthSpellTitle = activity.findViewById(R.id.spell_title_fourth);
        TextView fourthSpellDesc = activity.findViewById(R.id.spell_desc_fourth);
        TextView fourthSpellCD = activity.findViewById(R.id.spell_cd_fourth);
        generateSpellDetailsFromJSON(json, new TextView[]{fourthSpellTitle, fourthSpellDesc, fourthSpellCD},
                "spell", 3);
    }

    private void generateSpellDetailsFromJSON(JSONObject json, TextView[] viewObject, String mode, int index) {
        try {
            if (mode.equals("passive")) {
                JSONObject passiveObj = new JSONObject(json.get("passive").toString());
                viewObject[0].setText(passiveObj.getString("name"));
                viewObject[1].setText(Html.fromHtml(passiveObj.getString("description")));
            } else {
                JSONArray spellsObj = new JSONArray(json.get("spells").toString());
                JSONObject skillObj = new JSONObject(spellsObj.get(index).toString());
                viewObject[0].setText(skillObj.getString("name"));
                viewObject[1].setText(Html.fromHtml(skillObj.getString("description")));
                viewObject[2].setText(String.format("Cooldown: %s seconds", skillObj.getString("cooldownBurn")));
            }
        } catch (Exception ignored) {}
    }
}

package com.example.omegalol.view_object;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.omegalol.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

public class RuneObject {
    private Context context;
    private View itemView;
    private JSONObject jsonObject;

    public RuneObject(Context context, View itemView, JSONObject jsonObject) {
        this.context = context;
        this.itemView = itemView;
        this.jsonObject = jsonObject;
    }

    public void setFirstSlotItemView() {
        ImageView firstslot_first = itemView.findViewById(R.id.firstslot_first);
        ImageView firstslot_second = itemView.findViewById(R.id.firstslot_second);
        ImageView firstslot_third = itemView.findViewById(R.id.firstslot_third);
        ImageView firstslot_fourth = itemView.findViewById(R.id.firstslot_fourth);
        this.setRuneImageView(new ImageView[]{firstslot_first, firstslot_second, firstslot_third
                , firstslot_fourth}, 0);
    }

    public void setSecondSlotItemView() {
        ImageView secondslot_first = itemView.findViewById(R.id.secondslot_first);
        ImageView secondslot_second = itemView.findViewById(R.id.secondslot_second);
        ImageView secondslot_third = itemView.findViewById(R.id.secondslot_third);
        ImageView secondslot_fourth = itemView.findViewById(R.id.secondslot_fourth);
        this.setRuneImageView(new ImageView[]{secondslot_first, secondslot_second, secondslot_third
                , secondslot_fourth}, 1);
    }

    public void setThirdSlotItemView() {
        ImageView thirdslot_first = itemView.findViewById(R.id.thirdslot_first);
        ImageView thirdslot_second = itemView.findViewById(R.id.thirdslot_second);
        ImageView thirdslot_third = itemView.findViewById(R.id.thirdslot_third);
        ImageView thirdslot_fourth = itemView.findViewById(R.id.thirdslot_fourth);
        this.setRuneImageView(new ImageView[]{thirdslot_first, thirdslot_second, thirdslot_third
                , thirdslot_fourth}, 2);
    }

    public void setFourthSlotItemView() {
        ImageView fourthslot_first = itemView.findViewById(R.id.fourthslot_first);
        ImageView fourthslot_second = itemView.findViewById(R.id.fourthslot_second);
        ImageView fourthslot_third = itemView.findViewById(R.id.fourthslot_third);
        ImageView fourthslot_fourth = itemView.findViewById(R.id.fourthslot_fourth);
        this.setRuneImageView(new ImageView[]{fourthslot_first, fourthslot_second, fourthslot_third
                , fourthslot_fourth}, 3);
    }

    private void setRuneImageView(ImageView[] imageViewList, int slotIndex) {
        try {
            JSONArray runeSlot = new JSONArray(jsonObject.get("slots").toString());
            JSONObject target = new JSONObject(runeSlot.get(slotIndex).toString());
            JSONArray runeArray = new JSONArray(target.get("runes").toString());
            for (int i = 0; i < imageViewList.length; i++) {
                JSONObject runeIndex = new JSONObject(runeArray.get(i).toString());
                String icon = runeIndex.getString("icon");
                Picasso.get().load(generateUri(icon)).into(imageViewList[i]);
                setOnClickForRuneDetails(imageViewList[i], runeIndex.getString("name")
                        , runeIndex.getString("longDesc"), generateUri(icon));
            }
        } catch (Exception ignored) {}
    }

    private String generateUri(String image) {
        String baseUri = context.getString(R.string.ddragon_uri);
        return String.format("%s%s%s", baseUri, "cdn/img/", image);
    }

    private void setOnClickForRuneDetails(ImageView item, final String name, final String desc, final String uri) {
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(setDetailsInDialog(setRuneDetailsLayout(), uri, name, desc));
                builder.show();
            }
        });
    }

    @SuppressLint("InflateParams")
    private View setRuneDetailsLayout() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        return inflater.inflate(R.layout.layout_rune_details, null);
    }

    private View setDetailsInDialog(View view, String uri, String name, String desc) {
        ImageView runeImg = view.findViewById(R.id.dialog_rune_img);
        TextView runeName = view.findViewById(R.id.dialog_rune_name);
        TextView runeDesc = view.findViewById(R.id.dialog_rune_desc);

        Picasso.get().load(uri).into(runeImg);
        runeName.setText(name);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            runeDesc.setText(Html.fromHtml(desc, Html.FROM_HTML_MODE_COMPACT));
        } else {
            runeDesc.setText(Html.fromHtml(desc));
        }
        return view;
    }
}

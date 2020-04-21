package com.example.omegalol.view_object;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.omegalol.R;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

public class ItemDetailsObject {
    private Context context;
    private JSONObject jsonObject;
    private String itemNameStr, itemPlaintextStr;

    public ItemDetailsObject(Context context, JSONObject jsonObject, String itemName, String itemPlaintext) {
        this.context = context;
        this.jsonObject = jsonObject;
        this.itemNameStr = itemName;
        this.itemPlaintextStr = itemPlaintext;
    }

    public void setOnClickForItemDetails(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(setDetailsInDialog(setItemDetailsLayout()));
                builder.show();
            }
        });
    }

    private void setItemDescription(TextView itemDescription) {
        try {
            String description = jsonObject.getString("description").replace("#FFF", "#000");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                itemDescription.setText(Html.fromHtml(description, Html.FROM_HTML_MODE_COMPACT));
            } else {
                itemDescription.setText(Html.fromHtml(description));
            }
        } catch (Exception ignored) {}
    }

    private void setSellItemGold(TextView itemGold) {
        try {
            JSONObject goldObject = new JSONObject(jsonObject.get("gold").toString());
            String goldOnSell = goldObject.getString("sell");
            itemGold.setText(goldOnSell);
        } catch (Exception e) {
            itemGold.setText("0");
        }
    }

    @SuppressLint("InflateParams")
    private View setItemDetailsLayout() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        return inflater.inflate(R.layout.layout_item_details, null);
    }

    private View setDetailsInDialog(View view) {
        ImageView itemImg = view.findViewById(R.id.dialog_item_img);
        TextView itemName = view.findViewById(R.id.dialog_item_name);
        TextView itemText = view.findViewById(R.id.dialog_item_ptxt);
        TextView itemDesc = view.findViewById(R.id.dialog_item_desc);
        TextView itemGold = view.findViewById(R.id.dialog_item_gold);

        Picasso.get().load(generateImageUri()).into(itemImg);
        itemName.setText(itemNameStr);      itemText.setText(itemPlaintextStr);
        setItemDescription(itemDesc);       setSellItemGold(itemGold);
        return view;
    }

    private String generateImageUri() {
        try {
            String baseUri = context.getString(R.string.ddragon_uri);
            String version = context.getString(R.string.version);
            JSONObject imageObj = new JSONObject(jsonObject.get("image").toString());
            String name = imageObj.get("full").toString();
            return String.format("%s%s%s%s%s", baseUri, "cdn/", version, "/img/item/", name);
        } catch (Exception e) {
            return "";
        }
    }
}

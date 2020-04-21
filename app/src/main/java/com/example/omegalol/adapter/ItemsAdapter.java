package com.example.omegalol.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omegalol.R;
import com.example.omegalol.view_object.ItemDetailsObject;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsHolder> {
    private ArrayList<JSONObject> dataset;
    private JSONObject itemJSON;
    private Context context;

    public ItemsAdapter(Context context, ArrayList<JSONObject> itemList, JSONObject itemJSON) {
        this.dataset = itemList;
        this.itemJSON = itemJSON;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.layout_item, parent, false);
        return new ItemsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsHolder holder, int position) {
        try {
            holder.jsonObject = dataset.get(position);
            setItemText(holder, getItemName(position), getItemPlainText(position));
            holder.setItemButton();
            Picasso.get().load(generateUri(getImageName(position))).into(holder.item_img);
        } catch (Exception e) {
            holder.item_img.setImageResource(R.drawable.app_unknown);
        }
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    private void setItemText(ItemsHolder holder, String name, String plaintxt) {
        holder.item_name.setText(name);
        holder.item_plaintxt.setText(plaintxt);
    }

    private String getItemName(int position) throws JSONException {
        JSONObject nameObj = dataset.get(position);
        return nameObj.get("name").toString();
    }

    private String getItemPlainText(int position) throws JSONException {
        JSONObject itemObj = dataset.get(position);
        String plaintxt = itemObj.get("plaintext").toString();
        if (plaintxt.equals("")) {
            try {
                String recipe = itemObj.get("specialRecipe").toString();
                JSONObject specialRecipt = new JSONObject(itemJSON.get(recipe).toString());
                return String.format("Special Recipe: %s", specialRecipt.get("name").toString());
            } catch (Exception recipeError) {
                JSONObject obj = new JSONObject(itemObj.get("gold").toString());
                if (obj.get("purchasable").toString().equals("true")) {
                    return "Purchasable: Yes";
                }
                return "Purchasable: No";
            }
        }
        return plaintxt;
    }

    private String getImageName(int position) throws JSONException {
        JSONObject nameObj = dataset.get(position);
        JSONObject imageObj = new JSONObject(nameObj.get("image").toString());
        return imageObj.get("full").toString();
    }

    private String generateUri(String image) {
        String baseUri = context.getString(R.string.ddragon_uri);
        String version = context.getString(R.string.version);
        return String.format("%s%s%s%s%s", baseUri, "cdn/", version, "/img/item/", image);
    }

    class ItemsHolder extends RecyclerView.ViewHolder {
        ImageView item_img;
        TextView item_name, item_plaintxt;
        Button item_button;
        JSONObject jsonObject;
        ItemDetailsObject viewObject;

        ItemsHolder(View itemView) {
            super(itemView);
            item_img = itemView.findViewById(R.id.item_img);
            item_name = itemView.findViewById(R.id.item_name);
            item_plaintxt = itemView.findViewById(R.id.item_plaintxt);
            item_button = itemView.findViewById(R.id.view_item);
        }

        void setItemButton() {
            String name = item_name.getText().toString();
            String text = item_plaintxt.getText().toString();
            viewObject = new ItemDetailsObject(context, jsonObject, name, text);
            viewObject.setOnClickForItemDetails(item_button);
        }
    }
}

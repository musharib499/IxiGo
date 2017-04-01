package com.example.root.ixigosearchapp.util;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;



public class Util {


    public static JSONObject loadJSONFromAsset(Context context, String fileName) throws JSONException {
        String json = null;
        JSONObject jsonObject=null;

        try {
            InputStream is = context.getAssets().open(fileName+".json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
         jsonObject=new JSONObject(json);
        return jsonObject;
    }


    public static RecyclerView recyclerView(RecyclerView recyclerView, Context context, boolean orientation) {
        LinearLayoutManager recycler_layout = new LinearLayoutManager(context);
        if (orientation)
            recycler_layout.setOrientation(LinearLayout.VERTICAL);
        else
            recycler_layout.setOrientation(LinearLayout.HORIZONTAL);

        recyclerView.setLayoutManager(recycler_layout);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        return recyclerView;
    }
}

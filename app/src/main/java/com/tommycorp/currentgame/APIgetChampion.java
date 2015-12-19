package com.tommycorp.currentgame;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class APIgetChampion extends AsyncTask<String, String, List<ObjectChampion>> {

    List<ObjectChampion> championlist = new ArrayList<>();
    JSONObject jsonRootObject = new JSONObject();
    JSONArray jsonArray = new JSONArray();
    JSONObject jsonObject = new JSONObject();
    JSONObject tempObject = new JSONObject();
    StringBuilder jsonStringBuilder = new StringBuilder();
    String jsonLine;

    protected List<ObjectChampion> doInBackground(String...urls) {
        try {
            URL url = new URL(urls[0]);
            HttpURLConnection urlC = (HttpURLConnection) url.openConnection();
            BufferedReader jsonReader = new BufferedReader(new InputStreamReader(urlC.getInputStream()));
            while ((jsonLine = jsonReader.readLine()) != null) {
                jsonStringBuilder.append(jsonLine);
            }
            jsonRootObject = new JSONObject(jsonStringBuilder.toString());

            jsonObject = jsonRootObject.optJSONObject("data");
            Iterator<String> keys = jsonObject.keys();
            while (keys.hasNext()) {
                String name = keys.next();
                tempObject = jsonObject.optJSONObject(name);
                int id = tempObject.getInt("id");
                championlist.add(new ObjectChampion(id,name));
            }

        } catch (Exception e) {
            return championlist;
        }
        return championlist;
    }

    public AsyncResponseChamp delegate = null;

    @Override
    protected void onPostExecute(List<ObjectChampion> result) {
        delegate.processFinish(result);
    }
}

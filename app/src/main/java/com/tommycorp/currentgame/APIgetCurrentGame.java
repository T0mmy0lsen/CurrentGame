package com.tommycorp.currentgame;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class APIgetCurrentGame extends AsyncTask<String, String, List<ObjectParticipants>> {

    List<ObjectParticipants> participantslist = new ArrayList<>();
    JSONObject jsonRootObject = new JSONObject();
    JSONArray jsonArray = new JSONArray();
    JSONObject jsonObject = new JSONObject();
    StringBuilder jsonStringBuilder = new StringBuilder();
    String jsonLine;

    protected List<ObjectParticipants> doInBackground(String...urls) {
        try {
            URL url = new URL(urls[0]);
            HttpURLConnection urlC = (HttpURLConnection) url.openConnection();
            BufferedReader jsonReader = new BufferedReader(new InputStreamReader(urlC.getInputStream()));
            while ((jsonLine = jsonReader.readLine()) != null) {
                jsonStringBuilder.append(jsonLine);
            }
            jsonRootObject = new JSONObject(jsonStringBuilder.toString());

            jsonArray = jsonRootObject.optJSONArray("participants");
            for (int i = 0; i < 10; i++){
                jsonObject = jsonArray.getJSONObject(i);
                int spell1 = jsonObject.optInt("spell1Id");
                int spell2 = jsonObject.optInt("spell2Id");
                int chId = jsonObject.optInt("championId");
                int piconId = jsonObject.optInt("profileIconId");
                int sId = jsonObject.optInt("summonerId");
                String sName = jsonObject.optString("summonerName");
                participantslist.add(new ObjectParticipants(spell1,spell2,chId,piconId,sId,sName));
            }
        } catch (Exception e) {
            return participantslist;
        }
        return participantslist;
    }

    public AsyncResponseCG delegate = null;

    @Override
    protected void onPostExecute(List<ObjectParticipants> result) {
        delegate.processFinish(result);
    }
}


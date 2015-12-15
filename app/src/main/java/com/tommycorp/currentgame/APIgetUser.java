package com.tommycorp.currentgame;

        import android.os.AsyncTask;
        import org.json.JSONObject;
        import java.io.BufferedReader;
        import java.io.InputStreamReader;
        import java.net.HttpURLConnection;
        import java.net.URL;
        import java.util.ArrayList;
        import java.util.Iterator;
        import java.util.List;

public class APIgetUser extends AsyncTask<String, String, List<String>> {

    List<String> userinfo = new ArrayList<>();
    JSONObject jsonRootObject = new JSONObject();
    JSONObject jsonObject = new JSONObject();
    StringBuilder jsonStringBuilder = new StringBuilder();
    String jsonLine;
    String name;
    int summonerLevel;
    int summonID;

    protected List<String> doInBackground(String...urls) {
        try {
            URL url = new URL(urls[0]);
            HttpURLConnection urlC = (HttpURLConnection) url.openConnection();
            BufferedReader jsonReader = new BufferedReader(new InputStreamReader(urlC.getInputStream()));
            while ((jsonLine = jsonReader.readLine()) != null) {
                jsonStringBuilder.append(jsonLine);
            }
            jsonRootObject = new JSONObject(jsonStringBuilder.toString());

            Iterator<String> keys = jsonRootObject.keys();
            if( keys.hasNext() ){
                name = keys.next();
            }
            String root = jsonRootObject.optString(name);
            jsonObject = new JSONObject(root);
            summonerLevel = jsonObject.optInt("summonerLevel");
            String summonerLevelStr = "" + summonerLevel;
            summonID = jsonObject.optInt("id");
            String summonIDStr = "" + summonID;

            userinfo.add(name);
            userinfo.add(summonerLevelStr);
            userinfo.add(summonIDStr);

        } catch (Exception e) {
            userinfo.add(e.toString());
            return userinfo;
        }
        return userinfo;
    }

    public AsyncResponse delegate = null;

    @Override
    protected void onPostExecute(List<String> result) {
        delegate.processFinish(result);
    }
}


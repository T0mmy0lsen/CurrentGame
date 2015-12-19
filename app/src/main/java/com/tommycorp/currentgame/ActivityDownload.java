package com.tommycorp.currentgame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ActivityDownload extends AppCompatActivity implements AsyncResponseChamp{

    APIgetChampion apIgetChampion = new APIgetChampion();
    DatabaseChampions DatabaseChampions;
    String url = "https://global.api.pvp.net/api/lol/static-data/euw/v1.2/champion?api_key=a7c084f1-8ec6-4e3e-840b-085cbde654a1";
    List<ObjectChampion> objectChampionList = new ArrayList<ObjectChampion>();

    Button download;
    TextView champion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        download = (Button) findViewById(R.id.downloadButton);
        champion = (TextView) findViewById(R.id.downloadText);

        download.setEnabled(false);
        DatabaseChampions = new DatabaseChampions(getApplicationContext());
        apIgetChampion.delegate = this;
        apIgetChampion.execute(url);
    }

    public void processFinish(List<ObjectChampion> result) {
        objectChampionList = result;
        for (int i = 0; i < result.size(); i++){
            DatabaseChampions.addChamp(result.get(i));
        }
        download.setEnabled(true);
    }

    public void closeWindowClick(View view){
        finish();
    }
}

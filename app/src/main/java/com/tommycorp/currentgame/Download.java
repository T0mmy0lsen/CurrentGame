package com.tommycorp.currentgame;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Download extends AppCompatActivity implements AsyncResponseChamp{

    APIgetChampion apIgetChampion = new APIgetChampion();
    ChampionsDB championsDB;
    String url = "https://global.api.pvp.net/api/lol/static-data/euw/v1.2/champion?api_key=a7c084f1-8ec6-4e3e-840b-085cbde654a1";
    List<Champion> championList = new ArrayList<Champion>();
    Button download = (Button) findViewById(R.id.downloadButton);
    TextView champion = (TextView) findViewById(R.id.downloadText);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        download.setEnabled(false);
        championsDB = new ChampionsDB(getApplicationContext());
        apIgetChampion.delegate = this;
        apIgetChampion.execute(url);
    }

    public void processFinish(List<Champion> result) {
        championList = result;
        for (int i = 0; i < result.size(); i++){
            championsDB.addChamp(result.get(i));
        }
        download.setEnabled(true);
        champion.setText(championsDB.getChampion(12).toString());
    }

    public void closeWindowClick(){

    }
}

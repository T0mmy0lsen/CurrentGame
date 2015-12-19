package com.tommycorp.currentgame;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ActivityShowCurrentGame extends AppCompatActivity implements AsyncResponseCG{

    APIgetCurrentGame apIgetCurrentGame = new APIgetCurrentGame();
    ArrayAdapter<ObjectParticipants> participantsAdapter;
    List<ObjectParticipants> participants = new ArrayList<ObjectParticipants>();
    ListView participantsListView;
    DatabaseChampions DatabaseChampions;
    DatabaseSummonerspell DatabaseSummonerspell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_current_game);

        participantsListView = (ListView) findViewById(R.id.cgListView);

        Bundle getLoginScreen = getIntent().getExtras();
        if( getLoginScreen == null ){
            return;
        }

        int userID = getLoginScreen.getInt("ID");
        String id = "" + userID;

        String url = "https://euw.api.pvp.net/observer-mode/rest/consumer/getSpectatorGameInfo/EUW1/" + id + "?api_key=a7c084f1-8ec6-4e3e-840b-085cbde654a1";

        apIgetCurrentGame.delegate = this;
        apIgetCurrentGame.execute(url);
    }

    public void processFinish(List<ObjectParticipants> result) {
        participants = result;
        updateList();
    }

    private void updateList(){
        participantsAdapter = new ParticipantsListAdapter();
        participantsListView.setAdapter(participantsAdapter);
    }

    private class ParticipantsListAdapter extends ArrayAdapter<ObjectParticipants> {
        public ParticipantsListAdapter() {
            super(ActivityShowCurrentGame.this, R.layout.currentgame_list, participants);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {

            DatabaseChampions = new DatabaseChampions(getApplicationContext());
            DatabaseSummonerspell = new DatabaseSummonerspell(getApplicationContext());

            if (view == null){
                view = getLayoutInflater().inflate(R.layout.currentgame_list, parent, false);
            }

            ObjectParticipants currentParticipant = participants.get(position);


            ImageView cgSpell1 = (ImageView) view.findViewById(R.id.cgSpell11);
            ImageView cgSpell2 = (ImageView) view.findViewById(R.id.cgSpell22);
            TextView cgChampionId = (TextView) view.findViewById(R.id.cgChampName);
            ImageView cgProfileIconId = (ImageView) view.findViewById(R.id.cgChampId);
            TextView cgSummonId = (TextView) view.findViewById(R.id.cgPlayerId);
            TextView cgSummonerName = (TextView) view.findViewById(R.id.cgPlayer);

            cgSpell1.setImageURI(DatabaseSummonerspell.getSummonerSpellID(currentParticipant.getSpell1Id()));
            cgSpell2.setImageURI(DatabaseSummonerspell.getSummonerSpellID(currentParticipant.getSpell2Id()));

            String champName = DatabaseChampions.getChampion(currentParticipant.getChampionId());

            cgChampionId.setText(champName);
            cgProfileIconId.setImageURI(Uri.parse("android.resource://com.tommycorp.currentgame/drawable/" + champName.toLowerCase() + "_square_0"));
            cgSummonId.setText(String.valueOf(currentParticipant.getSummonerId()));
            cgSummonerName.setText(currentParticipant.getSummonerName());

            return view;
        }
    }
}

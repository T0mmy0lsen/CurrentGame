package com.tommycorp.currentgame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ShowCurrentGame extends AppCompatActivity implements AsyncResponseCG{

    APIgetCurrentGame apIgetCurrentGame = new APIgetCurrentGame();
    ArrayAdapter<Participants> participantsAdapter;
    List<Participants> participants = new ArrayList<Participants>();
    ListView participantsListView;

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

    public void processFinish(List<Participants> result) {
        participants = result;
        updateList();
    }

    private void updateList(){
        participantsAdapter = new ParticipantsListAdapter();
        participantsListView.setAdapter(participantsAdapter);
    }

    private class ParticipantsListAdapter extends ArrayAdapter<Participants> {
        public ParticipantsListAdapter() {
            super(ShowCurrentGame.this, R.layout.currentgame_list, participants);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null){
                view = getLayoutInflater().inflate(R.layout.currentgame_list, parent, false);
            }

            Participants currentParticipant = participants.get(position);


            TextView cgSpell1 = (TextView) view.findViewById(R.id.cgSpell1);
            TextView cgSpell2 = (TextView) view.findViewById(R.id.cgSpell2);
            TextView cgChampionId = (TextView) view.findViewById(R.id.cgChampName);
            TextView cgProfileIconId = (TextView) view.findViewById(R.id.cgChampId);
            TextView cgSummonId = (TextView) view.findViewById(R.id.cgPlayerId);
            TextView cgSummonerName = (TextView) view.findViewById(R.id.cgPlayer);

            cgSpell1.setText(String.valueOf(currentParticipant.getSpell1Id()));
            cgSpell2.setText(String.valueOf(currentParticipant.getSpell2Id()));
            cgChampionId.setText(String.valueOf(currentParticipant.getChampionId()));
            cgProfileIconId.setText(String.valueOf(currentParticipant.getProfileIconId()));
            cgSummonId.setText(String.valueOf(currentParticipant.getSummonerId()));
            cgSummonerName.setText(currentParticipant.getSummonerName());

            return view;
        }
    }
}

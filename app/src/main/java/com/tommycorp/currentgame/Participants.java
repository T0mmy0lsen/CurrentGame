package com.tommycorp.currentgame;

public class Participants {

    private int _spell1Id, _spell2Id, _championId, _profileIconId, _summonerId;
    private String _summonerName;

    public Participants(int spell1Id, int spell2Id, int championId, int profileIconId, int summonerId, String summonerName){
        _spell1Id = spell1Id;
        _spell2Id = spell2Id;
        _championId = championId;
        _profileIconId = profileIconId;
        _summonerId = summonerId;
        _summonerName = summonerName;
    }

    public int getSpell1Id() { return _spell1Id;}
    public int getSpell2Id() { return _spell2Id;}
    public int getChampionId() { return _championId;}
    public int getProfileIconId() { return _profileIconId;}
    public int getSummonerId() { return _summonerId;}
    public String getSummonerName() { return _summonerName;}
}


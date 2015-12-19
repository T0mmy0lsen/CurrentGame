package com.tommycorp.currentgame;


import android.net.Uri;

public class ObjectUser {

    private String _loginname, _summonname, _region;
    private Uri _iconUri;
    private int _id, _summonerLevel, _summonID;

    public ObjectUser(int id, String loginname, String summonname, String region, Uri iconUri, int summonerLevel, int summonID) {
        _id = id;
        _loginname = loginname;
        _summonname = summonname;
        _region = region;
        _iconUri = iconUri;
        _summonerLevel = summonerLevel;
        _summonID = summonID;
    }

    public int getId(){
        return _id;
    }

    public String getLoginname(){
        return _loginname;
    }

    public String getSummonname() { return _summonname; }

    public String getRegion(){
        return _region;
    }

    public Uri geticonUri() {
        return _iconUri;
    }

    public int getSummonerLevel(){
        return _summonerLevel;
    }

    public int getSummonID(){
        return _summonID;
    }
}

package com.tommycorp.currentgame;

public class ObjectChampion {

    private String _name;
    private int _id;

    public ObjectChampion(int id, String name) {
        _id = id;
        _name = name;
    }

    public int getid(){
        return _id;
    }

    public String getname(){
        return _name;
    }

}


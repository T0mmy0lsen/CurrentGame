package com.tommycorp.currentgame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class DatabaseSummonerspell extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "summonerspell.db",
            TABLE = "summomnerspell",
            C_ID = "_id",
            C_NAME = "_uri";

    public DatabaseSummonerspell(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE + "(" +
                C_ID + " INTEGER, " +
                C_NAME + " TEXT " +
                ");";
        db.execSQL(query);
    }

    public void addSummonerSpellID() {
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("INSERT INTO " + TABLE + " VALUES(21, 'barrier')");
        db.execSQL("INSERT INTO " + TABLE + " VALUES(1, 'boost')");
        db.execSQL("INSERT INTO " + TABLE + " VALUES(2, 'clairvoyance')");
        db.execSQL("INSERT INTO " + TABLE + " VALUES(14, 'dot')");
        db.execSQL("INSERT INTO " + TABLE + " VALUES(3, 'exhaust')");
        db.execSQL("INSERT INTO " + TABLE + " VALUES(4, 'flash')");
        db.execSQL("INSERT INTO " + TABLE + " VALUES(6, 'haste')");
        db.execSQL("INSERT INTO " + TABLE + " VALUES(7, 'heal')");
        db.execSQL("INSERT INTO " + TABLE + " VALUES(13, 'mana')");
        db.execSQL("INSERT INTO " + TABLE + " VALUES(17, 'odingarrison')");
        db.execSQL("INSERT INTO " + TABLE + " VALUES(30, 'pororecall')");
        db.execSQL("INSERT INTO " + TABLE + " VALUES(31, 'porothrow')");
        db.execSQL("INSERT INTO " + TABLE + " VALUES(11, 'smite')");
        db.execSQL("INSERT INTO " + TABLE + " VALUES(32, 'snowball')");
        db.execSQL("INSERT INTO " + TABLE + " VALUES(12, 'teleport')");

        db.close();
    }

    public Uri getSummonerSpellID(int i){
        SQLiteDatabase db = getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + TABLE + " WHERE _id=" + i, null);
        c.moveToFirst();

        Uri iconUri = Uri.parse("android.resource://com.tommycorp.currentgame/drawable/" + c.getString(1));

        db.close();
        c.close();
        return iconUri;
    }
}

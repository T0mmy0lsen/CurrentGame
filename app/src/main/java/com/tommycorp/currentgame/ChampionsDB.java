package com.tommycorp.currentgame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class ChampionsDB extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "champions.db",
            TABLE = "champions",
            C_ID = "_id",
            C_NAME = "_name";

    public ChampionsDB(Context context) {
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

    public void addChamp(Champion champ) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(C_ID, champ.getid());
        contentValues.put(C_NAME, champ.getname());

        db.insert(TABLE, null, contentValues);
        db.close();
    }

    public Champion getChampion(int i){
        Champion getChampion;
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + TABLE + " WHERE " + C_ID + " = '" + i + "'", null);

        getChampion = new Champion(c.getInt(0), c.getString(1));

        db.close();
        c.close();
        return getChampion;
    }
}
package com.tommycorp.currentgame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class DatabaseUser extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "users.db",
    TABLE_USERS = "users",
    COLUMN_ID = "_id",
    COLUMN_LOGINNAME = "_loginname",
    COLUMN_SUMMONNAME = "_summonname",
    COLUMN_REGION = "_region",
    COLUMN_ICONURI = "_iconuri",
    COLUMN_SUMLEVEL = "_summonerLevel",
    COLUMN_SUMID = "_summonID";

    public DatabaseUser(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_USERS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_LOGINNAME + " TEXT, " +
                COLUMN_SUMMONNAME + " TEXT, " +
                COLUMN_REGION + " TEXT, " +
                COLUMN_ICONURI + " TEXT, " +
                COLUMN_SUMLEVEL + " INTEGER, " +
                COLUMN_SUMID + " INTEGER " +
                ");";
        db.execSQL(query);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public void addUser(ObjectUser user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_ID, user.getId());
        contentValues.put(COLUMN_LOGINNAME, user.getLoginname());
        contentValues.put(COLUMN_SUMMONNAME, user.getSummonname());
        contentValues.put(COLUMN_REGION, user.getRegion());
        contentValues.put(COLUMN_ICONURI, user.geticonUri().toString());
        contentValues.put(COLUMN_SUMLEVEL, user.getSummonerLevel());
        contentValues.put(COLUMN_SUMID, user.getSummonID());

        db.insert(TABLE_USERS, null, contentValues);
        db.close();
    }

    public List<ObjectUser> getAllUsers(){
        List<ObjectUser> users = new ArrayList<ObjectUser>();
        SQLiteDatabase db = getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_USERS, null);

        if (c.moveToFirst()) {
            do {
                users.add(new ObjectUser(Integer.parseInt(c.getString(0)), c.getString(1), c.getString(2)
                        , c.getString(3), Uri.parse(c.getString(4)), Integer.parseInt(c.getString(5))
                        , Integer.parseInt(c.getString(6))));
            } while (c.moveToNext());
        }
        db.close();
        c.close();
        return users;
    }

    public int getUserCount(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_USERS, null);
        int count = c.getCount();
        db.close();
        c.close();

        return count;
    }

    public void deleteUser(String i) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_USERS, COLUMN_ID + "=" + i, null);
        db.close();
    }

    public ObjectUser getUser(int i){
        ObjectUser getUser;
        SQLiteDatabase db = getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_USERS, null);
        c.moveToPosition(i);

        getUser = new ObjectUser(Integer.parseInt(c.getString(0)), c.getString(1), c.getString(2)
                        , c.getString(3), Uri.parse(c.getString(4)), Integer.parseInt(c.getString(5))
                        , Integer.parseInt(c.getString(6)));

        db.close();
        c.close();
        return getUser;
    }
}

package com.tommycorp.currentgame;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class IntentCurrentGame extends IntentService {

    private static final String TAG = "CurrentGame";

    public IntentCurrentGame() {
        super("IntentCurrentGame");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "Started");
    }
}

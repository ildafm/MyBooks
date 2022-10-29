package com.if5a.mybooksfadli.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.if5a.mybooksfadli.R;

public class AppPreference {
    private SharedPreferences prefs;
    private Context context;

    public AppPreference(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
    }

    //apakah sudah pernah diinsert datanya atau belum
    //pertama launch atau bukan
    public void setFirstRun(Boolean input){
        SharedPreferences.Editor editor = prefs.edit();
        String key = context.getString(R.string.app_first_run);
        editor.putBoolean(key, input);
        editor.commit();
    }

    //mengambil nilai yang sudah pernah diisi
    public Boolean getFirstRun(){
        String key = context.getString(R.string.app_first_run);
        return prefs.getBoolean(key, true);
    }
}

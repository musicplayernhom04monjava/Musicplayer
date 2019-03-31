package com.hutech.musicplayer.managers;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.hutech.musicplayer.base.MusicPlayerApplication;
import com.hutech.musicplayer.models.User;

import java.util.HashMap;

public class SessionManager {

    public static SessionManager sharedInstance = new SessionManager();
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private static final int PRIVATE_MODE = 0;
    private static final String KEY_FILENAME = "account_data";
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_ID = "id";
    public static final String KEY_USERNAME = "username";
    private static final String KEY_FULLNAME = "fullname";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_GENDER  = "gender";
    private static final String KEY_PASSWORD = "password";

    public SessionManager(){
        pref = MusicPlayerApplication.getInstance().getSharedPreferences(KEY_FILENAME,PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(User user)
    {
        //Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        // Save value in pref
//        editor.putString(KEY_ID, user.getId());
//        editor.putString(KEY_USERNAME,user.getUsername());
        editor.putString(KEY_FULLNAME,user.getFullname());
        editor.putString(KEY_EMAIL,user.getEmail());
//        editor.putString(KEY_GENDER,user.Gender());
        editor.putString(KEY_PASSWORD,user.getPassword());
        editor.commit();
    }

    public HashMap<String,String> getUserDetail()
    {
        HashMap<String,String> data = new HashMap<>();
//        data.put(KEY_ID,pref.getString(KEY_ID,"0"));
//        data.put(KEY_USERNAME,pref.getString(KEY_USERNAME,"0"));
//        data.put(KEY_GENDER, pref.getString(KEY_GENDER,""));
        data.put(KEY_FULLNAME,pref.getString(KEY_FULLNAME,""));
        data.put(KEY_EMAIL,pref.getString(KEY_EMAIL,""));
        data.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD,""));
        return  data;
    }
    //remove
    public void removeValue()
    {
        editor.remove(KEY_ID)
                .remove(KEY_PASSWORD)
                .remove(KEY_FULLNAME)
                .remove(KEY_EMAIL).commit();
//                .remove(KEY_GENDER)
//                .remove(KEY_USERNAME)

    }
}

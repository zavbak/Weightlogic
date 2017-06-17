package ru.a799000.android.weightlogic.repository.sharedpref;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ru.a799000.android.weightlogic.mvp.model.intities.SettingsApp;

/**
 * Created by user on 17.06.2017.
 */

public class SharedPreferenseHelper{

    private  static final String MY_APP_PREFERENCES = "ca7eed88-2409-4de7-b529-52598af76734";
    private static final String PREF_SETTINGS = "963dfbb5-5f25-4fa9-9a9e-6766bfebfda8";


    private SharedPreferences sharedPrefs;
    private static SharedPreferenseHelper instance;

    private SharedPreferenseHelper(Context context){
        //using application context just to make sure we don't leak any activities
        sharedPrefs = context.getApplicationContext().getSharedPreferences(MY_APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    public static synchronized SharedPreferenseHelper getInstance(Context context){
        if(instance == null)
            instance = new SharedPreferenseHelper(context);
        return instance;
    }

    public SettingsApp getSettings() {
        String sSetting = sharedPrefs.getString(PREF_SETTINGS, null);
        if(sSetting == null){
            return new SettingsApp();
        }else{

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            SettingsApp settings = gson.fromJson(sSetting, SettingsApp.class);
            return  settings;

        }
    }

    public void saveSettings(SettingsApp settings) {

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(PREF_SETTINGS, gson.toJson(settings));
        editor.apply();

    }

}

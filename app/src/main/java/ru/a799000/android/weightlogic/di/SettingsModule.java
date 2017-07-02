package ru.a799000.android.weightlogic.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.a799000.android.weightlogic.app.App;
import ru.a799000.android.weightlogic.mvp.model.intities.SettingsApp;
import ru.a799000.android.weightlogic.repository.sharedpref.SharedPreferenseHelper;

/**
 * Created by user on 02.07.2017.
 */
@Module
public class SettingsModule {
    @Provides
    SettingsApp provideRealmConfiguration(Context context){
        return SharedPreferenseHelper.getInstance(context).getSettings();
    }
}

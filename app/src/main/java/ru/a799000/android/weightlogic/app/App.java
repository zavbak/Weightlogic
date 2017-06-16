package ru.a799000.android.weightlogic.app;

import android.app.Application;

import io.realm.Realm;
import ru.a799000.android.weightlogic.di.AppComponent;
import ru.a799000.android.weightlogic.di.AppModule;
import ru.a799000.android.weightlogic.di.DaggerAppComponent;


/**
 * Created by user on 07.06.2017.
 */

public class App extends Application{
    private static AppComponent mAppComponent;


    @Override
    public void onCreate() {
        super.onCreate();
        init();


    }

    /*
    Инициализация всего
     */
    void init(){
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

        Realm.init(this);
        Realm.setDefaultConfiguration(mAppComponent.getRealmConfiguration());

    }

    public static AppComponent getAppComponent() {
        return mAppComponent;
    }
}

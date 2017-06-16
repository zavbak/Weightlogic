package ru.a799000.android.weightlogic.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import io.realm.RealmConfiguration;
import ru.a799000.android.weightlogic.mvp.presenters.GeneralScreenPr;


@Singleton
@Component(modules={AppModule.class,RealmModule.class})
public interface AppComponent {
    Context getContext();
    RealmConfiguration getRealmConfiguration();

    void injectGeneralScreenPr(GeneralScreenPr generalScreenPr);
}

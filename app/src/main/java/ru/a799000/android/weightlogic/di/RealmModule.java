package ru.a799000.android.weightlogic.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.DellAllInteractor;
import ru.a799000.android.weightlogic.repository.realm.RealmMigration;


@Module
public class RealmModule {
    @Provides
    @Singleton
    RealmConfiguration provideRealmConfiguration(){
        return new RealmConfiguration.Builder()
                .schemaVersion(1)
                .migration(new RealmMigration())
                .build();
    }


    @Provides
    Realm provideRealm(){
        return Realm.getDefaultInstance();
    }

}

package ru.a799000.android.weightlogic.mvp.model.interactors.realm;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import ru.a799000.android.weightlogic.repository.realm.RealmMigration;

import static org.junit.Assert.*;

/**
 * Created by Alex on 11.08.2017.
 */
public class GetPaletSummInteractorTest {


    Realm mRealm;
    RealmConfiguration mConfig;


    @Before
    public void setUp() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();

        mConfig = new RealmConfiguration.Builder()
                .schemaVersion(1)
                .migration(new RealmMigration())
                .build();



        if (mRealm == null || mRealm.isClosed()) {
            mRealm = Realm.getInstance(mConfig);
        }


    }

    @Test
    public void getObservable() throws Exception {
        Log.d("anit", "start");

        GetPaletSummInteractor getPaletSummInteractor = new GetPaletSummInteractor(1);

        getPaletSummInteractor.getObservable()
                .doOnNext(o -> {
                    Log.d("anit", "o " + o);
                })
                .subscribe();

    }

}
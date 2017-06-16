package ru.a799000.android.weightlogic.repository.realm;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmModel;

/**
 * Created by user on 07.06.2017.
 */

public class RealmHelper {

    @Inject
    Realm mRealm;

    @Inject
    public RealmHelper() {
    }

    public   <E extends RealmModel> long getNextId(Class<E> clazz) {

        long id = 1;

        try {
            id = mRealm.where(clazz).max(RealmTable.ID).intValue() + 1;
        } catch (Exception e) {

        }

        return id;
    }



}

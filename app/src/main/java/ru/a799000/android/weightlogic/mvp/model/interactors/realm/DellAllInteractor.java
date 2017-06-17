package ru.a799000.android.weightlogic.mvp.model.interactors.realm;


import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

import ru.a799000.android.weightlogic.app.App;
import ru.a799000.android.weightlogic.mvp.model.interactors.Interactor;
import ru.a799000.android.weightlogic.mvp.model.intities.Barcode;
import ru.a799000.android.weightlogic.mvp.model.intities.Product;
import rx.Observable;

public class DellAllInteractor extends Interactor {

    @Inject
    Realm mRealm;


    public DellAllInteractor() {
        App.getAppComponent().injectDellAllInteractor(this);
    }

    @Override
    public Observable getObservable() {

        try {
            mRealm.beginTransaction();

            RealmQuery<Product> query = mRealm.where(Product.class);
            RealmResults<Product> results = query.findAll();
            results.deleteAllFromRealm();


            RealmQuery<Barcode> queryBarcode = mRealm.where(Barcode.class);
            RealmResults<Barcode> resultsBarcode = queryBarcode.findAll();
            resultsBarcode.deleteAllFromRealm();

            mRealm.commitTransaction();

            return Observable.empty();

        } catch (Exception e) {
            e.printStackTrace();
            try {
                mRealm.cancelTransaction();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            return Observable.error(e);
        }

    }


}

package ru.a799000.android.weightlogic.mvp.model.interactors.realm;

import java.math.BigDecimal;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import ru.a799000.android.weightlogic.app.App;
import ru.a799000.android.weightlogic.mvp.model.interactors.Interactor;
import ru.a799000.android.weightlogic.mvp.model.intities.Barcode;
import ru.a799000.android.weightlogic.mvp.model.intities.PaletSumResult;
import ru.a799000.android.weightlogic.mvp.model.intities.Product;
import ru.a799000.android.weightlogic.repository.realm.RealmTable;
import rx.Observable;


public class DellPaletInteractor extends Interactor {


    @Inject
    Realm mRealm;
    long mId;
    int mPallet;


    /**
     * get Product by ID
     */
    public DellPaletInteractor(long idProduct, int pallet) {
        App.getAppComponent().injectGetPaletSummInteractor(this);
        mId = idProduct;
        mPallet = pallet;
    }


    Observable<PaletSumResult> getPuletSumResultObserver() {
        try {
            mRealm.beginTransaction();
            Product product = mRealm.where(Product.class).equalTo(RealmTable.ID, mId).findFirst();
            RealmList<Barcode> barcodes = product.getBarcodes();
            RealmResults<Barcode> results = barcodes.where().equalTo("pallet", mPallet).findAll();
            results.deleteAllFromRealm();
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


    @Override
    public Observable<PaletSumResult> getObservable() {
        try {
           return getPuletSumResultObserver();

        } catch (Exception e) {
            e.printStackTrace();
            return Observable.error(e);

        }
    }


}

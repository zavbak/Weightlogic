package ru.a799000.android.weightlogic.mvp.model.interactors.realm;

import javax.inject.Inject;

import io.realm.Realm;
import ru.a799000.android.weightlogic.app.App;
import ru.a799000.android.weightlogic.mvp.model.interactors.Interactor;
import ru.a799000.android.weightlogic.mvp.model.intities.Barcode;
import ru.a799000.android.weightlogic.repository.realm.RealmTable;
import rx.Observable;


public class DellBarcodeInteractor extends Interactor {

    @Inject
    Realm mRealm;
    long mIdBarcode;


    /**
     * get Product by ID
     */
    public DellBarcodeInteractor(long idBarcode) {
        App.getAppComponent().injectDellBarcodeInteractor(this);
        mIdBarcode = idBarcode;
    }


    @Override
    public Observable getObservable() {


        try {
            mRealm.beginTransaction();

            Barcode barcodeRealm = mRealm.where(Barcode.class).equalTo(RealmTable.ID, mIdBarcode).findFirst();
            barcodeRealm.deleteFromRealm();

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

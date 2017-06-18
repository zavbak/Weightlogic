package ru.a799000.android.weightlogic.mvp.model.interactors.realm;

import javax.inject.Inject;

import io.realm.Realm;
import ru.a799000.android.weightlogic.app.App;
import ru.a799000.android.weightlogic.mvp.model.interactors.Interactor;
import ru.a799000.android.weightlogic.mvp.model.intities.Barcode;
import ru.a799000.android.weightlogic.mvp.model.intities.Product;
import ru.a799000.android.weightlogic.repository.realm.RealmTable;
import rx.Observable;


public class GetBarcodeByIDInteractor extends Interactor<Barcode> {

    @Inject
    Realm mRealm;
    long mIdBarcode;


    /**
     * get Product by ID
     */
    public GetBarcodeByIDInteractor(long idBarcode) {
        App.getAppComponent().GetBarcodeByIDInteractor(this);
        mIdBarcode = idBarcode;
    }


    @Override
    public Observable<Barcode> getObservable() {
        try {
            Barcode barcodeRealm = mRealm.where(Barcode.class).equalTo(RealmTable.ID, mIdBarcode).findFirst();
            return Observable.just(barcodeRealm);
        } catch (Exception e) {
            e.printStackTrace();
            return Observable.error(e);
        }
    }


}

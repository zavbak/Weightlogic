package ru.a799000.android.weightlogic.mvp.model.interactors.realm;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;
import ru.a799000.android.weightlogic.app.App;
import ru.a799000.android.weightlogic.mvp.model.interactors.Interactor;
import ru.a799000.android.weightlogic.mvp.model.intities.Barcode;
import ru.a799000.android.weightlogic.mvp.model.intities.Product;
import ru.a799000.android.weightlogic.repository.realm.RealmTable;
import rx.Observable;


public class GetAllBarcodesByIdProductInteractor extends Interactor<RealmResults<Barcode>> {

    @Inject
    Realm mRealm;
    long mId;


    /**
     * get Product by ID
     */
    public GetAllBarcodesByIdProductInteractor(long id) {
        App.getAppComponent().injectGetBarcodesByIdProductInteractor(this);
        mId = id;
    }


    @Override
    public Observable<RealmResults<Barcode>> getObservable() {
        try {
            Product product = mRealm.where(Product.class).equalTo(RealmTable.ID, mId).findFirst();
            RealmList<Barcode> barcodes = product.getBarcodes();
            RealmResults<Barcode> results = barcodes.sort(RealmTable.ID, Sort.DESCENDING);
            return Observable.just(results);

        } catch (Exception e) {
            e.printStackTrace();
            return Observable.error(e);

        }
    }


}

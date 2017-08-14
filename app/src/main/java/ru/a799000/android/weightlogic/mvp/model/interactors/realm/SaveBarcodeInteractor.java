package ru.a799000.android.weightlogic.mvp.model.interactors.realm;

import java.util.Date;

import javax.inject.Inject;

import io.realm.Realm;
import ru.a799000.android.weightlogic.app.App;
import ru.a799000.android.weightlogic.mvp.model.interactors.Interactor;
import ru.a799000.android.weightlogic.mvp.model.intities.Barcode;
import ru.a799000.android.weightlogic.mvp.model.intities.Product;
import ru.a799000.android.weightlogic.repository.realm.RealmHelper;
import ru.a799000.android.weightlogic.repository.realm.RealmTable;
import rx.Observable;


public class SaveBarcodeInteractor extends Interactor {

    @Inject
    Realm mRealm;
    long mIdProduct;
    Barcode mBarcode;
    @Inject
    RealmHelper mRealmHelper;


    /**
     * get Product by ID
     */
    public SaveBarcodeInteractor(long idProduct, Barcode barcode) {
        App.getAppComponent().injectSaveBarcodeInteractor(this);
        mIdProduct = idProduct;
        mBarcode   = barcode;
    }


    @Override
    public Observable<Barcode> getObservable() {

        Barcode barcodeRealm;

        try {
            mRealm.beginTransaction();

            Product realmProduct = mRealm.where(Product.class).equalTo(RealmTable.ID, mIdProduct).findFirst();
            if(realmProduct == null){
                return Observable.error(new Exception("Товар не найден"));
            }

            Boolean isNew = false;

            if (mBarcode.getId() == 0) {
                barcodeRealm = mRealm.createObject(Barcode.class,mRealmHelper.getNextId(Barcode.class));
                isNew = true;
            } else {
                barcodeRealm = mRealm.where(Barcode.class).equalTo(RealmTable.ID, mBarcode.getId()).findFirst();
            }


            barcodeRealm.setDate(new Date());
            barcodeRealm.setBarcode(mBarcode.getBarcode());
            barcodeRealm.setWeight(mBarcode.getWeight());
            barcodeRealm.setPlaces(mBarcode.getPlaces());
            barcodeRealm.setDate(mBarcode.getDate());
            barcodeRealm.setDate(new Date());
            barcodeRealm.setPallet(mBarcode.getPallet());

            if(isNew) realmProduct.getBarcodes().add(barcodeRealm);

            mRealm.commitTransaction();

            return Observable.just(barcodeRealm);
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

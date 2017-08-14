package ru.a799000.android.weightlogic.mvp.model.interactors.realm;

import java.util.Iterator;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import ru.a799000.android.weightlogic.app.App;
import ru.a799000.android.weightlogic.mvp.model.interactors.Interactor;
import ru.a799000.android.weightlogic.mvp.model.intities.Barcode;
import ru.a799000.android.weightlogic.mvp.model.intities.Product;
import ru.a799000.android.weightlogic.mvp.model.intities.PaletSumResult;
import ru.a799000.android.weightlogic.repository.realm.RealmTable;
import rx.Observable;
import rx.Observer;


public class GetPaletSummInteractor extends Interactor {


    @Inject
    Realm mRealm;
    long mId;


    /**
     * get Product by ID
     */
    public GetPaletSummInteractor(long id) {
        App.getAppComponent().injectGetPaletSummInteractor(this);
        mId = id;
    }


    Observable<PaletSumResult> getPuletSumResultObserver(int pallet) {


        Product product = mRealm.where(Product.class).equalTo(RealmTable.ID, mId).findFirst();
        RealmList<Barcode> barcodes = product.getBarcodes();
        RealmResults<Barcode> results = barcodes.where().equalTo("pallet", pallet).findAll();


         return Observable.from(results)
                .reduce(new PaletSumResult(pallet,product),(r, barcode) -> {

                     int plases = ((PaletSumResult) r).getPlaces() + barcode.getPlaces();
                     float weights = ((PaletSumResult) r).getWeight() + barcode.getWeight();

                    ((PaletSumResult) r).setPlaces(plases);
                    ((PaletSumResult) r).setWeight(weights);

                    return ((PaletSumResult) r);
                } );

    }


    @Override
    public Observable<PaletSumResult> getObservable() {
        try {
            Product product = mRealm.where(Product.class).equalTo(RealmTable.ID, mId).findFirst();
            RealmList<Barcode> barcodes = product.getBarcodes();
            RealmResults<Barcode> results = barcodes.sort(RealmTable.ID);

            return Observable.from(results)
                    .map(barcode -> {
                        return barcode.getPallet();
                    })
                    .distinct()
                    .flatMap(i-> {
                        return getPuletSumResultObserver(i);
                    });

        } catch (Exception e) {
            e.printStackTrace();
            return Observable.error(e);

        }
    }


}

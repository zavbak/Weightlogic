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


public class GetPaletSummInteractor extends Interactor {


    @Inject
    Realm mRealm;
    long mId;
    int mPallet;


    /**
     * get Product by ID
     */
    public GetPaletSummInteractor(long id,int pallet) {
        App.getAppComponent().injectGetPaletSummInteractor(this);
        mId = id;
        mPallet = pallet;
    }


    Observable<PaletSumResult> getPuletSumResultObserver() {


        Product product = mRealm.where(Product.class).equalTo(RealmTable.ID, mId).findFirst();
        RealmList<Barcode> barcodes = product.getBarcodes();
        RealmResults<Barcode> results = barcodes.where().equalTo("pallet", mPallet).findAll();




         return Observable.from(results)
                .reduce(new PaletSumResult(mPallet,product),(r, barcode) -> {

                     int plases = ((PaletSumResult) r).getPlaces() + barcode.getPlaces();
                     float weights = ((PaletSumResult) r).getWeight() + barcode.getWeight();

                    BigDecimal bigDecimalW = ((PaletSumResult) r).getBigDecimalWeight().add(new BigDecimal(Float.toString(barcode.getWeight())));


                    ((PaletSumResult) r).setPlaces(plases);
                    ((PaletSumResult) r).setWeight(weights);
                    ((PaletSumResult) r).setBigDecimalWeight(bigDecimalW);


                    return ((PaletSumResult) r);
                } );

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

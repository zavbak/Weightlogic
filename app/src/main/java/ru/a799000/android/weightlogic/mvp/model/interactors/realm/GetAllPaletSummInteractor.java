package ru.a799000.android.weightlogic.mvp.model.interactors.realm;

import java.math.BigDecimal;
import java.util.Iterator;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;
import ru.a799000.android.weightlogic.app.App;
import ru.a799000.android.weightlogic.mvp.model.interactors.Interactor;
import ru.a799000.android.weightlogic.mvp.model.intities.Barcode;
import ru.a799000.android.weightlogic.mvp.model.intities.Product;
import ru.a799000.android.weightlogic.mvp.model.intities.PaletSumResult;
import ru.a799000.android.weightlogic.repository.realm.RealmTable;
import rx.Observable;
import rx.Observer;


public class GetAllPaletSummInteractor extends Interactor {


    @Inject
    Realm mRealm;
    long mId;


    /**
     * get Product by ID
     */
    public GetAllPaletSummInteractor(long id) {
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
                    })
                    .toSortedList((paletSumResult, paletSumResult2) -> {

                        int a = paletSumResult.getPullet();  //элемент преобразуется в Integer
                        int b = paletSumResult2.getPullet();   //элемент преобразуется в Integer
                        return a < b ? 1 : a == b ? 0 : -1;  //выполняется сравнение и возврат наименьшего элемента
                    })
                    .flatMap(paletSumResults -> {
                        return Observable.from(paletSumResults);
                    });

        } catch (Exception e) {
            e.printStackTrace();
            return Observable.error(e);

        }
    }


}

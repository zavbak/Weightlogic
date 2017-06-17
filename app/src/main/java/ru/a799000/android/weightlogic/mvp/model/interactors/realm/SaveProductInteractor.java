package ru.a799000.android.weightlogic.mvp.model.interactors.realm;

import javax.inject.Inject;

import io.realm.Realm;
import ru.a799000.android.weightlogic.app.App;
import ru.a799000.android.weightlogic.mvp.model.interactors.Interactor;
import ru.a799000.android.weightlogic.mvp.model.intities.Product;
import ru.a799000.android.weightlogic.repository.realm.RealmHelper;
import ru.a799000.android.weightlogic.repository.realm.RealmTable;
import rx.Observable;

/**
 * Created by user on 17.06.2017.
 */

public class SaveProductInteractor extends Interactor<Product> {


    @Inject
    Realm mRealm;
    @Inject
    RealmHelper mRealmHelper;

    Product mProduct;

    public SaveProductInteractor(Product product) {
        mProduct = product;
        App.getAppComponent().injectSaveProductInteractor(this);
    }


    @Override
    public Observable<Product> getObservable() {

        Product realmProduct;
        try {
            mRealm.beginTransaction();

            if (mProduct.getId() == 0) {
                realmProduct = mRealm.createObject(Product.class,mRealmHelper.getNextId(Product.class));
            } else {
                realmProduct = mRealm.where(Product.class).equalTo(RealmTable.ID, mProduct.getId()).findFirst();
            }

            realmProduct.setCode(mProduct.getCode());
            realmProduct.setName(mProduct.getName());
            realmProduct.setUnit(mProduct.getUnit());
            realmProduct.setInitBarcode(mProduct.getInitBarcode());
            realmProduct.setStart(mProduct.getStart());
            realmProduct.setFinish(mProduct.getFinish());
            realmProduct.setCoef(mProduct.getCoef());

            mRealm.commitTransaction();

            return Observable.just(realmProduct);
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

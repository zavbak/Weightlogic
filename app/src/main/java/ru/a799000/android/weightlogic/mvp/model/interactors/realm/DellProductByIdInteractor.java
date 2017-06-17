package ru.a799000.android.weightlogic.mvp.model.interactors.realm;

import javax.inject.Inject;

import io.realm.Realm;
import ru.a799000.android.weightlogic.app.App;
import ru.a799000.android.weightlogic.mvp.model.interactors.Interactor;
import ru.a799000.android.weightlogic.mvp.model.intities.Product;
import ru.a799000.android.weightlogic.repository.realm.RealmTable;
import rx.Observable;


public class DellProductByIdInteractor extends Interactor {

    @Inject
    Realm mRealm;
    long mId;


    /**
     * get Product by ID
     */
    public DellProductByIdInteractor(long id) {
        App.getAppComponent().injectDellProductByIdInteractor(this);
        mId = id;
    }


    @Override
    public Observable getObservable() {
        try {
            mRealm.beginTransaction();
            Product product = mRealm.where(Product.class).equalTo(RealmTable.ID, mId).findFirst();
            product.deleteFromRealm();
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

package ru.a799000.android.weightlogic.mvp.model.interactors.realm;

import javax.inject.Inject;

import io.realm.Realm;
import ru.a799000.android.weightlogic.app.App;
import ru.a799000.android.weightlogic.mvp.model.interactors.Interactor;
import ru.a799000.android.weightlogic.mvp.model.intities.Product;
import ru.a799000.android.weightlogic.repository.realm.RealmTable;
import rx.Observable;


public class GetProductByIdInteractor extends Interactor {

    @Inject
    Realm mRealm;
    long mId;


    /**
     * get Product by ID
     */
    public GetProductByIdInteractor(long id) {
        App.getAppComponent().injectGetProductByIdInteractor(this);
        mId = id;
    }


    @Override
    public Observable<Product> getObservable() {
        try {
            Product product = mRealm.where(Product.class).equalTo(RealmTable.ID, mId).findFirst();
            return Observable.just(product);

        } catch (Exception e) {
            e.printStackTrace();
            return Observable.error(e);

        }
    }


}

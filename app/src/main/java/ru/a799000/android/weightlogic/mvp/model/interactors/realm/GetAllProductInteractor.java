package ru.a799000.android.weightlogic.mvp.model.interactors.realm;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import ru.a799000.android.weightlogic.app.App;
import ru.a799000.android.weightlogic.mvp.model.interactors.Interactor;
import ru.a799000.android.weightlogic.mvp.model.intities.Product;
import rx.Observable;


public class GetAllProductInteractor extends Interactor {

    @Inject
    Realm mRealm;


    /**
     * get All
     */
    public GetAllProductInteractor() {
        App.getAppComponent().injectGetAllProductInteractor(this);
    }

    @Override
    public Observable<RealmResults<Product>> getObservable() {
        try {

            RealmQuery<Product> query = mRealm.where(Product.class);
            RealmResults<Product> results = query.findAll();
            return Observable.just(results);

        } catch (Exception e) {
            e.printStackTrace();
            return Observable.error(e);

        }
    }


}

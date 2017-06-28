package ru.a799000.android.weightlogic.mvp.model.interactors;

import javax.inject.Inject;

import ru.a799000.android.weightlogic.app.App;
import ru.a799000.android.weightlogic.mvp.model.intities.load.IntitiesParamLoadHttp;
import ru.a799000.android.weightlogic.repository.net.ApiService;
import ru.a799000.android.weightlogic.repository.net.ResponseModelDataServiceLoad;
import rx.Observable;

/**
 * Created by user on 17.06.2017.
 */

public class LoadHttpInteractor extends Interactor<ResponseModelDataServiceLoad> {

    String auth;
    IntitiesParamLoadHttp mIntitiesParamLoadHttp;

    @Inject
    ApiService mApiService;




    public LoadHttpInteractor(String auth, IntitiesParamLoadHttp intitiesParamLoadHttp) {
        this.auth = auth;
        mIntitiesParamLoadHttp = intitiesParamLoadHttp;
        App.getAppComponent().injectLoadHttpInteractor(this);
    }

    @Override
    public Observable<ResponseModelDataServiceLoad> getObservable() {

        return mApiService.getResponseModelDataServiceObservable(auth,mIntitiesParamLoadHttp);
    }
}

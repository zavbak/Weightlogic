package ru.a799000.android.weightlogic.mvp.model.interactors;

import javax.inject.Inject;

import ru.a799000.android.weightlogic.app.App;
import ru.a799000.android.weightlogic.repository.net.SendModel;
import ru.a799000.android.weightlogic.repository.net.ApiService;
import ru.a799000.android.weightlogic.repository.net.ResponseModel;
import rx.Observable;

/**
 * Created by user on 17.06.2017.
 */

public class HttpInteractor extends Interactor<ResponseModel> {

    String auth;
    SendModel mIntitiesParamLoadHttp;

    @Inject
    ApiService mApiService;




    public HttpInteractor(String auth, SendModel intitiesParamLoadHttp) {
        this.auth = auth;
        mIntitiesParamLoadHttp = intitiesParamLoadHttp;
        App.getAppComponent().injectHttpInteractor(this);
    }

    @Override
    public Observable<ResponseModel> getObservable() {

        return mApiService.getResponseModelDataServiceObservable("Api",auth,mIntitiesParamLoadHttp);
    }
}

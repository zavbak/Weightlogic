package ru.a799000.android.weightlogic.mvp.model.interactors;

import javax.inject.Inject;

import ru.a799000.android.weightlogic.app.App;
import ru.a799000.android.weightlogic.mvp.model.intities.SettingsApp;
import ru.a799000.android.weightlogic.repository.net.AutoritationManager;
import ru.a799000.android.weightlogic.repository.net.SendModel;
import ru.a799000.android.weightlogic.repository.net.ApiService;
import ru.a799000.android.weightlogic.repository.net.ResponseModel;
import rx.Observable;

/**
 * Created by user on 17.06.2017.
 */

public class HttpInteractor extends Interactor<ResponseModel> {


    SendModel mSendModel;

    @Inject
    ApiService mApiService;


    public HttpInteractor(SendModel sendModel) {
        mSendModel = sendModel;
        App.getAppComponent().injectHttpInteractor(this);
    }

    @Override
    public Observable<ResponseModel> getObservable() {

        SettingsApp settingsApp = App.getAppComponent().getSettingsApp();
        String base = settingsApp.getNameBase();

        String auth = AutoritationManager.getStringAutorization(settingsApp.getLogin(),
                settingsApp.getPass());

        return mApiService.getResponseModelDataServiceObservable(base,auth,mSendModel);
    }
}

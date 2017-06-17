package ru.a799000.android.weightlogic.mvp.model.interactors;

import ru.a799000.android.weightlogic.app.App;
import ru.a799000.android.weightlogic.mvp.model.intities.SettingsApp;
import ru.a799000.android.weightlogic.repository.sharedpref.SharedPreferenseHelper;
import rx.Observable;

/**
 * Created by user on 17.06.2017.
 */

public class GetSettings extends Interactor<SettingsApp> {

    @Override
    protected Observable<SettingsApp> getObservable() {
        return Observable.just(SharedPreferenseHelper.getInstance(App.getAppComponent().getContext()).getSettings());
    }
}

package ru.a799000.android.weightlogic.mvp.model.interactors;

import ru.a799000.android.weightlogic.app.App;
import ru.a799000.android.weightlogic.mvp.model.intities.SettingsApp;
import ru.a799000.android.weightlogic.repository.sharedpref.SharedPreferenseHelper;
import rx.Observable;

/**
 * Created by user on 17.06.2017.
 */

public class SaveSettings extends Interactor<SettingsApp> {

    SettingsApp mSettingsApp;

    public SaveSettings(SettingsApp settingsApp) {
        mSettingsApp = settingsApp;
    }

    @Override
    protected Observable getObservable() {
        try {
            SharedPreferenseHelper.getInstance(App.getAppComponent().getContext()).saveSettings(mSettingsApp);
            return  Observable.empty();
        } catch (Exception e) {
            e.printStackTrace();
            return Observable.error(e);
        }
    }
}

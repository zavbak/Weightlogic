package ru.a799000.android.weightlogic.mvp.model.interactors;

import ru.a799000.android.weightlogic.app.App;
import ru.a799000.android.weightlogic.mvp.model.intities.SettingsApp;
import ru.a799000.android.weightlogic.repository.filesys.LoadFileHelper;
import ru.a799000.android.weightlogic.repository.sharedpref.SharedPreferenseHelper;
import rx.Observable;

/**
 * Created by user on 17.06.2017.
 */

public class LoadFileseInteractor extends Interactor<String> {

    @Override
    public Observable<String> getObservable() {
        SettingsApp settingsApp = SharedPreferenseHelper.getInstance(App.getAppComponent().getContext()).getSettings();
        LoadFileHelper loadFileHelper = new LoadFileHelper(settingsApp.getFileNameLoad());
        return loadFileHelper.readStringObservable();
    }
}

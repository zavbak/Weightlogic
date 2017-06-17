package ru.a799000.android.weightlogic.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import ru.a799000.android.weightlogic.mvp.model.interactors.GetSettingsInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.SaveSettingsInteractor;
import ru.a799000.android.weightlogic.mvp.model.intities.SettingsApp;
import ru.a799000.android.weightlogic.mvp.view.SettingsScreenView;
import ru.a799000.android.weightlogic.repository.sharedpref.SharedPreferenseHelper;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by user on 17.06.2017.
 */

@InjectViewState
public class SettingsScreenPr extends MvpPresenter<SettingsScreenView> {
    SettingsApp mSettingsApp;

    public SettingsScreenPr() {
        mSettingsApp = new SettingsApp();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        GetSettingsInteractor interactor = new GetSettingsInteractor();
        interactor.getObservable()
                .subscribeOn(AndroidSchedulers.mainThread()) //Schedulers.io()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(settings -> {
                    this.mSettingsApp = settings;
                    getViewState().refresh();
                });
    }

    public String getFileName() {
        return mSettingsApp.getFileName() == null ? "" : mSettingsApp.getFileName();
    }

    public void changeFileName(String s) {
        mSettingsApp.setFileName(s);
    }


    public void onClickCancel() {
        getViewState().finishView();
    }

    public void onClickSave() {
        SaveSettingsInteractor interactor = new SaveSettingsInteractor(mSettingsApp);
        interactor.getObservable()
                .subscribeOn(AndroidSchedulers.mainThread()) //Schedulers.io()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(settings -> {

                        }, throwable -> {

                            getViewState().showSnackbarView(throwable.toString());
                            getViewState().finishView();

                        }, () -> {
                            getViewState().showSnackbarView("Сохранено");
                            getViewState().finishView();
                        }
                );

    }
}

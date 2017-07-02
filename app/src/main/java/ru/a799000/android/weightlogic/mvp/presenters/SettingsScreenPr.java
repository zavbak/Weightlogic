package ru.a799000.android.weightlogic.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import ru.a799000.android.weightlogic.mvp.model.interactors.GetSettingsInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.SaveSettingsInteractor;
import ru.a799000.android.weightlogic.mvp.model.intities.SettingsApp;
import ru.a799000.android.weightlogic.mvp.view.SettingsScreenView;
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
        return mSettingsApp.getFileNameLoad() == null ? "" : mSettingsApp.getFileNameLoad();
    }

    public void changeFileName(String s) {
        mSettingsApp.setFileNameLoad(s);
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

    public String getServerName() {
        return mSettingsApp.getNameServer() == null ? "" : mSettingsApp.getNameServer();
    }

    public void changeServerName(String s) {
        mSettingsApp.setNameServer(s);
    }

    public String getNameBase() {
        return mSettingsApp.getNameBase() == null ? "" : mSettingsApp.getNameBase();
    }

    public void changeNameBase(String s) {
        mSettingsApp.setNameBase(s);
    }

    public String getCode() {
        return mSettingsApp.getCode1C() == null ? "" : mSettingsApp.getCode1C();
    }

    public void changeCode(String s) {
        mSettingsApp.setCode1C(s);
    }

    public String getLogin() {
        return mSettingsApp.getLogin() == null ? "" : mSettingsApp.getLogin();
    }

    public void changeLogin(String s) {
        mSettingsApp.setLogin(s);
    }

    public String getPass() {
        return mSettingsApp.getPass() == null ? "" : mSettingsApp.getPass();
    }

    public void changePass(String s) {
        mSettingsApp.setPass(s);
    }
}

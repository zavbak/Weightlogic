package ru.a799000.android.weightlogic.mvp.presenters;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import ru.a799000.android.weightlogic.R;
import ru.a799000.android.weightlogic.app.App;
import ru.a799000.android.weightlogic.mvp.model.interactors.GetSettingsInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.SaveFileInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.DellAllInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.LoadFileseInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.SaveProductInteractor;
import ru.a799000.android.weightlogic.mvp.model.intities.SettingsApp;
import ru.a799000.android.weightlogic.mvp.model.intities.load.IntitiesLoadObject;
import ru.a799000.android.weightlogic.mvp.model.intities.Product;
import ru.a799000.android.weightlogic.mvp.view.GeneralScreenView;
import ru.a799000.android.weightlogic.repository.filesys.SaveFileHelper;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


@InjectViewState
public class GeneralScreenPr extends MvpPresenter<GeneralScreenView> {

    @Inject
    Context mContext;
    List<String> mlist;

    public GeneralScreenPr() {
        App.getAppComponent().injectGeneralScreenPr(this);
        mlist = Arrays.asList(mContext.getResources().getStringArray(R.array.commands_array));
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }


    List<String> getListCommands() {
        return mlist;
    }


    void executeCommand(int id) {

        switch (id) {
            case 0:
                getViewState().startProductsScreenView();
                break;
            case 1:
                loadFile();
                break;
            case 2:
                saveFiles();
                break;
            case 4:
                getViewState().startSettingsScreenView();
                break;
            case 5:
                getViewState().startTestScreenView();
                break;
        }
    }

    private void saveFiles() {
        getViewState().showProgressDialog(true);

        Observable<SettingsApp> settingsAppObservable = new GetSettingsInteractor().getObservable();
        Observable<String> obsJson = new SaveFileInteractor().getObservable();

        Observable.zip(settingsAppObservable, obsJson, (settingsApp, s) -> {
            String fileName = settingsApp.getFileNameSave();
            String bodyJson = s;
            return new SaveFileHelper(fileName, s);
        })
                .flatMap(saveFileHelper -> {
                    return saveFileHelper.getObservable();
                })
                .subscribeOn(AndroidSchedulers.mainThread()) //делаем запрос, преобразование, кэширование в отдельном потоке
                .observeOn(AndroidSchedulers.mainThread()) // обработка результата - в main thread
                .subscribe(o -> {}, throwable -> {
                    getViewState().showSnackbarView(throwable.toString());
                    getViewState().showProgressDialog(false);

                }, () -> {
                    getViewState().showSnackbarView("Записали!");
                    getViewState().showProgressDialog(false);
                });
    }

    private void loadFile() {
        getViewState().showProgressDialog(true);

        DellAllInteractor dellAllInteractor = new DellAllInteractor();
        dellAllInteractor.getObservable()
                .subscribeOn(AndroidSchedulers.mainThread()) //делаем запрос, преобразование, кэширование в отдельном потоке
                .observeOn(AndroidSchedulers.mainThread()) // обработка результата - в main thread
                .subscribe(o -> {

                }, throwable -> {
                    getViewState().showProgressDialog(false);
                    getViewState().showSnackbarView(throwable.toString());

                }, () -> {
                    getViewState().showSnackbarView("Удалили");
                    getViewState().showProgressDialog(true);
                    LoadFileseInteractor interactor = new LoadFileseInteractor();
                    interactor.getObservable()
                            .map(s -> {
                                GsonBuilder builder = new GsonBuilder();
                                Gson gson = builder.create();
                                return gson.fromJson(s, IntitiesLoadObject.class);
                            })
                            .flatMap(intitiesLoadObject -> {
                                return Observable.from(intitiesLoadObject.getTovars());
                            })
                            .subscribeOn(Schedulers.io()) //делаем запрос, преобразование, кэширование в отдельном потоке
                            .observeOn(AndroidSchedulers.mainThread()) // обработка результата - в main thread
                            .flatMap(intitiesTovar -> {
                                Product product = new Product();
                                product.setCode(intitiesTovar.getCode());
                                product.setName(intitiesTovar.getName());
                                product.setUnit(intitiesTovar.getEd());

                                SaveProductInteractor saveProductInteractor = new SaveProductInteractor(product);
                                return saveProductInteractor.getObservable();

                            })
                            .count()
                            .subscribe(integer -> {

                                getViewState().showSnackbarView("Загружено: " + integer);
                                getViewState().showProgressDialog(false);

                            }, throwable -> {

                                getViewState().showSnackbarView(throwable.getMessage());
                                getViewState().showProgressDialog(false);


                            }, () -> {


                            });

                });


    }

    public void onStart() {

        getViewState().refreshView(getListCommands());
    }

    public void selectCommand(int position) {
        getViewState().showSnackbarView(mlist.get(position));
        executeCommand(position);
    }

    public void pressKey(char number) {
        try {
            int i = Integer.parseInt(Character.toString(number));
            if ((i != 0) && (i - 1 < mlist.size())) {
                executeCommand(i - 1);
                getViewState().showSnackbarView(mlist.get(i - 1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

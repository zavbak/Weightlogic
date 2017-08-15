package ru.a799000.android.weightlogic.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import io.realm.RealmResults;
import ru.a799000.android.weightlogic.mvp.model.interactors.GetSettingsInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.SaveSettingsInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.DellBarcodeInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.DellProductByIdInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.GetAllProductInteractor;
import ru.a799000.android.weightlogic.mvp.model.intities.Product;
import ru.a799000.android.weightlogic.mvp.model.intities.SettingsApp;
import ru.a799000.android.weightlogic.mvp.view.ProductsListScreenView;
import ru.a799000.android.weightlogic.ui.dialogs.OkCancelDialog;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by user on 17.06.2017.
 */

@InjectViewState
public class ProductsListScreenPr extends MvpPresenter<ProductsListScreenView> implements OkCancelDialog.CallBackOkCancelDialog {

    RealmResults<Product> mData;
    String mId;
    int mPositionCurent;

    OkCancelDialog.BuilderInterface mOkCancelDialog;
    final int OK_CANCEL_DI_DELL = 1;

    int mPositionForDell;


    private void refreshList() {
        GetAllProductInteractor interactor = new GetAllProductInteractor();
        interactor.getObservable()
                .subscribeOn(AndroidSchedulers.mainThread()) //Schedulers.io()
                .observeOn(AndroidSchedulers.mainThread()) //AndroidSchedulers.mainThread()
                .subscribe(
                        resultO -> {
                            mData = (RealmResults<Product>) resultO;
                            getViewState().refreshView(mData);

                        }
                        , throwable ->
                                getViewState().showInfoView(throwable.getMessage()));


    }


    public void onStart() {
        setCurentPosition();
        refreshList();
    }

    void executerCommand(int number, int position) {
        switch (number) {
            case 1:

                getViewState().startListScreenBarcodes(Long.toString(mData.get(position).getId()));
                break;
            case 3:

                getViewState().startDetailProductScreenView(null);
                break;
            case 9:
                delleteProduct(position);
                break;

        }
    }

    public void pressKey(char number, int position) {
        try {
            int i = Integer.parseInt(Character.toString(number));

            //getViewState().showSnackbarView("I = " + i + " position: " +  position);
            executerCommand(i, position);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void delleteProduct(int position) {

        mPositionForDell = position;

        mOkCancelDialog = new OkCancelDialog.BuilderInterface() {
            @Override
            public int getId() {
                return OK_CANCEL_DI_DELL;
            }

            @Override
            public String getTitle() {
                return "Удалить ?";
            }

            @Override
            public String getMessage() {
                return null;
            }
        };


        getViewState().startOkCancelDialog();
    }


    public void onClickProduct(int position) {
        getViewState().startDetailProductScreenView(Long.toString(mData.get(position).getId()));
    }

    public void setID(String id) {
        mId = id;
    }

    public int getSelectionPosition() {
        return mPositionCurent;
    }

    public void scanBarcode(String s) {
        getViewState().showSnackbarView(s);
    }

    public void saveCurentPosition(int selectedItemPosition) {

        mPositionCurent = selectedItemPosition;

        SettingsApp settingsApp;
        GetSettingsInteractor interactor = new GetSettingsInteractor();
        interactor.getObservable()
                .subscribeOn(AndroidSchedulers.mainThread()) //Schedulers.io()
                .observeOn(AndroidSchedulers.mainThread()) //AndroidSchedulers.mainThread()
                .flatMap(settings -> {
                    settings.setCurentProduct(selectedItemPosition);
                    settings.setCurentBarcode(0);
                    return new SaveSettingsInteractor(settings).getObservable();
                })
                .subscribe();

    }


    public void setCurentPosition() {

        mPositionCurent = 0;
        GetSettingsInteractor interactor = new GetSettingsInteractor();
        interactor.getObservable()
                .subscribeOn(AndroidSchedulers.mainThread()) //Schedulers.io()
                .observeOn(AndroidSchedulers.mainThread())
                .filter(settingsApp -> {
                    return settingsApp != null;
                })
                .map(settingsApp -> settingsApp.getCurentProduct())
                .map(integer -> {
                    if (integer < 0) {
                        return 0;
                    } else {
                        return integer;
                    }
                })
                .subscribe(integer -> {
                    mPositionCurent = integer;
                });
    }


    public OkCancelDialog.BuilderInterface getOkCancelDialog() {
       return mOkCancelDialog;
    }

    @Override
    public void chouiceDialog(int id, Boolean positive) {
        if (id == OK_CANCEL_DI_DELL) {
            if (positive) {


                long idDell = 0;
                try {
                    idDell = mData.get(mPositionForDell).getId();

                    DellProductByIdInteractor interactor = new DellProductByIdInteractor(idDell);
                    //DellProductByIdInteractor interactor = new DellProductByIdInteractor(100);
                    interactor.getObservable()
                            .subscribeOn(AndroidSchedulers.mainThread()) //Schedulers.io()
                            .observeOn(AndroidSchedulers.mainThread()) //AndroidSchedulers.mainThread()
                            .subscribe(
                                    resultO -> {

                                    }
                                    , throwable -> {
                                        getViewState().showSnackbarView(throwable.toString());
                                    }
                                    , () -> {
                                        getViewState().showSnackbarView("Удалено");
                                    }
                            );


                } catch (Exception e) {
                    e.printStackTrace();
                    getViewState().showSnackbarView(e.getMessage());
                }

                refreshList();

            }
        }


        mOkCancelDialog = null;
        getViewState().startOkCancelDialog();
    }
}

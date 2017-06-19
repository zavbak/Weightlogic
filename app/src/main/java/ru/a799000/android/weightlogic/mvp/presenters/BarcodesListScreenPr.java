package ru.a799000.android.weightlogic.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;


import io.realm.RealmResults;


import ru.a799000.android.weightlogic.mvp.model.interactors.realm.DellBarcodeInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.GetAllBarcodesByIdProductInteractor;

import ru.a799000.android.weightlogic.mvp.model.intities.Barcode;

import ru.a799000.android.weightlogic.mvp.view.BarcodesListScreenView;

import rx.android.schedulers.AndroidSchedulers;


@InjectViewState
public class BarcodesListScreenPr extends MvpPresenter<BarcodesListScreenView> {

    RealmResults<Barcode> mData;
    String mIdProduct;
    String mIdPosition;
    int positionCurent;


    private void refreshList() {

        GetAllBarcodesByIdProductInteractor interactor = new GetAllBarcodesByIdProductInteractor(Long.parseLong(mIdProduct));
        interactor.getObservable()
                .subscribeOn(AndroidSchedulers.mainThread()) //Schedulers.io()
                .observeOn(AndroidSchedulers.mainThread()) //AndroidSchedulers.mainThread()
                .subscribe(
                        resultO -> {
                            mData = (RealmResults<Barcode>) resultO;
                            getViewState().refreshView(mData);
                            setListPosition();


                        }
                        , throwable ->
                                getViewState().showInfoView(throwable.getMessage()));

    }


    void setListPosition() {
        if (mIdPosition != null) {
            int i = 0;
            for (Barcode barcode : mData) {
                if (barcode.getId() == Long.parseLong(mIdPosition)) {
                    positionCurent = i;
                    getViewState().setListPosition();
                }
                i = i + 1;
            }
        }
    }


    public void onStart() {
        refreshList();
    }

    void executerCommand(int number, int position) {
        switch (number) {

            case 3:
                getViewState().startDetailBarcodeScreenView(mIdProduct,null);
                break;
            case 9:
                dellete(position);
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

    private void dellete(int position) {

        DellBarcodeInteractor dellBarcodeInteractor = new DellBarcodeInteractor(mData.get(position).getId());
        dellBarcodeInteractor.getObservable()
                .subscribeOn(AndroidSchedulers.mainThread()) //Schedulers.io()
                .observeOn(AndroidSchedulers.mainThread()) //AndroidSchedulers.mainThread()
                .subscribe(
                        resultO -> {


                        }
                        , throwable ->
                                getViewState().showSnackbarView(throwable.toString())
                        , () -> {

                            getViewState().showSnackbarView("Удалили!");
                        });


    }


    public void onClickBarcode(int position) {
        getViewState().startDetailBarcodeScreenView(mIdProduct,Long.toString(mData.get(position).getId()));
    }

    public void setIdProduct(String id) {
        mIdProduct = id;
    }

    public int getSelectionPosition() {
        return positionCurent;
    }
}

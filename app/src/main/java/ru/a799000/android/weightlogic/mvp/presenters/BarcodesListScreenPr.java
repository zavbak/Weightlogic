package ru.a799000.android.weightlogic.mvp.presenters;



import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;


import java.math.BigDecimal;
import java.text.DecimalFormat;


import io.realm.RealmResults;


import ru.a799000.android.weightlogic.mvp.model.interactors.GetSettingsInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.SaveSettingsInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.DellBarcodeInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.GetAllBarcodesByIdProductInteractor;

import ru.a799000.android.weightlogic.mvp.model.interactors.realm.GetProductByIdInteractor;
import ru.a799000.android.weightlogic.mvp.model.intities.Barcode;

import ru.a799000.android.weightlogic.mvp.model.intities.Product;
import ru.a799000.android.weightlogic.mvp.model.intities.SettingsApp;
import ru.a799000.android.weightlogic.mvp.view.BarcodesListScreenView;

import ru.a799000.android.weightlogic.ui.dialogs.OkCancelDialog;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;


@InjectViewState
public class BarcodesListScreenPr extends MvpPresenter<BarcodesListScreenView> implements OkCancelDialog.CallBackOkCancelDialog {

    RealmResults<Barcode> mData;
    String mParamIdProduct;
    String mIdPosition;
    int mPositionCurent;

    Product mProduct;



    OkCancelDialog.BuilderInterface mOkCancelDialog;
    final int OK_CANCEL_DI_DELL = 1;

    int mPositionForDell;



    private void refreshList() {

        Observable<Product> oProduct = new GetProductByIdInteractor(Long.parseLong(mParamIdProduct != null ? mParamIdProduct : "0")).getObservable();
        Observable<RealmResults<Barcode>> oBarcodes = new GetAllBarcodesByIdProductInteractor(Long.parseLong(mParamIdProduct)).getObservable();

        Observable.zip(oBarcodes, oProduct, (barcodes, product) -> {
            init(barcodes, product);
            return Observable.empty();
        })
                .subscribeOn(AndroidSchedulers.mainThread()) //Schedulers.io()
                .observeOn(AndroidSchedulers.mainThread()) //AndroidSchedulers.mainThread()
                .subscribe(objectObservable -> {
                }, throwable -> {
                    getViewState().showInfoView(throwable.getMessage());
                });
    }


    void init(RealmResults<Barcode> barcodes, Product product) {
        mData = (RealmResults<Barcode>) barcodes;
        mProduct = product;
        getViewState().refreshView(mData);
    }


    public void onStart() {
        loadSettings();
        refreshList();
    }

    void executerCommand(int number, int position) {
        switch (number) {

            case 3:
                getViewState().startDetailBarcodeScreenView(mParamIdProduct, null);
                break;
            case 5:
                getViewState().startReportPalet(mParamIdProduct);
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


    public void onClickBarcode(int position) {
        getViewState().startDetailBarcodeScreenView(mParamIdProduct, Long.toString(mData.get(position).getId()));
    }

    public void setParamIdProduct(String id) {
        mParamIdProduct = id;
    }


    public void scanBarcode(String s) {
        getViewState().startDetailBarcodeForNewBarcodeScreenView(mParamIdProduct, s);
    }

    public void saveSettings(int selectedItemPosition) {

        mPositionCurent = selectedItemPosition;

        SettingsApp settingsApp;
        GetSettingsInteractor interactor = new GetSettingsInteractor();
        interactor.getObservable()
                .subscribeOn(AndroidSchedulers.mainThread()) //Schedulers.io()
                .observeOn(AndroidSchedulers.mainThread()) //AndroidSchedulers.mainThread()
                .flatMap(settings -> {
                    settings.setCurentBarcode(selectedItemPosition);
                    return new SaveSettingsInteractor(settings).getObservable();
                })
                .subscribe();

    }

    public void loadSettings() {

        mPositionCurent = 0;
        GetSettingsInteractor interactor = new GetSettingsInteractor();
        interactor.getObservable()
                .subscribeOn(AndroidSchedulers.mainThread()) //Schedulers.io()
                .observeOn(AndroidSchedulers.mainThread())
                .filter(settingsApp -> {
                    return settingsApp != null;
                })
                .map(settingsApp -> settingsApp.getCurentBarcode())
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

    public int getSelectionPosition() {
        return mPositionCurent;
    }

    public CharSequence getInfoProduct() {
        if (mProduct == null) {
            return "";
        } else {

            String str = mProduct.getName();
            return str;
        }

    }

    public CharSequence getInfoBarcodes() {


        if (mProduct.getBarcodes().size() == 0) {
            return "";
        } else {
            String str = "";

            BigDecimal bigDecimalW = new BigDecimal(Float.toString(0));

            float weight = 0;
            int plases = 0;
            int count = 0;

            for (Barcode barcode : mData) {
                weight = weight + barcode.getWeight();
                plases = plases + barcode.getPlaces();


                bigDecimalW = bigDecimalW.add(new BigDecimal(Float.toString(barcode.getWeight())));


                count++;

            }

            DecimalFormat df = new DecimalFormat();
            df.setGroupingUsed(true);
            String result = df.format(bigDecimalW);


            return String.format("Вес %s Mест %d Кол. %d", result, plases, count);

        }

    }

    @Override
    public void chouiceDialog(int id, Boolean positive) {
        if (id == OK_CANCEL_DI_DELL) {
            if (positive) {


                long idDell = 0;
                try {
                    idDell = mData.get(mPositionForDell).getId();

                    DellBarcodeInteractor dellBarcodeInteractor = new DellBarcodeInteractor(idDell);
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




    public OkCancelDialog.BuilderInterface getOkCancelDialog() {
        return mOkCancelDialog;
    }
}

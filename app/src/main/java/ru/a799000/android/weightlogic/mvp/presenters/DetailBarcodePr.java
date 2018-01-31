package ru.a799000.android.weightlogic.mvp.presenters;

import android.view.KeyEvent;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.text.SimpleDateFormat;
import java.util.Date;

import ru.a799000.android.weightlogic.mvp.model.common.BarcodeSeporator;
import ru.a799000.android.weightlogic.mvp.model.interactors.GetSettingsInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.SaveSettingsInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.DellBarcodeInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.GetBarcodeByIDInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.GetPaletSummInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.GetProductByIdInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.SaveBarcodeInteractor;
import ru.a799000.android.weightlogic.mvp.model.intities.Barcode;
import ru.a799000.android.weightlogic.mvp.model.intities.PaletSumResult;
import ru.a799000.android.weightlogic.mvp.model.intities.Product;
import ru.a799000.android.weightlogic.mvp.model.intities.SettingsApp;
import ru.a799000.android.weightlogic.mvp.view.DetailBarcodeView;
import ru.a799000.android.weightlogic.ui.dialogs.OkCancelDialog;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by user on 17.06.2017.
 */

@InjectViewState
public class DetailBarcodePr extends MvpPresenter<DetailBarcodeView> implements OkCancelDialog.CallBackOkCancelDialog {
    String mParamIdProduct;
    String mParamIdBarcode;
    String mParamBarcode;

    //Комментарий****7


    Product mProduct;
    Barcode mBarcode;
    BarcodeSeporator mBarcodeSeporator;

    OkCancelDialog.BuilderInterface mOkCancelDialog;
    final int OK_CANCEL_DI_DELL = 1;


    PaletSumResult mPaletSumResult;
    Boolean mControlLengthBK;


    public DetailBarcodePr() {
        mProduct = new Product();
        mBarcode = new Barcode();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        init();
    }

    private void init() {


        Observable<Barcode> oBarcode = new GetBarcodeByIDInteractor(Long.parseLong(mParamIdBarcode != null ? mParamIdBarcode : "0")).getObservable();
        Observable<Product> oProduct = new GetProductByIdInteractor(Long.parseLong(mParamIdProduct != null ? mParamIdProduct : "0")).getObservable();
        Observable<SettingsApp> oSettingsApp = new GetSettingsInteractor().getObservable();


        Observable.zip(oProduct, oBarcode, oSettingsApp, (product, barcode, settingsApp) -> {

            if (settingsApp == null){
                mControlLengthBK = false;
            }else{
                mControlLengthBK = settingsApp.getControlLengthBK();
            }

            if (product != null) {
                mProduct = Product.getBuilder()
                        .id(product.getId())
                        .code(product.getCode())
                        .name(product.getName())
                        .initBarcode(product.getInitBarcode())
                        .unit(product.getUnit())
                        .start(product.getStart())
                        .finish(product.getFinish())
                        .coef(product.getCoef())
                        .build();
            }

            if (barcode != null) {
                mBarcode = Barcode.getBuilder()
                        .setId(barcode.getId())
                        .setBarcode(barcode.getBarcode())
                        .setDate(barcode.getDate())
                        .setWeight(barcode.getWeight())
                        .setPlaces(barcode.getPlaces())
                        .setPallet(barcode.getPallet())
                        .build();
            } else {
                mBarcode.setPlaces(1);
                if (mParamBarcode != null) {
                    changeBarcode(mParamBarcode);
                }

                if (settingsApp != null) {
                    mBarcode.setPallet(settingsApp.getCurentPallet());
                }

            }

            return mBarcode;
        })
                .flatMap(barcode -> {
                    if (barcode.getId() != 0) {
                        return Observable.just(barcode);
                    } else {
                        return new SaveBarcodeInteractor(mProduct.getId(), barcode).getObservable();
                    }
                })
                .doOnNext(barcode -> {
                    mBarcode = Barcode.getBuilder()
                            .setId(barcode.getId())
                            .setBarcode(barcode.getBarcode())
                            .setDate(barcode.getDate())
                            .setWeight(barcode.getWeight())
                            .setPlaces(barcode.getPlaces())
                            .setPallet(barcode.getPallet())
                            .build();
                })
                .flatMap(barcode -> {

                    GetPaletSummInteractor getPaletSummInteractor =
                            new GetPaletSummInteractor(Long.parseLong(mParamIdProduct != null ? mParamIdProduct : "0"), barcode.getPallet());

                    return getPaletSummInteractor.getObservable();


                })
                .doOnNext(paletSumResult -> {
                    mPaletSumResult = paletSumResult;
                })
                .subscribeOn(AndroidSchedulers.mainThread()) //Schedulers.io()
                .observeOn(AndroidSchedulers.mainThread()) //AndroidSchedulers.mainThread()
                .subscribe(objectObservable -> {


                }, throwable -> {
                    getViewState().showSnackbarView(throwable.toString());
                }, () -> {
                    getViewState().refresh();
                });


    }


    public void refreshBarcode() {
        mBarcodeSeporator = new BarcodeSeporator(mBarcode.getBarcode(), mProduct);
        getViewState().refreshBarcodeView(mBarcodeSeporator);
    }

    public void setInputData(String idProduct, String idBurcode, String barcode) {
        mParamIdProduct = idProduct;
        mParamIdBarcode = idBurcode;
        mParamBarcode = barcode;
    }

    public CharSequence getId() {
        return mBarcode.getId() == 0 ? "( )" : Long.toString(mBarcode.getId());
    }

    public CharSequence getBarcode() {

        CharSequence charSequence = mBarcode.getBarcode() == null ? "" : mBarcode.getBarcode();

        if (mBarcodeSeporator != null) {
            if (mBarcodeSeporator.getFormatText() != null) {
                charSequence = mBarcodeSeporator.getFormatText();

            }
        }
        return charSequence;

    }

    public CharSequence getInfoProduct() {
        return mProduct.getName() != null ? mProduct.getName() : "";
    }

    public CharSequence getInfoPallet() {
        String info = String.format("№ %s         %s / %s",
                mBarcode.getPallet(), mPaletSumResult.getPlaces(), mPaletSumResult.getBigDecimalWeight());

        return info;

    }


    public CharSequence getSizeBarcode() {
        return mBarcode.getBarcode() == null ? "" : "Сим. " + mBarcode.getBarcode().length();
    }

    public CharSequence getWeight() {
        return mBarcode.getWeight() != 0 ? Float.toString(mBarcode.getWeight()) : "";
    }

    public CharSequence getSites() {
        return mBarcode.getPlaces() != 0 ? Integer.toString(mBarcode.getPlaces()) : "";
    }

    public CharSequence getPallet() {
        return mBarcode.getPallet() != 0 ? Integer.toString(mBarcode.getPallet()) : "";
    }

    public CharSequence getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy hh:mm:ss");
        String str = mBarcode.getDate() == null ? "" : dateFormat.format(mBarcode.getDate());

        return str;
    }

    public void changeBarcode(String barcode) {
        mBarcode.setBarcode(barcode);
        mBarcodeSeporator = new BarcodeSeporator(barcode, mProduct);
        if (!mBarcodeSeporator.getError()) {
            mBarcode.setWeight(mBarcodeSeporator.getWeight());
        }
    }

    public void changeWeight(String weight) {
        float fWeight = 0;
        try {
            fWeight = Float.parseFloat(weight.toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            fWeight = 0;
        }
        mBarcode.setWeight(fWeight);

    }

    public void changeSites(String sites) {
        int iSites = 0;
        try {
            iSites = Integer.parseInt(sites.toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            iSites = 0;
        }
        mBarcode.setPlaces(iSites);
    }

    public void changePallet(String pallet) {
        int i = 0;
        try {
            i = Integer.parseInt(pallet.toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            i = 0;
        }
        mBarcode.setPallet(i);

    }

    public void onClickSave() {
        saveSettings();
        SaveBarcodeInteractor interactor = new SaveBarcodeInteractor(mProduct.getId(), mBarcode);
        interactor.getObservable()
                .subscribeOn(AndroidSchedulers.mainThread()) //Schedulers.io()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(barcode -> {
                            getViewState().showSnackbarView("Сохранено");
                            getViewState().finishView();
                        },
                        throwable -> {
                            getViewState().showSnackbarView(throwable.toString());
                        });
    }


    public void onClickDell() {
        dell();
    }


    public void onClickCancel() {
        getViewState().finishView();
    }


    public void scanBarcode(String s) {
        //changeBarcode(s);
        //getViewState().refresh();

        Observable<Boolean> booleanObservable = new GetSettingsInteractor().getObservable()
                .flatMap(settingsApp -> {
                    settingsApp.setCurentPallet(mBarcode.getPallet());
                    return new SaveSettingsInteractor(settingsApp).getObservable();
                })
                .flatMap(o -> {
                    return new SaveBarcodeInteractor(mProduct.getId(), mBarcode).getObservable();
                })
                .map(o -> {
                    return true;
                });


        Observable.zip(booleanObservable, Observable.just(s), (barcodeSave, scancode) -> {
            return scancode;
        })
                .subscribe(s1 -> {


                    if (mControlLengthBK == true){
                        int lengthBk = mProduct.getInitBarcode()== null
                                ? 0 : mProduct.getInitBarcode().length();

                        if (lengthBk == 0){
                            getViewState().showErrorSnackbarView("Не установлен ШК в товаре!");
                        }else if (lengthBk != s1.length()){
                            getViewState().showErrorSnackbarView("Длинна ШК не совподает!");
                        }else{

                            getViewState().finishView();
                            getViewState().startDetailBarcodeForNewBarcodeScreenView(mParamIdProduct, s1);

                        }
                    }else{

                        getViewState().finishView();
                        getViewState().startDetailBarcodeForNewBarcodeScreenView(mParamIdProduct, s1);
                    }



                }, throwable -> {
                    getViewState().showSnackbarView(throwable.getMessage());
                });
    }


    public boolean onKeyListner(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            onClickSave();
        } else if (keyCode == KeyEvent.KEYCODE_ESCAPE) {
            onClickCancel();
        } else {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {


                try {
                    int i = Integer.parseInt(Character.toString(event.getNumber()));
                    executerCommand(i);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

            }
        }
        return false;
    }

    private void executerCommand(int number) {

    }

    private void dell() {


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


    public void saveSettings() {

        SettingsApp settingsApp;
        GetSettingsInteractor interactor = new GetSettingsInteractor();
        interactor.getObservable()
                .subscribeOn(AndroidSchedulers.mainThread()) //Schedulers.io()
                .observeOn(AndroidSchedulers.mainThread()) //AndroidSchedulers.mainThread()
                .flatMap(settings -> {
                    settings.setCurentPallet(mBarcode.getPallet());
                    return new SaveSettingsInteractor(settings).getObservable();
                })
                .subscribe();

    }

    public void onStart() {

    }

    public OkCancelDialog.BuilderInterface getOkCancelDialog() {
        return mOkCancelDialog;
    }

    @Override
    public void chouiceDialog(int id, Boolean positive) {


        if (id == OK_CANCEL_DI_DELL) {
            if (positive) {


                try {


                    DellBarcodeInteractor dellBarcodeInteractor = new DellBarcodeInteractor(mBarcode.getId());
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
                                        getViewState().finishView();
                                    });


                } catch (Exception e) {
                    e.printStackTrace();
                    getViewState().showSnackbarView(e.getMessage());
                }

            }
        }


        mOkCancelDialog = null;

        getViewState().startOkCancelDialog();

    }


}

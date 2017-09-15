package ru.a799000.android.weightlogic.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import ru.a799000.android.weightlogic.mvp.model.interactors.Interactor;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.DellBarcodeInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.DellPaletInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.GetAllPaletSummInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.GetProductByIdInteractor;
import ru.a799000.android.weightlogic.mvp.model.intities.Barcode;
import ru.a799000.android.weightlogic.mvp.model.intities.PaletSumResult;
import ru.a799000.android.weightlogic.mvp.model.intities.Product;
import ru.a799000.android.weightlogic.mvp.view.ReportPaletListScreenView;
import ru.a799000.android.weightlogic.ui.dialogs.OkCancelDialog;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Alex on 11.08.2017.
 */

@InjectViewState
public class ReportPaletListScreenPr extends MvpPresenter<ReportPaletListScreenView> implements OkCancelDialog.CallBackOkCancelDialog {
    String mIdProduct;

    OkCancelDialog.BuilderInterface mOkCancelDialog;
    final int OK_CANCEL_DI_DELL = 1;

    int mPositionForDell;

    List<PaletSumResult> mPaletSumResultList;

    Product mProduct;


    public void setID(String idProduct) {
        mIdProduct = idProduct;
    }


    private void refreshList() {
        Observable<PaletSumResult> oPaletSumResult = new GetAllPaletSummInteractor(Long.parseLong(mIdProduct)).getObservable();
//        Observable<Product> oProduct = new GetProductByIdInteractor(Long.parseLong(mIdProduct != null ? mIdProduct : "0")).getObservable();
//
//        Observable.zip(oPaletSumResult, oProduct, (paletSumResult, product) -> {
//            mProduct = product;
//
//            return paletSumResult;
//        });


        oPaletSumResult
                .subscribeOn(AndroidSchedulers.mainThread()) //Schedulers.io()
                .observeOn(AndroidSchedulers.mainThread()) //AndroidSchedulers.mainThread()
                .doOnNext(paletSumResult -> {
                    mProduct = paletSumResult.getProduct();
                })
                .toList()
                .subscribe(paletSumResults -> {
                    mPaletSumResultList = paletSumResults;
                    getViewState().refreshView(mPaletSumResultList);
                }, throwable -> {
                    getViewState().showSnackbarView(throwable.getMessage());
                });


    }

    public void setProduct(Product product) {
        mProduct = product;
    }

    public void onStart() {
        refreshList();
    }


    void executerCommand(int number, int position) {
        switch (number) {

            case 9:
                dellete(position);
                break;
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

    public void pressKey(char number, int position) {

        try {
            int i = Integer.parseInt(Character.toString(number));

            //getViewState().showSnackbarView("I = " + i + " position: " +  position);
            executerCommand(i, position);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public OkCancelDialog.BuilderInterface getOkCancelDialog() {
        return mOkCancelDialog;
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

            for (Barcode barcode : mProduct.getBarcodes()) {
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


                PaletSumResult paletSumResult = mPaletSumResultList.get(mPositionForDell);

                try {

                    DellPaletInteractor dellPaletInteractor =
                            new DellPaletInteractor(paletSumResult.getProduct().getId(),
                                    paletSumResult.getPullet());


                    dellPaletInteractor.getObservable()
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

    public CharSequence getInfoProduct() {
        if (mProduct == null) {
            return "";
        } else {

            String str = mProduct.getName();
            return str;
        }

    }
}

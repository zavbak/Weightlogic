package ru.a799000.android.weightlogic.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import ru.a799000.android.weightlogic.mvp.model.common.BarcodeSeporator;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.GetBarcodeByIDInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.GetProductByIdInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.SaveProductInteractor;
import ru.a799000.android.weightlogic.mvp.model.intities.Barcode;
import ru.a799000.android.weightlogic.mvp.model.intities.Product;
import ru.a799000.android.weightlogic.mvp.view.DetailBarcodeView;
import ru.a799000.android.weightlogic.mvp.view.DetailProductView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by user on 17.06.2017.
 */

@InjectViewState
public class DetailBarcodePr extends MvpPresenter<DetailBarcodeView> {
    String mIdProduct;
    String mIdBurcode;

    Product mProduct;
    Barcode mBarcode;

    BarcodeSeporator mBarcodeSeporator;


    public DetailBarcodePr() {
        mProduct = new Product();
        mBarcode = new Barcode();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        Observable<Product> oProduct = new GetProductByIdInteractor(Long.parseLong(mIdProduct)).getObservable();
        Observable<Barcode> oBarcode = new GetBarcodeByIDInteractor(Long.parseLong(mIdBurcode)).getObservable();

        Observable.zip(oProduct, oBarcode, (product, barcode) -> {
            init((Barcode) barcode, (Product) product);
            return Observable.empty();
        })
                .subscribeOn(AndroidSchedulers.mainThread()) //Schedulers.io()
                .observeOn(AndroidSchedulers.mainThread()) //AndroidSchedulers.mainThread()

                .subscribe(objectObservable -> {

                }, throwable -> {
                    getViewState().showSnackbarView(throwable.toString());
                }, () -> {
                    getViewState().refresh();
                    refreshBarcode();
                });


    }

    private void init(Barcode barcode, Product product) {

        if (barcode != null) {
            mBarcode = Barcode.getBuilder()
                    .setId(barcode.getId())
                    .setBarcode(barcode.getBarcode())
                    .setDate(barcode.getDate())
                    .setWeight(barcode.getWeight())
                    .setPlaces(barcode.getPlaces())
                    .build();
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
    }


    void refreshBarcode() {
        mBarcodeSeporator = new BarcodeSeporator(mBarcode.getBarcode(), mProduct);
        getViewState().refreshBarcodeView(mBarcodeSeporator);
    }

    public void setInputData(String idProduct, String idBurcode) {
        mIdProduct = idProduct;
        mIdBurcode = idBurcode;
    }

    public CharSequence getId() {
        return mBarcode.getId() == 0 ? "( )" : Long.toString(mBarcode.getId());
    }

    public CharSequence getBarcode() {


        CharSequence charSequence = mProduct.getInitBarcode() == null ? "" : mProduct.getInitBarcode();

        if (mBarcodeSeporator != null) {
            if (mBarcodeSeporator.getFormatText() != null) {
                charSequence = mBarcodeSeporator.getFormatText();

            }
        }
        return charSequence;

    }

    public CharSequence getInfoProduct() {
        return mProduct.getName()!=null? mProduct.getName():"";
    }


    public CharSequence getSizeBarcode() {
        return mProduct.getInitBarcode() == null ? "" : "Сим. " + mProduct.getInitBarcode().length();
    }

    public CharSequence getWeight() {
        return mBarcode.getWeight()!=0?Float.toString(mBarcode.getWeight()):"";
    }

    public CharSequence getSites() {
         return mBarcode.getPlaces()!=0?Integer.toString(mBarcode.getPlaces()):"";
    }

    public void changeBarcode(String barcode) {
        mBarcode.setBarcode(barcode);
        refreshBarcode();
    }

    public void onClickSave() {

//        SaveProductInteractor interactor = new SaveProductInteractor(mProduct);
//        interactor.getObservable()
//                .subscribeOn(AndroidSchedulers.mainThread()) //Schedulers.io()
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(product -> {
//                            getViewState().showSnackbarView("Сохранено");
//                        },
//                        throwable -> {
//                            getViewState().showSnackbarView(throwable.toString());
//                        });
//
//
//        getViewState().finishView();
    }


    public void onClickCancel() {
        getViewState().finishView();
    }



}

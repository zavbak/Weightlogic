package ru.a799000.android.weightlogic.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.text.SimpleDateFormat;
import java.util.Date;

import ru.a799000.android.weightlogic.mvp.model.common.BarcodeSeporator;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.GetBarcodeByIDInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.GetProductByIdInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.SaveBarcodeInteractor;
import ru.a799000.android.weightlogic.mvp.model.intities.Barcode;
import ru.a799000.android.weightlogic.mvp.model.intities.Product;
import ru.a799000.android.weightlogic.mvp.view.DetailBarcodeView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by user on 17.06.2017.
 */

@InjectViewState
public class DetailBarcodePr extends MvpPresenter<DetailBarcodeView> {
    String mIdProduct;
    String mIdBarcode;

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

        Observable<Product> oProduct = new GetProductByIdInteractor(Long.parseLong(mIdProduct!=null?mIdProduct:"0")).getObservable();
        Observable<Barcode> oBarcode = new GetBarcodeByIDInteractor(Long.parseLong(mIdBarcode!=null?mIdBarcode:"0")).getObservable();

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


    public void refreshBarcode() {
        mBarcodeSeporator = new BarcodeSeporator(mBarcode.getBarcode(), mProduct);
        getViewState().refreshBarcodeView(mBarcodeSeporator);
    }

    public void setInputData(String idProduct, String idBurcode) {
        mIdProduct = idProduct;
        mIdBarcode = idBurcode;
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

    public CharSequence getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy hh:mm:ss");
        String str = mBarcode.getDate()==null?"":dateFormat.format(mBarcode.getDate());

        return str;
    }

    public void changeBarcode(String barcode) {
        mBarcode.setBarcode(barcode);
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

    public void onClickSave() {
        mBarcode.setDate(new Date());
        SaveBarcodeInteractor interactor = new SaveBarcodeInteractor(mProduct.getId(),mBarcode);
        interactor.getObservable()
                .subscribeOn(AndroidSchedulers.mainThread()) //Schedulers.io()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(barcode -> {
                            getViewState().showSnackbarView("Сохранено");
                        },
                        throwable -> {
                            getViewState().showSnackbarView(throwable.toString());
                        });

          getViewState().finishView();
    }


    public void onClickCancel() {
        getViewState().finishView();
    }



}

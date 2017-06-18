package ru.a799000.android.weightlogic.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import ru.a799000.android.weightlogic.mvp.model.common.BarcodeSeporator;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.GetProductByIdInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.SaveBarcodeInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.SaveProductInteractor;
import ru.a799000.android.weightlogic.mvp.model.intities.Product;
import ru.a799000.android.weightlogic.mvp.view.DetailProductView;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by user on 17.06.2017.
 */

@InjectViewState
public class DetailProductPr extends MvpPresenter<DetailProductView> {
    String mId;
    Product mProduct;

    BarcodeSeporator mBarcodeSeporator;


    public DetailProductPr() {
        mProduct = new Product();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        if(mId != null){
            GetProductByIdInteractor interactor = new GetProductByIdInteractor(Long.parseLong(mId));
            interactor.getObservable()
                    .subscribeOn(AndroidSchedulers.mainThread()) //Schedulers.io()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::initProduct,
                            throwable -> {getViewState().showSnackbarView(throwable.toString());});//AndroidSchedulers.mainThread()

        }


    }


    void initProduct(Product product) {
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


        getViewState().refreshProduct();
        refreshBarcode();

    }


    void refreshBarcode(){
        mBarcodeSeporator = new BarcodeSeporator(mProduct.getInitBarcode(),mProduct);
        getViewState().refreshBarcodeView(mBarcodeSeporator);
    }

    public void setID(String ID) {
        mId = ID;
    }


    public CharSequence getName() {
        return mProduct.getName()==null?"":mProduct.getName();
    }



    public CharSequence getId() {
        return mProduct.getId()==0?"( )":Long.toString(mProduct.getId());
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

    public CharSequence getStart() {
        return mProduct.getStart()==0?"":Integer.toString(mProduct.getStart());
    }

    public CharSequence getFinish() {
        return mProduct.getFinish() == 0 ? "" : Integer.toString(mProduct.getFinish());
    }

    public CharSequence getCoef() {
        return mProduct.getCoef()==0?"":Float.toString(mProduct.getCoef());
    }

    public CharSequence getCodes() {
        String sCode = mProduct.getCode() ==null? "": "Код 1С: " + mProduct.getCode();

        return "(" + getId()+")" + "    " +  sCode;
    }

    public CharSequence  getSizeBarcode() {
        return mProduct.getInitBarcode()==null?"":"Сим. " + mProduct.getInitBarcode().length();
    }

    public void changeBarcode(String barcode){
        mProduct.setInitBarcode(barcode);
        refreshBarcode();
    }

    public void changeName(String name) {
        mProduct.setName(name);
    }

    public void changeStart(String start) {
        int iStars = 0;
        try {
            iStars = Integer.parseInt(start.toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            iStars = 0;
        }
        mProduct.setStart(iStars);
        refreshBarcode();
    }

    public void changeFinish(String finish) {
        int iFinish = 0;
        try {
            iFinish = Integer.parseInt(finish.toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            iFinish = 0;
        }
        mProduct.setFinish(iFinish);
        refreshBarcode();
    }

    public void changeCoef(String coef) {
        float iCoef = 0;
        try {
            iCoef = Float.parseFloat(coef.toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            iCoef = 0;
        }
        mProduct.setCoef(iCoef);
        refreshBarcode();
    }

    public void onClickSave() {

        SaveProductInteractor interactor = new SaveProductInteractor(mProduct);
        interactor.getObservable()
                .subscribeOn(AndroidSchedulers.mainThread()) //Schedulers.io()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(product -> {getViewState().showSnackbarView("Сохранено");},
                        throwable -> {getViewState().showSnackbarView(throwable.toString());});


        getViewState().finishView();
    }


    public void onClickCancel() {
        getViewState().finishView();
    }
}

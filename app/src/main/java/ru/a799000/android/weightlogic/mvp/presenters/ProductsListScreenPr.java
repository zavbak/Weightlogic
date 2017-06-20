package ru.a799000.android.weightlogic.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import io.realm.RealmResults;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.DellProductByIdInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.GetAllProductInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.GetProductByIdInteractor;
import ru.a799000.android.weightlogic.mvp.model.intities.Product;
import ru.a799000.android.weightlogic.mvp.view.ProductsListScreenView;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by user on 17.06.2017.
 */

@InjectViewState
public class ProductsListScreenPr extends MvpPresenter<ProductsListScreenView> {

    RealmResults<Product> mData;
    String mId;
    int positionCurent;



    private void refreshList() {
        GetAllProductInteractor interactor = new GetAllProductInteractor();
        interactor.getObservable()
                .subscribeOn(AndroidSchedulers.mainThread()) //Schedulers.io()
                .observeOn(AndroidSchedulers.mainThread()) //AndroidSchedulers.mainThread()
                .subscribe(
                        resultO -> {
                            mData = (RealmResults<Product>) resultO;
                            getViewState().refreshView(mData);
                            setListPosition();

                        }
                        , throwable ->
                                getViewState().showInfoView(throwable.getMessage()));


    }


    void setListPosition(){
        if(mId != null){
            int i = 0;
            for(Product product:mData){
                if(product.getId()==Long.parseLong(mId)){
                    positionCurent = i;
                    getViewState().setListPosition();
                }
                i = i +1;
            }
        }
    }


    public void onStart() {
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

        DellProductByIdInteractor interactor = new DellProductByIdInteractor(mData.get(position).getId());
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
    }


    public void onClickProduct(int position) {
        getViewState().startDetailProductScreenView(Long.toString(mData.get(position).getId()));
    }

    public void setID(String id) {
        mId = id;
    }

    public int getSelectionPosition() {
        return positionCurent;
    }

    public void scanBarcode(String s) {
        getViewState().showSnackbarView(s);
    }
}

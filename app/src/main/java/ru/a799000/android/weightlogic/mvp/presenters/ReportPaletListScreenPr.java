package ru.a799000.android.weightlogic.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import ru.a799000.android.weightlogic.mvp.model.interactors.Interactor;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.GetAllPaletSummInteractor;
import ru.a799000.android.weightlogic.mvp.model.intities.PaletSumResult;
import ru.a799000.android.weightlogic.mvp.view.ReportPaletListScreenView;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Alex on 11.08.2017.
 */

@InjectViewState
public class ReportPaletListScreenPr extends MvpPresenter<ReportPaletListScreenView> {
    String mIdProduct;

    public void setID(String idProduct) {
        mIdProduct = idProduct;
    }


    private void refreshList() {
        Interactor<PaletSumResult> interactor = new GetAllPaletSummInteractor(Long.parseLong(mIdProduct));
        interactor.getObservable()
                .subscribeOn(AndroidSchedulers.mainThread()) //Schedulers.io()
                .observeOn(AndroidSchedulers.mainThread()) //AndroidSchedulers.mainThread()
                .toList()
                .subscribe(paletSumResults -> {
                    getViewState().refreshView(paletSumResults);
                },throwable -> {
                    getViewState().showSnackbarView(throwable.getMessage());
                });


    }

    public void onStart() {
        refreshList();
    }
}

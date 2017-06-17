package ru.a799000.android.weightlogic.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.a799000.android.weightlogic.mvp.view.MainAcView;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Alex on 16.06.2017.
 */

@InjectViewState
public class MainAcPr extends MvpPresenter<MainAcView> {


    Observable<String> clickEventObservable = Observable.create(new Observable.OnSubscribe<String>() {
        @Override
        public void call(final Subscriber<? super String> subscriber) {

        }
    });

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().startGeneralSreenView();
        
    }

    public void onScanBarcode(String barcode) {
        getViewState().showSnackbarView(barcode);

    }
}

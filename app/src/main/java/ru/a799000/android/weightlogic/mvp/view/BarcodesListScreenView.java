package ru.a799000.android.weightlogic.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import io.realm.OrderedRealmCollection;
import ru.a799000.android.weightlogic.mvp.model.intities.Barcode;


/**
 * Created by user on 17.06.2017.
 */

public interface BarcodesListScreenView extends MvpView {


    @StateStrategyType(OneExecutionStateStrategy.class)
    void refreshView(OrderedRealmCollection<Barcode> list);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showInfoView(String messager);


    @StateStrategyType(SkipStrategy.class)
    void showSnackbarView(String messager);


    @StateStrategyType(SkipStrategy.class)
    void startDetailBarcodeScreenView(String idProduct,String idBarcode);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setListPosition();
}

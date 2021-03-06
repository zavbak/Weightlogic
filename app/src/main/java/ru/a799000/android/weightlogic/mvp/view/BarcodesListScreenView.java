package ru.a799000.android.weightlogic.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import io.realm.OrderedRealmCollection;
import ru.a799000.android.weightlogic.mvp.model.intities.Barcode;
import ru.a799000.android.weightlogic.ui.dialogs.OkCancelDialog;


/**
 * Created by user on 17.06.2017.
 */

public interface BarcodesListScreenView extends MvpView {


    @StateStrategyType(OneExecutionStateStrategy.class)
    void refreshView(OrderedRealmCollection<Barcode> list);

    @StateStrategyType(AddToEndSingleStrategy .class)
    void startOkCancelDialog();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showInfoView(String messager);


    @StateStrategyType(SkipStrategy.class)
    void showSnackbarView(String messager);


    @StateStrategyType(SkipStrategy.class)
    void startDetailBarcodeScreenView(String idProduct,String idBarcode);

    @StateStrategyType(SkipStrategy.class)
    void startDetailBarcodeForNewBarcodeScreenView(String idProduct, String barcode);

    @StateStrategyType(SkipStrategy.class)
    void startReportPalet(String paramIdProduct);

    @StateStrategyType(SkipStrategy.class)
    void showErrorSnackbarView(String messager);
}

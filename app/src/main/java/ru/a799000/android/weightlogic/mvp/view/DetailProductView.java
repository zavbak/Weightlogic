package ru.a799000.android.weightlogic.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.a799000.android.weightlogic.mvp.model.common.BarcodeSeporator;

/**
 * Created by user on 17.06.2017.
 */

public interface DetailProductView extends MvpView{

    @StateStrategyType(AddToEndSingleStrategy.class)
    void refresh();

    @StateStrategyType(SkipStrategy.class)
    void showSnackbarView(String messager);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void refreshBarcodeView(BarcodeSeporator barcodeSeporator);

    @StateStrategyType(SkipStrategy.class)
    void finishView();
}

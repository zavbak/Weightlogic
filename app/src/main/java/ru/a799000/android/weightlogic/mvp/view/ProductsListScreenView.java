package ru.a799000.android.weightlogic.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import io.realm.OrderedRealmCollection;
import ru.a799000.android.weightlogic.mvp.model.intities.Product;

/**
 * Created by user on 17.06.2017.
 */

public interface ProductsListScreenView extends MvpView {


    @StateStrategyType(OneExecutionStateStrategy.class)
    void refreshView(OrderedRealmCollection<Product> list);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showInfoView(String messager);


    @StateStrategyType(SkipStrategy.class)
    void showSnackbarView(String messager);


    @StateStrategyType(SkipStrategy.class)
    void startDetailProductScreenView(String id);

    @StateStrategyType(SkipStrategy.class)
    void startListScreenBarcodes(String s);

    @StateStrategyType(AddToEndSingleStrategy .class)
    void startOkCancelDialog();





}

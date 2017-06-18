package ru.a799000.android.weightlogic.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

/**
 * Created by Alex on 16.06.2017.
 */

public interface MainAcView extends MvpView{

    @StateStrategyType(OneExecutionStateStrategy.class)
    void startGeneralSreenView();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void startTestScreenView();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void startProductsScreenView(String id);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void startSettingsScreenView();



    @StateStrategyType(SkipStrategy.class)
    void showSnackbarView(String messager);



}

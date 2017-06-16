package ru.a799000.android.weightlogic.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

/**
 * Created by Alex on 16.06.2017.
 */

public interface GeneralScreenView extends MvpView{
    @StateStrategyType(OneExecutionStateStrategy.class)
    void refreshView(List<String> list);

    void showMessageView(String messager);
}

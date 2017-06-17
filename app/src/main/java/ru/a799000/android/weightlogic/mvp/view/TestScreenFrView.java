package ru.a799000.android.weightlogic.mvp.view;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

/**
 * Created by user on 17.06.2017.
 */

public interface TestScreenFrView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showTvMessageView(@NonNull CharSequence text);
}

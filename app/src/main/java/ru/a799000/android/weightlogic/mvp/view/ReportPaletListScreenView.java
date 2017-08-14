package ru.a799000.android.weightlogic.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import io.realm.OrderedRealmCollection;
import ru.a799000.android.weightlogic.mvp.model.intities.PaletSumResult;
import ru.a799000.android.weightlogic.mvp.model.intities.Product;

/**
 * Created by Alex on 11.08.2017.
 */

public interface ReportPaletListScreenView extends MvpView {
    @StateStrategyType(OneExecutionStateStrategy.class)
    void refreshView(List<PaletSumResult> lv);

    @StateStrategyType(SkipStrategy.class)
    void showSnackbarView(String messager);

}

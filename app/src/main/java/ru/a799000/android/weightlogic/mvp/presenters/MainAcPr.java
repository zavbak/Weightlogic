package ru.a799000.android.weightlogic.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import ru.a799000.android.weightlogic.mvp.view.MainAcView;

/**
 * Created by Alex on 16.06.2017.
 */

@InjectViewState
public class MainAcPr extends MvpPresenter<MainAcView> {

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().startGeneralSreenView();
    }
}

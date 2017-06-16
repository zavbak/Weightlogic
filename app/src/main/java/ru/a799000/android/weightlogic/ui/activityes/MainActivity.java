package ru.a799000.android.weightlogic.ui.activityes;

import android.os.Bundle;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.presenter.InjectPresenter;

import ru.a799000.android.weightlogic.R;
import ru.a799000.android.weightlogic.mvp.presenters.MainAcPr;
import ru.a799000.android.weightlogic.mvp.view.MainAcView;


public class MainActivity extends MvpAppCompatActivity implements MainAcView {

    @InjectPresenter
    MainAcPr mPresenter;

    RouterScreen mRouterScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRouterScreen = new RouterScreen(this);
    }


    @Override
    public void startGeneralSreenView() {
        mRouterScreen.startGeneralScreen();
    }
}

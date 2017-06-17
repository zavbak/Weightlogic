package ru.a799000.android.weightlogic.ui.activityes;

import android.os.Bundle;
import android.view.MenuItem;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.presenter.InjectPresenter;

import ru.a799000.android.weightlogic.R;
import ru.a799000.android.weightlogic.mvp.presenters.MainAcPr;
import ru.a799000.android.weightlogic.mvp.view.MainAcView;


public class MainActivity extends MvpAppCompatActivity implements MainAcView,CallBackScreens {

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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }


    @Override
    public void startGeneralSreenView() {
        mRouterScreen.startGeneralScreen();
    }

    @Override
    public void startTestScreenView() {
        mRouterScreen.startTestScreen();
    }

    @Override
    public void startProductsScreenView() {
        mRouterScreen.startProductsScreen();
    }

    @Override
    public void startDetailProductScreenView(String id) {
        mRouterScreen.startDetailProductScreen(id);
    }


}

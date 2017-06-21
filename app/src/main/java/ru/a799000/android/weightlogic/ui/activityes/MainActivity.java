package ru.a799000.android.weightlogic.ui.activityes;

import android.appwidget.AppWidgetHostView;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.MenuItem;

import com.arellomobile.mvp.MvpAppCompatActivity;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.f2prateek.rx.receivers.RxBroadcastReceiver;


import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.realm.RealmResults;
import ru.a799000.android.weightlogic.R;
import ru.a799000.android.weightlogic.mvp.model.intities.Product;
import ru.a799000.android.weightlogic.mvp.presenters.MainAcPr;
import ru.a799000.android.weightlogic.mvp.view.MainAcView;
import ru.a799000.android.weightlogic.repository.barcode.BarcodeDataBroadcastReceiver;
import rx.Observable;
import rx.subjects.AsyncSubject;
import rx.subjects.PublishSubject;


public class MainActivity extends MvpAppCompatActivity implements MainAcView,CallBackScreens,GetDataActivity {

    @InjectPresenter
    MainAcPr mPresenter;

    RouterScreen mRouterScreen;

    //BarcodeDataBroadcastReceiver mBarcodeDataBroadcastReceiver;
    //Observable<String> mObservableBarcode;


    BarcodeDataBroadcastReceiver mBarcodeDataBroadcastReceiver;
    PublishSubject<String> mBarcodeSubject = PublishSubject.create();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRouterScreen = new RouterScreen(this);

        mBarcodeDataBroadcastReceiver = new BarcodeDataBroadcastReceiver(barcode -> {
            String g = barcode;
            mBarcodeSubject.onNext(barcode);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter1 = new IntentFilter("DATA_SCAN");
        registerReceiver(mBarcodeDataBroadcastReceiver, intentFilter1);
    }

    @Override
    public PublishSubject<String> getObservableBarcode() {
        return mBarcodeSubject;
    }


    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mBarcodeDataBroadcastReceiver);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

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
    public void startProductsScreenView(String id) {
        mRouterScreen.startProductsScreen(id);
    }

    @Override
    public void startSettingsScreenView() {
        mRouterScreen.startSettingsScreen();
    }

    @Override
    public void startListScreenBarcodes(String id) {
        mRouterScreen.startListScreenBarcodes(id);
    }

    @Override
    public void startBarcodeProductScreenView(String idProduct, String idBarcode) {
        mRouterScreen.startDetailBarcodeScreen(idProduct,idBarcode);
    }

    @Override
    public void startDetailBarcodeForNewBarcodeScreenView(String idProduct, String barcode) {
        mRouterScreen.startDetailBarcodeForNewBarcodeScreenView(idProduct,barcode);
    }


    @Override
    public void showSnackbarView(String messager) {
        Snackbar.make(findViewById(R.id.root), messager, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void startDetailProductScreenView(String id) {
        mRouterScreen.startDetailProductScreen(id);
    }

    @Override
    public void backStack() {
        mRouterScreen.backStack();
    }


}

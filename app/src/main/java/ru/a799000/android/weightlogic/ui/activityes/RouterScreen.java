package ru.a799000.android.weightlogic.ui.activityes;

import android.support.v7.app.AppCompatActivity;

import ru.a799000.android.weightlogic.R;
import ru.a799000.android.weightlogic.mvp.view.SettingsScreenView;
import ru.a799000.android.weightlogic.ui.fragments.BarcodesListScreenFr;
import ru.a799000.android.weightlogic.ui.fragments.DetailBarcodeScreenFr;
import ru.a799000.android.weightlogic.ui.fragments.DetailProductScreenFr;
import ru.a799000.android.weightlogic.ui.fragments.GeneralScreenFr;
import ru.a799000.android.weightlogic.ui.fragments.ProductsListScreenFr;
import ru.a799000.android.weightlogic.ui.fragments.SettingsScreenFr;
import ru.a799000.android.weightlogic.ui.fragments.TestScreenFr;

/**
 * Created by Alex on 16.06.2017.
 */

public class RouterScreen {
    AppCompatActivity mActivity;

    public RouterScreen(AppCompatActivity activity) {
        mActivity = activity;
    }

    void startGeneralScreen(){
        mActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment, GeneralScreenFr.getInstance(),GeneralScreenFr.TAG)
                .commit();
    }


    void startTestScreen(){
        mActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment, TestScreenFr.getInstance(),TestScreenFr.TAG)
                .addToBackStack("myBackStack")
                .commit();
    }


    void startProductsScreen(String id){
        mActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment, ProductsListScreenFr.getInstance(id), ProductsListScreenFr.TAG)
                .addToBackStack("myBackStack")
                .commit();
    }


    void startDetailProductScreen(String id){


            mActivity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment, DetailProductScreenFr.getInstance(id), DetailProductScreenFr.TAG)
                    .addToBackStack("myBackStack")
                    .commit();

    }

    void startSettingsScreen(){

        mActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment, SettingsScreenFr.getInstance(), SettingsScreenFr.TAG)
                .addToBackStack("myBackStack")
                .commit();

    }


    public void backStack() {
        if (mActivity.getSupportFragmentManager().getBackStackEntryCount() > 0) {
            mActivity.getSupportFragmentManager().popBackStack();
        } else {
            mActivity.onBackPressed();
        }
    }

    public void startListScreenBarcodes(String id) {
        mActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment, BarcodesListScreenFr.getInstance(id), BarcodesListScreenFr.TAG)
                .addToBackStack("myBackStack")
                .commit();
    }

    public void startDetailBarcodeScreen(String idProduct,String idBarcode) {
        mActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment, DetailBarcodeScreenFr.getInstance(idProduct,idBarcode,null), DetailBarcodeScreenFr.TAG)
                .addToBackStack("myBackStack")
                .commit();
    }

    public void startDetailBarcodeForNewBarcodeScreenView(String idProduct, String barcode) {
        mActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment, DetailBarcodeScreenFr.getInstance(idProduct,null,barcode), DetailBarcodeScreenFr.TAG)
                .addToBackStack("myBackStack")
                .commit();

    }
}

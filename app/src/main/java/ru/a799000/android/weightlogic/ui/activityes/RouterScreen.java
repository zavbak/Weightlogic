package ru.a799000.android.weightlogic.ui.activityes;

import android.support.v7.app.AppCompatActivity;

import ru.a799000.android.weightlogic.R;
import ru.a799000.android.weightlogic.ui.fragments.DetailProductScreenFr;
import ru.a799000.android.weightlogic.ui.fragments.GeneralScreenFr;
import ru.a799000.android.weightlogic.ui.fragments.ProductsListScreenFr;
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


    void startProductsScreen(){
        mActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment, ProductsListScreenFr.getInstance(), ProductsListScreenFr.TAG)
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

}

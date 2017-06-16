package ru.a799000.android.weightlogic.ui.activityes;

import android.support.v7.app.AppCompatActivity;

import ru.a799000.android.weightlogic.R;
import ru.a799000.android.weightlogic.ui.fragments.GeneralScreenFr;

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

}

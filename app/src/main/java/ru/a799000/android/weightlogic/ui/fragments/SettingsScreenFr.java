package ru.a799000.android.weightlogic.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import ru.a799000.android.weightlogic.R;
import ru.a799000.android.weightlogic.mvp.presenters.SettingsScreenPr;
import ru.a799000.android.weightlogic.mvp.view.SettingsScreenView;
import ru.a799000.android.weightlogic.ui.activityes.CallBackScreens;
import ru.a799000.android.weightlogic.ui.activityes.MainActivity;

/**
 * Created by user on 17.06.2017.
 */

public class SettingsScreenFr extends MvpAppCompatFragment implements SettingsScreenView {
    public static final String TAG = "SettingsScreenFr";

    @InjectPresenter
    SettingsScreenPr mPresenter;

    CallBackScreens mCallBackScreens;

    public static SettingsScreenFr getInstance() {
        SettingsScreenFr fragment = new SettingsScreenFr();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings, container, false);

        Bundle args = getArguments();
        mCallBackScreens = (CallBackScreens) getActivity();
        return view;
    }

}

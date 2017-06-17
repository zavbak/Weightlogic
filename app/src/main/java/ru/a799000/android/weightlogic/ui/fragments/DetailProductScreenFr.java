package ru.a799000.android.weightlogic.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.a799000.android.weightlogic.R;

import ru.a799000.android.weightlogic.mvp.presenters.DetailProductPr;
import ru.a799000.android.weightlogic.mvp.view.DetailProductView;
import ru.a799000.android.weightlogic.ui.activityes.CallBackScreens;

/**
 * Created by user on 17.06.2017.
 */

public class DetailProductScreenFr extends MvpAppCompatFragment implements DetailProductView {

    public static final String TAG = "DetailProductScreenFr";
    static final String ID = "DetailProductScreenFr";

    CallBackScreens mCallBackScreens;


    @InjectPresenter
    DetailProductPr mPresenter;


    public static DetailProductScreenFr getInstance(String id) {
        DetailProductScreenFr fragment = new DetailProductScreenFr();
        Bundle args = new Bundle();
        args.getCharSequence(ID,id);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_product, container, false);

        Bundle args = getArguments();
        mPresenter.setID(args.getString(ID));

        mCallBackScreens = (CallBackScreens) getActivity();

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

}

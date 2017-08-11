package ru.a799000.android.weightlogic.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import ru.a799000.android.weightlogic.R;
import ru.a799000.android.weightlogic.mvp.presenters.ProductsListScreenPr;
import ru.a799000.android.weightlogic.mvp.presenters.ReportPaletListScreenPr;
import ru.a799000.android.weightlogic.mvp.view.ProductsListScreenView;
import ru.a799000.android.weightlogic.mvp.view.ReportPaletListScreenView;
import ru.a799000.android.weightlogic.ui.activityes.CallBackScreens;

/**
 * Created by Alex on 11.08.2017.
 */

public class ReportPaletListScreenFr extends MvpAppCompatFragment implements ReportPaletListScreenView {

    public static final String TAG = "ReportPaletListScreenFr";
    static final String ID = "id";


    @InjectPresenter
    ReportPaletListScreenPr mPresenter;


    public static ReportPaletListScreenFr getInstance(String id) {
        ReportPaletListScreenFr fragment = new ReportPaletListScreenFr();
        Bundle args = new Bundle();
        args.putString(ID, id);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_palet_report_screen, container, false);

        Bundle args = getArguments();
        //mPresenter.setID(args.getString(ID));

        return view;
    }






}

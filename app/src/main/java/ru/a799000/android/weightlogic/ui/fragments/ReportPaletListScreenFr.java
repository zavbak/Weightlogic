package ru.a799000.android.weightlogic.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import ru.a799000.android.weightlogic.R;
import ru.a799000.android.weightlogic.mvp.model.intities.PaletSumResult;
import ru.a799000.android.weightlogic.mvp.model.intities.Product;
import ru.a799000.android.weightlogic.mvp.presenters.ProductsListScreenPr;
import ru.a799000.android.weightlogic.mvp.presenters.ReportPaletListScreenPr;
import ru.a799000.android.weightlogic.mvp.view.ProductsListScreenView;
import ru.a799000.android.weightlogic.mvp.view.ReportPaletListScreenView;
import ru.a799000.android.weightlogic.ui.activityes.CallBackScreens;
import ru.a799000.android.weightlogic.ui.activityes.MainActivity;
import ru.a799000.android.weightlogic.ui.adapters.AdaprerReportPalet;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Alex on 11.08.2017.
 */

public class ReportPaletListScreenFr extends MvpAppCompatFragment implements ReportPaletListScreenView {

    public static final String TAG = "ReportPaletListScreenFr";
    static final String ID = "id";


    @InjectPresenter
    ReportPaletListScreenPr mPresenter;

    @BindView(R.id.lv)
    ListView mListView;


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
        mPresenter.setID(args.getString(ID));
        return view;

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

    }

    @Override
    public void onResume() {
        super.onResume();
        init();
        mPresenter.onStart();
    }

    void init() {

    }


    @Override
    public void refreshView(List<PaletSumResult> lv) {
        AdaprerReportPalet adaprerReportPalet = new AdaprerReportPalet(getActivity(),lv);
        mListView.setAdapter(adaprerReportPalet);

    }

    @Override
    public void showSnackbarView(String messager) {
        Snackbar.make(getView(), messager, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }


}

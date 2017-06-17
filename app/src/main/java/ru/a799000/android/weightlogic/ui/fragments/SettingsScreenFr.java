package ru.a799000.android.weightlogic.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.jakewharton.rxbinding.widget.RxTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.a799000.android.weightlogic.R;
import ru.a799000.android.weightlogic.mvp.presenters.SettingsScreenPr;
import ru.a799000.android.weightlogic.mvp.view.SettingsScreenView;
import ru.a799000.android.weightlogic.ui.activityes.CallBackScreens;
import ru.a799000.android.weightlogic.ui.activityes.MainActivity;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by user on 17.06.2017.
 */

public class SettingsScreenFr extends MvpAppCompatFragment implements SettingsScreenView {
    public static final String TAG = "SettingsScreenFr";

    @InjectPresenter
    SettingsScreenPr mPresenter;

    @BindView(R.id.edFileName)
    EditText edFileName;

    CallBackScreens mCallBackScreens;

    private CompositeSubscription mCompositeSubscription;

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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initSubscription();
    }

    void initSubscription(){
        mCompositeSubscription = new CompositeSubscription();

        mCompositeSubscription.add(RxTextView.textChanges(edFileName)
                .filter(charSequence -> (!charSequence.toString().equals(mPresenter.getFileName())))
                .subscribe(charSequence -> mPresenter.changeFileName(charSequence.toString())));

    }

    @Override
    public void refresh() {
        edFileName.setText(mPresenter.getFileName());
    }

    @OnClick(R.id.btSave)
    void onClickSave(){
        mPresenter.onClickSave();
    }


    @OnClick(R.id.btCancel)
    void onClickCancel(){
        mPresenter.onClickCancel();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeSubscription.unsubscribe();
    }

    @Override
    public void showSnackbarView(String messager) {
        Snackbar.make(getView(), messager, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void finishView() {
        mCallBackScreens.backStack();
    }

}

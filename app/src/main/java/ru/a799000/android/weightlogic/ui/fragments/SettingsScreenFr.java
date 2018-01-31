package ru.a799000.android.weightlogic.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

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

    @BindView(R.id.edServerName)
    EditText edServerName;

    @BindView(R.id.edNameBase)
    EditText edNameBase;

    @BindView(R.id.edCode)
    EditText edCode;

    @BindView(R.id.edLogin)
    EditText edLogin;

    @BindView(R.id.edPass)
    EditText edPass;

    @BindView(R.id.swControlLengthBK)
    Switch swControlLengthBK;

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
    }

    void init(){
        mCompositeSubscription = new CompositeSubscription();

        mCompositeSubscription.add(RxTextView.textChanges(edFileName)
                .filter(charSequence -> (!charSequence.toString().equals(mPresenter.getFileName())))
                .subscribe(charSequence -> mPresenter.changeFileName(charSequence.toString())));

        mCompositeSubscription.add(RxTextView.textChanges(edServerName)
                .filter(charSequence -> (!charSequence.toString().equals(mPresenter.getServerName())))
                .subscribe(charSequence -> mPresenter.changeServerName(charSequence.toString())));

        mCompositeSubscription.add(RxTextView.textChanges(edNameBase)
                .filter(charSequence -> (!charSequence.toString().equals(mPresenter.getNameBase())))
                .subscribe(charSequence -> mPresenter.changeNameBase(charSequence.toString())));


        mCompositeSubscription.add(RxTextView.textChanges(edCode)
                .filter(charSequence -> (!charSequence.toString().equals(mPresenter.getCode())))
                .subscribe(charSequence -> mPresenter.changeCode(charSequence.toString())));


        mCompositeSubscription.add(RxTextView.textChanges(edLogin)
                .filter(charSequence -> (!charSequence.toString().equals(mPresenter.getLogin())))
                .subscribe(charSequence -> mPresenter.changeLogin(charSequence.toString())));

        mCompositeSubscription.add(RxTextView.textChanges(edPass)
                .filter(charSequence -> (!charSequence.toString().equals(mPresenter.getPass())))
                .subscribe(charSequence -> mPresenter.changePass(charSequence.toString())));

        swControlLengthBK.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mPresenter.setControlLengthBK(isChecked);
            }
        });



    }

    @Override
    public void refresh() {
        edFileName.setText(mPresenter.getFileName());
        edServerName.setText(mPresenter.getServerName());
        edNameBase.setText(mPresenter.getNameBase());
        edCode.setText(mPresenter.getCode());
        edLogin.setText(mPresenter.getLogin());
        edPass.setText(mPresenter.getPass());
        swControlLengthBK.setChecked(mPresenter.getControlLengthBK());

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
    public void onResume() {
        super.onResume();
        init();
    }

    @Override
    public void onPause() {
        super.onPause();
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

package ru.a799000.android.weightlogic.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.jakewharton.rxbinding.widget.RxTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import ru.a799000.android.weightlogic.R;

import ru.a799000.android.weightlogic.mvp.model.common.BarcodeSeporator;
import ru.a799000.android.weightlogic.mvp.presenters.DetailProductPr;
import ru.a799000.android.weightlogic.mvp.view.DetailProductView;
import ru.a799000.android.weightlogic.ui.activityes.CallBackScreens;
import ru.a799000.android.weightlogic.ui.activityes.MainActivity;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by user on 17.06.2017.
 */

public class DetailProductScreenFr extends MvpAppCompatFragment implements DetailProductView, View.OnKeyListener {

    public static final String TAG = "DetailProductScreenFr";
    static final String ID = "id";

    @BindView(R.id.edName)
    EditText edName;

    @BindView(R.id.tvId)
    TextView tvId;

    @BindView(R.id.tvMessageBarcode)
    TextView tvMessageBarcode;


    @BindView(R.id.edBarcode)
    EditText edBarcode;

    @BindView(R.id.edStart)
    EditText edStart;

    @BindView(R.id.edFinish)
    EditText edFinish;

    @BindView(R.id.edCoef)
    EditText edCoef;

    @InjectPresenter
    DetailProductPr mPresenter;

    CallBackScreens mCallBackScreens;

    private CompositeSubscription mCompositeSubscription;

    public static DetailProductScreenFr getInstance(String id) {
        DetailProductScreenFr fragment = new DetailProductScreenFr();
        Bundle args = new Bundle();
        args.putString(ID, id);
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

    void init() {
        mCompositeSubscription = new CompositeSubscription();

        mCompositeSubscription.add(RxTextView.textChanges(edName)
                .map(charSequence -> charSequence.toString())
                .filter(s -> !s.equals(mPresenter.getName().toString()))
                .doOnNext(s -> mPresenter.changeName(s))
                .subscribe());

        mCompositeSubscription.add(RxTextView.textChanges(edBarcode)
                .map(charSequence -> charSequence.toString())
                .filter(s -> !s.equals(mPresenter.getBarcode().toString()))
                .doOnNext(s -> mPresenter.changeBarcode(s))
                .doOnNext(s -> mPresenter.refreshBarcode())
                .subscribe());

        mCompositeSubscription.add(RxTextView.textChanges(edStart)
                .map(charSequence -> charSequence.toString())
                .filter(s -> !s.equals(mPresenter.getStart().toString()))
                .doOnNext(s -> mPresenter.changeStart(s))
                .doOnNext(s -> mPresenter.refreshBarcode())
                .subscribe());

        mCompositeSubscription.add(RxTextView.textChanges(edFinish)
                .map(charSequence -> charSequence.toString())
                .filter(s -> !s.equals(mPresenter.getFinish().toString()))
                .doOnNext(s -> mPresenter.changeFinish(s))
                .doOnNext(s -> mPresenter.refreshBarcode())
                .subscribe());

        mCompositeSubscription.add(RxTextView.textChanges(edCoef)
                .map(charSequence -> charSequence.toString())
                .filter(s -> !s.equals(mPresenter.getCoef().toString()))
                .doOnNext(s -> mPresenter.changeCoef(s))
                .doOnNext(s -> mPresenter.refreshBarcode())
                .subscribe());


        mCompositeSubscription.add(((MainActivity) getActivity()).getObservableBarcode()
                .doOnNext(s -> mPresenter.scanBarcode(s))
                .doOnNext(s -> mPresenter.refreshBarcode())
                .subscribe());

        getView().setOnKeyListener(this);
        edName.setOnKeyListener(this);
        edBarcode.setOnKeyListener(this);
        edStart.setOnKeyListener(this);
        edFinish.setOnKeyListener(this);
        edCoef.setOnKeyListener(this);

        //Focus
        edStart.requestFocus();



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

    @OnClick(R.id.btSave)
    void onClickSave() {
        mPresenter.onClickSave();
    }

    @OnClick(R.id.btCancel)
    void onClickCancel() {
        mPresenter.onClickCancel();
    }

    @OnFocusChange({R.id.edName,R.id.edBarcode,R.id.edStart,R.id.edFinish,R.id.edCoef})
    public void onFocusChange(View v, boolean hasFocus){
        if(hasFocus){
            EditText ed = (EditText) v;
            ed.setSelection(ed.getText().length());
        }
    }

    @Override
    public void refresh() {

        tvId.setText(mPresenter.getCodes());
        edName.setText(mPresenter.getName());
        edBarcode.setText(mPresenter.getBarcode());
        edStart.setText(mPresenter.getStart());
        edFinish.setText(mPresenter.getFinish());
        edCoef.setText(mPresenter.getCoef());

        onFocusChange(edStart, true);

    }

    @Override
    public void showSnackbarView(String messager) {
        Snackbar.make(getView(), messager, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void refreshBarcodeView(BarcodeSeporator barcodeSeporator) {

        CharSequence mess = mPresenter.getSizeBarcode();

        if (barcodeSeporator.getError()) {
            mess = mess + ", " + barcodeSeporator.getMessError();
        } else {

            String strMess = "Вес: " + Float.toString(barcodeSeporator.getWeight());
            mess = mess + ", " + strMess;
        }

        tvMessageBarcode.setText(mess);

        int sel = edBarcode.getSelectionEnd();
        edBarcode.setText(mPresenter.getBarcode());
        edBarcode.setSelection(sel);

    }

    @Override
    public void finishView() {
        mCallBackScreens.backStack();
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        return mPresenter.onKeyListner(keyCode,event);

    }
}

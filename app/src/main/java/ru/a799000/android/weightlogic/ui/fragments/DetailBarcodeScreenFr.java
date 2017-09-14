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
import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import ru.a799000.android.weightlogic.R;
import ru.a799000.android.weightlogic.mvp.model.common.BarcodeSeporator;
import ru.a799000.android.weightlogic.mvp.presenters.DetailBarcodePr;
import ru.a799000.android.weightlogic.mvp.view.DetailBarcodeView;

import ru.a799000.android.weightlogic.ui.activityes.CallBackScreens;
import ru.a799000.android.weightlogic.ui.activityes.MainActivity;
import ru.a799000.android.weightlogic.ui.dialogs.OkCancelDialog;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by user on 17.06.2017.
 */

public class DetailBarcodeScreenFr extends MvpAppCompatFragment implements DetailBarcodeView, View.OnKeyListener {

    public static final String TAG = "DetailBarcodeScreenFr";
    static final String ID_PRODUCT = "id_product";
    static final String ID_BARCODE = "id_barcode";
    static final String BARCODE    = "barcode";


    @BindView(R.id.tvId)
    TextView tvId;

    @BindView(R.id.tvDate)
    TextView tvDate;


    @BindView(R.id.tvInfoProduct)
    TextView tvInfoProduct;


    @BindView(R.id.edBarcode)
    EditText edBarcode;

    @BindView(R.id.tvMessageBarcode)
    TextView tvMessageBarcode;

    @BindView(R.id.tvInfoPallet)
    TextView tvInfoPallet;


    @BindView(R.id.edWeight)
    EditText edWeight;

    @BindView(R.id.edSites)
    EditText edSites;

    @BindView(R.id.edPallet)
    EditText edPallet;


    @InjectPresenter
    DetailBarcodePr mPresenter;

    CallBackScreens mCallBackScreens;

    private CompositeSubscription mCompositeSubscription;

    public static DetailBarcodeScreenFr getInstance(String idProduct, String idBarcode,String barcode) {
        DetailBarcodeScreenFr fragment = new DetailBarcodeScreenFr();
        Bundle args = new Bundle();
        args.putString(ID_PRODUCT, idProduct);
        args.putString(ID_BARCODE, idBarcode);
        args.putString(BARCODE   , barcode);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_barcode, container, false);

        Bundle args = getArguments();
        mPresenter.setInputData(args.getString(ID_PRODUCT)
                , args.getString(ID_BARCODE)
                , args.getString(BARCODE));

        mCallBackScreens = (CallBackScreens) getActivity();

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

    @Override
    public void onPause() {
        super.onPause();
        mCompositeSubscription.unsubscribe();
    }

    @OnClick(R.id.btSave)
    void onClickSave() {
        mPresenter.onClickSave();
    }

    @OnClick(R.id.btDell)
    void onClickDell() {
        mPresenter.onClickDell();
    }


    @OnClick(R.id.btCancel)
    void onClickCancel() {
        mPresenter.onClickCancel();
    }

    void init() {
        mCompositeSubscription = new CompositeSubscription();

        mCompositeSubscription.add(RxTextView.textChanges(edBarcode)
                .filter(s -> {
                    if (mPresenter.getOkCancelDialog() == null) {
                        return true;
                    } else {
                        return false;
                    }
                })
                .map(charSequence -> charSequence.toString())
                .filter(s -> !s.equals(mPresenter.getBarcode().toString()))
                .doOnNext(s -> mPresenter.changeBarcode(s))
                .doOnNext(s -> mPresenter.refreshBarcode())
                .subscribe());

        mCompositeSubscription.add(RxTextView.textChanges(edWeight)
                .filter(s -> {
                    if (mPresenter.getOkCancelDialog() == null) {
                        return true;
                    } else {
                        return false;
                    }
                })
                .skip(1)
                .map(charSequence -> charSequence.toString())
                .filter(s -> !s.equals(mPresenter.getWeight().toString()))
                .subscribe(s -> mPresenter.changeWeight(s)));

        mCompositeSubscription.add(RxTextView.textChanges(edSites)
                .filter(s -> {
                    if (mPresenter.getOkCancelDialog() == null) {
                        return true;
                    } else {
                        return false;
                    }
                })
                .skip(1)
                .map(charSequence -> charSequence.toString())
                .filter(s -> !s.equals(mPresenter.getSites().toString()))
                .subscribe(s -> mPresenter.changeSites(s)));

        mCompositeSubscription.add(RxTextView.textChanges(edPallet)
                .filter(s -> {
                    if (mPresenter.getOkCancelDialog() == null) {
                        return true;
                    } else {
                        return false;
                    }
                })
                .skip(1)
                .map(charSequence -> charSequence.toString())
                .filter(s -> !s.equals(mPresenter.getPallet().toString()))
                .subscribe(s -> mPresenter.changePallet(s)));

        mCompositeSubscription.add(((MainActivity) getActivity()).getObservableBarcode()
                .filter(s -> {
                    if (mPresenter.getOkCancelDialog() == null) {
                        return true;
                    } else {
                        return false;
                    }
                })
                .subscribe(s -> {
                    mPresenter.scanBarcode(s);
                }));


        getView().setFocusableInTouchMode(true);

        getView().setOnKeyListener(this);
        edWeight.setOnKeyListener(this);
        edPallet.setOnKeyListener(this);
        edSites.setOnKeyListener(this);

        //Focus
        edPallet.requestFocus();
    }

    @Override
    public void refresh() {
        tvId.setText(mPresenter.getId());
        edBarcode.setText(mPresenter.getBarcode());
        tvInfoProduct.setText(mPresenter.getInfoProduct());
        edWeight.setText(mPresenter.getWeight());
        edSites.setText(mPresenter.getSites());
        edPallet.setText(mPresenter.getPallet());
        tvDate.setText(mPresenter.getDate());
        tvInfoPallet.setText(mPresenter.getInfoPallet());

        mPresenter.refreshBarcode();

        onFocusChange(edPallet, true);

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

        edWeight.setText(mPresenter.getWeight());
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
    public void startDetailBarcodeForNewBarcodeScreenView(String idProduct, String barcode) {
        mCallBackScreens.startDetailBarcodeForNewBarcodeScreenView(idProduct,barcode);
    }

    @Override
    public void startOkCancelDialog() {
        OkCancelDialog.BuilderInterface okCancelDialogBuilderdata = mPresenter.getOkCancelDialog();
        if(okCancelDialogBuilderdata != null){
            OkCancelDialog okCancelDialog = OkCancelDialog.getInstance(okCancelDialogBuilderdata,mPresenter);
            okCancelDialog.show(getActivity().getSupportFragmentManager(),OkCancelDialog.TAG);
        }
    }


    @OnFocusChange({R.id.edWeight,R.id.edBarcode,R.id.edSites,R.id.edPallet})
    public void onFocusChange(View v, boolean hasFocus){
        if(hasFocus){
            EditText ed = (EditText) v;
            ed.setSelection(ed.getText().length());
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        return mPresenter.onKeyListner(keyCode,event);
    }
}

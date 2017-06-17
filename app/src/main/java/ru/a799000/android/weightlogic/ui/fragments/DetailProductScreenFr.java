package ru.a799000.android.weightlogic.ui.fragments;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
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
import ru.a799000.android.weightlogic.R;

import ru.a799000.android.weightlogic.mvp.model.common.BarcodeSeporator;
import ru.a799000.android.weightlogic.mvp.presenters.detailpresenter.DetailProductPr;
import ru.a799000.android.weightlogic.mvp.view.DetailProductView;
import ru.a799000.android.weightlogic.repository.barcode.BarcodeDataBroadcastReceiver;
import ru.a799000.android.weightlogic.ui.activityes.CallBackScreens;
import ru.a799000.android.weightlogic.ui.activityes.MainActivity;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by user on 17.06.2017.
 */

public class DetailProductScreenFr extends MvpAppCompatFragment implements DetailProductView {

    public static final String TAG = "DetailProductScreenFr";
    static final String ID = "id";



    CallBackScreens mCallBackScreens;

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

    private CompositeSubscription mCompositeSubscription;

    public static DetailProductScreenFr getInstance(String id) {
        DetailProductScreenFr fragment = new DetailProductScreenFr();
        Bundle args = new Bundle();
        args.putString(ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_product, container, false);

        Bundle args = getArguments();
        mPresenter.setID(args.getString(ID));

        mCallBackScreens = (CallBackScreens) getActivity();


        ((MainActivity)getActivity()).getObservableBarcode()
                .subscribe(s -> {
                    mPresenter.changeBarcode(s);
                });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initSubscription();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeSubscription.unsubscribe();
    }


    @OnClick(R.id.btSave)
    void onClickSave(){
        mPresenter.onClickSave();
    }


    @OnClick(R.id.btCancel)
    void onClickCancel(){
        mPresenter.onClickCancel();
    }

    void initSubscription(){
        mCompositeSubscription = new CompositeSubscription();

        mCompositeSubscription.add(RxTextView.textChanges(edBarcode)
                .filter(charSequence -> (!charSequence.toString().equals(mPresenter.getBarcode().toString())))
                .subscribe(charSequence -> mPresenter.changeBarcode(charSequence.toString())));

        mCompositeSubscription.add(RxTextView.textChanges(edName)
                .filter(charSequence -> (!charSequence.toString().equals(mPresenter.getName().toString())))
                .subscribe(charSequence -> mPresenter.changeName(charSequence.toString())));

        mCompositeSubscription.add(RxTextView.textChanges(edStart)
                .filter(charSequence -> (!charSequence.toString().equals(mPresenter.getStart())))
                .subscribe(charSequence -> mPresenter.changeStart(charSequence.toString())));

        mCompositeSubscription.add(RxTextView.textChanges(edFinish)
                .filter(charSequence -> (!charSequence.toString().equals(mPresenter.getFinish())))
                .subscribe(charSequence -> mPresenter.changeFinish(charSequence.toString())));

        mCompositeSubscription.add(RxTextView.textChanges(edCoef)
                .filter(charSequence -> (!charSequence.toString().equals(mPresenter.getCoef())))
                .subscribe(charSequence -> mPresenter.changeCoef(charSequence.toString())));


    }

    @Override
    public void refreshProduct() {

        tvId.setText(mPresenter.getCodes());
        edName.setText(mPresenter.getName());
        edBarcode.setText(mPresenter.getBarcode());
        edStart.setText(mPresenter.getStart());
        edFinish.setText(mPresenter.getFinish());
        edCoef.setText(mPresenter.getCoef());
    }

    @Override
    public void showSnackbarView(String messager) {
        Snackbar.make(getView(), messager, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void refreshBarcodeView(BarcodeSeporator barcodeSeporator) {

        CharSequence mess = mPresenter.getSizeBarcode();

        if(barcodeSeporator.getError() ){
            mess = mess + ", " + barcodeSeporator.getMessError();
        }else{

            String strMess =  "Вес: " + Float.toString(barcodeSeporator.getWeight());

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
}

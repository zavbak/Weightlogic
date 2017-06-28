package ru.a799000.android.weightlogic.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import ru.a799000.android.weightlogic.R;
import ru.a799000.android.weightlogic.mvp.model.intities.Barcode;
import ru.a799000.android.weightlogic.mvp.presenters.BarcodesListScreenPr;
import ru.a799000.android.weightlogic.mvp.view.BarcodesListScreenView;
import ru.a799000.android.weightlogic.ui.activityes.CallBackScreens;
import ru.a799000.android.weightlogic.ui.activityes.MainActivity;
import ru.a799000.android.weightlogic.ui.adapters.AdaprerBarcode;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by user on 17.06.2017.
 */

public class BarcodesListScreenFr extends MvpAppCompatFragment implements BarcodesListScreenView, View.OnKeyListener {
    public static final String TAG = "BarcodesListScreenFr";
    static final String ID = "id";


    @InjectPresenter
    BarcodesListScreenPr mPresenter;

    @BindView(R.id.tvMessageBarcode)
    TextView tvMessage;

    @BindView(R.id.tvInfoProduct)
    TextView tvInfoProduct;

    @BindView(R.id.tvInfoBarcodes)
    TextView tvInfoBarcodes;


    @BindView(R.id.lvBarcodes)
    ListView lvBarcodes;

    CallBackScreens mCallBackScreens;

    private CompositeSubscription mCompositeSubscription;


    public static BarcodesListScreenFr getInstance(String id) {
        BarcodesListScreenFr fragment = new BarcodesListScreenFr();
        Bundle args = new Bundle();
        args.putString(ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_barcodes_screen, container, false);

        Bundle args = getArguments();
        mPresenter.setParamIdProduct(args.getString(ID));
        mCallBackScreens = (CallBackScreens) getActivity();



        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        init();
    }

    void init() {
        mCompositeSubscription = new CompositeSubscription();
        mCompositeSubscription.add(((MainActivity) getActivity()).getObservableBarcode()
                .subscribe(s -> {
                    mPresenter.scanBarcode(s);
                }));
    }

    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mCompositeSubscription.unsubscribe();
        mPresenter.saveSettings(lvBarcodes.getSelectedItemPosition());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onStart();
    }


    @Override
    public void refreshView(OrderedRealmCollection<Barcode> data) {

        AdaprerBarcode adapter = new AdaprerBarcode(data);
        lvBarcodes.setAdapter(adapter);
        lvBarcodes.requestFocus(0);
        lvBarcodes.setClickable(true);

        lvBarcodes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                mPresenter.onClickBarcode(position);

            }
        });

        lvBarcodes.setOnKeyListener(this);

        try {
            lvBarcodes.setSelection(mPresenter.getSelectionPosition());
        } catch (Exception e) {
            e.printStackTrace();
        }


        tvInfoProduct.setText(mPresenter.getInfoProduct());
        tvInfoBarcodes.setText(mPresenter.getInfoBarcodes());

    }

    @Override
    public void showInfoView(String messager) {
        tvMessage.setText(messager);
    }

    @Override
    public void showSnackbarView(String messager) {
        Snackbar.make(getView(), messager, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void startDetailBarcodeScreenView(String idProduct,String idBarcode) {
        mCallBackScreens.startBarcodeProductScreenView(idProduct,idBarcode);
    }

    @Override
    public void startDetailBarcodeForNewBarcodeScreenView(String idProduct, String barcode) {
        mCallBackScreens.startDetailBarcodeForNewBarcodeScreenView(idProduct,barcode);
    }




    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        int position =  lvBarcodes.getSelectedItemPosition();

        if(event.getAction()== KeyEvent.ACTION_DOWN){
            mPresenter.pressKey(event.getNumber(),position);
        }

        return false;
    }


}

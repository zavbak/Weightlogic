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
import ru.a799000.android.weightlogic.mvp.model.intities.Product;
import ru.a799000.android.weightlogic.mvp.presenters.ProductsListScreenPr;
import ru.a799000.android.weightlogic.mvp.view.ProductsListScreenView;
import ru.a799000.android.weightlogic.ui.activityes.CallBackScreens;
import ru.a799000.android.weightlogic.ui.activityes.MainActivity;
import ru.a799000.android.weightlogic.ui.adapters.AdaprerProdact;
import ru.a799000.android.weightlogic.ui.dialogs.OkCancelDialog;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by user on 17.06.2017.
 */

public class ProductsListScreenFr extends MvpAppCompatFragment implements ProductsListScreenView {
    public static final String TAG = "ProductsListScreenFr";
    static final String ID = "id";


    @InjectPresenter
    ProductsListScreenPr mPresenter;

    @BindView(R.id.tvMessageBarcode)
    TextView tvMessage;

    @BindView(R.id.lvProducts)
    ListView lvProducts;


    CallBackScreens mCallBackScreens;

    private CompositeSubscription mCompositeSubscription;


    public static ProductsListScreenFr getInstance(String id) {
        ProductsListScreenFr fragment = new ProductsListScreenFr();
        Bundle args = new Bundle();
        args.putString(ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_products_screen, container, false);

        Bundle args = getArguments();
        mPresenter.setID(args.getString(ID));

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
        mPresenter.onStart();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                int position = lvProducts.getSelectedItemPosition();

                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    mPresenter.pressKey(event.getNumber(), position);
                }

                return false;
            }
        });
    }


    @Override
    public void onPause() {
        super.onPause();
        mCompositeSubscription.unsubscribe();
        mPresenter.saveCurentPosition(lvProducts.getSelectedItemPosition());
    }

    @Override
    public void refreshView(OrderedRealmCollection<Product> data) {

        AdaprerProdact adapter = new AdaprerProdact(data);
        lvProducts.setAdapter(adapter);
        lvProducts.requestFocus(0);
        lvProducts.setSelection(mPresenter.getSelectionPosition());
        lvProducts.setClickable(true);

        lvProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                mPresenter.onClickProduct(position);

            }
        });

        lvProducts.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                int position = lvProducts.getSelectedItemPosition();

                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    mPresenter.pressKey(event.getNumber(), position);
                }

                return false;
            }
        });


        try {
            lvProducts.setSelection(mPresenter.getSelectionPosition());
        } catch (Exception e) {
            e.printStackTrace();
        }


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
    public void startDetailProductScreenView(String id) {
        mCallBackScreens.startDetailProductScreenView(id);
    }

    @Override
    public void startListScreenBarcodes(String id) {
        mCallBackScreens.startListScreenBarcodes(id);
    }

    @Override
    public void startOkCancelDialog() {
        OkCancelDialog.BuilderInterface okCancelDialogBuilderdata = mPresenter.getOkCancelDialog();
        if(okCancelDialogBuilderdata != null){
            OkCancelDialog okCancelDialog = OkCancelDialog.getInstance(okCancelDialogBuilderdata,mPresenter);
            okCancelDialog.show(getActivity().getSupportFragmentManager(),OkCancelDialog.TAG);
        }
    }


}

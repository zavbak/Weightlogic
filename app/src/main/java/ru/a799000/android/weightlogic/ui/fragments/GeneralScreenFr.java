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

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.a799000.android.weightlogic.R;
import ru.a799000.android.weightlogic.mvp.presenters.GeneralScreenPr;
import ru.a799000.android.weightlogic.mvp.view.GeneralScreenView;
import ru.a799000.android.weightlogic.ui.activityes.CallBackScreens;
import ru.a799000.android.weightlogic.ui.adapters.CommandsAdapter;

/**
 * Created by Alex on 16.06.2017.
 */

public class GeneralScreenFr extends MvpAppCompatFragment implements GeneralScreenView {
    public static final String TAG = "GeneralScreenFr";

    CallBackScreens mCallBackScreens;


    @InjectPresenter
    GeneralScreenPr mPresenter;

    @BindView(R.id.lvCommands)
    ListView lvCommands;


    public static GeneralScreenFr getInstance() {
        GeneralScreenFr fragment = new GeneralScreenFr();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.general_screen, container, false);

        mCallBackScreens = (CallBackScreens) getActivity();

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    public void refreshView(List<String> list) {

        CommandsAdapter adapter = new CommandsAdapter(getActivity(),list);
        lvCommands.setAdapter(adapter);
        lvCommands.requestFocus(0);
        lvCommands.setSelection(0);
        lvCommands.setClickable(true);

        lvCommands.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                mPresenter.selectCommand(position);

            }
        });

        lvCommands.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(event.getAction()== KeyEvent.ACTION_DOWN){
                    mPresenter.pressKey(event.getNumber());
                }

                return false;
            }
        });
    }

    @Override
    public void showSnackbarView(String messager) {
        Snackbar.make(getView(), messager, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void startTestScreenView() {
        mCallBackScreens.startTestScreenView();
    }

    @Override
    public void startSettingsScreenView() {
        mCallBackScreens.startSettingsScreenView();
    }

    @Override
    public void startProductsScreenView() {
        mCallBackScreens.startProductsScreenView(null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }








}

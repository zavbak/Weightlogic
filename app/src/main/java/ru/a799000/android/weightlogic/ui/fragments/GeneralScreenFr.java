package ru.a799000.android.weightlogic.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;
import ru.a799000.android.weightlogic.R;
import ru.a799000.android.weightlogic.mvp.presenters.GeneralScreenPr;
import ru.a799000.android.weightlogic.mvp.view.GeneralScreenView;
import ru.a799000.android.weightlogic.ui.adapters.CommandsAdapter;

/**
 * Created by Alex on 16.06.2017.
 */

public class GeneralScreenFr extends MvpAppCompatFragment implements GeneralScreenView {
    public static final String TAG = "GeneralScreenFr";


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
        return inflater.inflate(R.layout.general_screen, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    public void refreshView(List<String> list) {
        //mRecyclerView.setAdapter(new AdapterListProduct(list, id -> mPresenter.clickItem(id)));
        CommandsAdapter adapter = new CommandsAdapter(getActivity(),list);
        lvCommands.setAdapter(adapter);

        lvCommands.requestFocus();
        lvCommands.requestFocus(5);
        lvCommands.setClickable(true);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }








}

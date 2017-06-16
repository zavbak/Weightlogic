package ru.a799000.android.weightlogic.mvp.presenters.generalscreen;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import ru.a799000.android.weightlogic.R;
import ru.a799000.android.weightlogic.app.App;
import ru.a799000.android.weightlogic.mvp.view.GeneralScreenView;


@InjectViewState
public class GeneralScreenPr extends MvpPresenter<GeneralScreenView> {

    @Inject
    Context mContext;

    public GeneralScreenPr() {
        App.getAppComponent().injectGeneralScreenPr(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }


    List<String> getListCommands(){
        List<String> list = Arrays.asList(mContext.getResources().getStringArray(R.array.commands_array));
        return list;
    }

    public void onStart() {
        getViewState().refreshView(getListCommands());
    }
}

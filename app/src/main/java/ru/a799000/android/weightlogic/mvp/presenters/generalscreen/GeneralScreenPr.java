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
    List<String> mlist;

    public GeneralScreenPr() {
        App.getAppComponent().injectGeneralScreenPr(this);
        mlist = Arrays.asList(mContext.getResources().getStringArray(R.array.commands_array));
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }


    List<String> getListCommands() {
        return mlist;
    }

    public void onStart() {

        getViewState().refreshView(getListCommands());
    }

    public void selectCommand(int position) {
        getViewState().showMessageView(mlist.get(position));
    }

    public void pressKey(char number) {
        try {
            int i = Integer.parseInt(Character.toString(number));
            if ((i != 0) && (i-1 < mlist.size())) {
                getViewState().showMessageView(mlist.get(i-1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

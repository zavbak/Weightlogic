package ru.a799000.android.weightlogic.mvp.presenters;

import com.arellomobile.mvp.MvpPresenter;

import ru.a799000.android.weightlogic.mvp.view.DetailProductView;

/**
 * Created by user on 17.06.2017.
 */

public class DetailProductPr extends MvpPresenter<DetailProductView> {
    String mId;

    public void setID(String ID) {
        mId = ID;
    }




}

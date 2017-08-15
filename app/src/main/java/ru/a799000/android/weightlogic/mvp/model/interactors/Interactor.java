package ru.a799000.android.weightlogic.mvp.model.interactors;

import rx.Observable;

/**
 * Created by user on 17.06.2017.
 */

public abstract class Interactor<T> {

    public abstract Observable<T> getObservable();
}

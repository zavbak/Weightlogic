package ru.a799000.android.weightlogic.ui.activityes;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by user on 17.06.2017.
 */

public interface GetDataActivity {
    PublishSubject<String> getObservableBarcode();
}

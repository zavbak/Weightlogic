package ru.a799000.android.weightlogic.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import io.realm.RealmConfiguration;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.DellAllInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.DellBarcodeInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.DellProductByIdInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.GetAllProductInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.GetProductByIdInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.SaveBarcodeInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.SaveProductInteractor;
import ru.a799000.android.weightlogic.mvp.presenters.GeneralScreenPr;


@Singleton
@Component(modules={AppModule.class,RealmModule.class})
public interface AppComponent {
    Context getContext();
    RealmConfiguration getRealmConfiguration();

    void injectGeneralScreenPr(GeneralScreenPr generalScreenPr);
    void injectSaveProductInteractor(SaveProductInteractor saveProductInteractor);
    void injectDellAllInteractor(DellAllInteractor dellAllInteractor);
    void injectDellBarcodeInteractor(DellBarcodeInteractor dellBarcodeInteractor);
    void injectDellProductByIdInteractor(DellProductByIdInteractor dellProductByIdInteractor);
    void injectGetAllProductInteractor(GetAllProductInteractor getAllProductInteractor);
    void injectGetProductByIdInteractor(GetProductByIdInteractor getProductByIdInteractor);
    void injectSaveBarcodeInteractor(SaveBarcodeInteractor saveBarcodeInteractor);
}

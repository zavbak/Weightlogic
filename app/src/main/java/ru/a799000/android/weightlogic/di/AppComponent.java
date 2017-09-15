package ru.a799000.android.weightlogic.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import ru.a799000.android.weightlogic.mvp.model.interactors.HttpInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.DellAllInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.DellBarcodeInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.DellPaletInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.DellProductByIdInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.GetAllProductInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.GetAllBarcodesByIdProductInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.GetBarcodeByIDInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.GetAllPaletSummInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.GetPaletSummInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.GetProductByIdInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.SaveBarcodeInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.SaveProductInteractor;
import ru.a799000.android.weightlogic.mvp.model.intities.SettingsApp;
import ru.a799000.android.weightlogic.mvp.presenters.GeneralScreenPr;


@Singleton
@Component(modules={AppModule.class,RealmModule.class,NetworkingModule.class,SettingsModule.class})
public interface AppComponent {
    Context getContext();
    RealmConfiguration getRealmConfiguration();
    Realm getRealm();
    SettingsApp getSettingsApp();


    void injectGeneralScreenPr(GeneralScreenPr generalScreenPr);
    void injectSaveProductInteractor(SaveProductInteractor saveProductInteractor);
    void injectDellAllInteractor(DellAllInteractor dellAllInteractor);
    void injectDellBarcodeInteractor(DellBarcodeInteractor dellBarcodeInteractor);
    void injectDellProductByIdInteractor(DellProductByIdInteractor dellProductByIdInteractor);
    void injectGetProductByIdInteractor(GetProductByIdInteractor getProductByIdInteractor);
    void injectSaveBarcodeInteractor(SaveBarcodeInteractor saveBarcodeInteractor);
    void injectGetAllProductInteractor(GetAllProductInteractor getAllProductInteractor);
    void injectGetBarcodesByIdProductInteractor(GetAllBarcodesByIdProductInteractor getBarcodesByIdProductInteractor);
    void GetBarcodeByIDInteractor(GetBarcodeByIDInteractor getBarcodeByIDInteractor);


    void injectLoadHttpInteractor(HttpInteractor loadHttpInteractor);
    void injectHttpInteractor(HttpInteractor httpInteractor);

    void injectGetPaletSummInteractor(GetAllPaletSummInteractor getPaletSummInteractor);
    void injectGetPaletSummInteractor(GetPaletSummInteractor getPaletSummInteractor);

    void injectGetPaletSummInteractor(DellPaletInteractor dellPaletInteractor);
}

package ru.a799000.android.weightlogic.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.a799000.android.weightlogic.app.App;
import ru.a799000.android.weightlogic.mvp.model.intities.SettingsApp;
import ru.a799000.android.weightlogic.repository.net.ApiService;
import ru.a799000.android.weightlogic.repository.sharedpref.SharedPreferenseHelper;


@Module
public class NetworkingModule {

    @Provides
    public Retrofit provideRestAdapter() {
        SettingsApp settingsApp = SharedPreferenseHelper.getInstance(App.getAppComponent().getContext()).getSettings();
        String baseUrl = settingsApp.getNameServer();

        return new Retrofit.Builder()
                .baseUrl(baseUrl) //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

    }


    @Provides
    public ApiService provideCatalogAPIService(Retrofit retrofit) {
        return  retrofit.create(ApiService.class);
    }
}

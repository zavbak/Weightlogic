package ru.a799000.android.weightlogic.repository.net;

import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import ru.a799000.android.weightlogic.mvp.model.intities.load.IntitiesParamLoadHttp;
import rx.Observable;


/**
 * Created by Alex on 20.04.2017.
 */

public interface ApiService {
    @POST("/Api/hs/api/")
    Observable<ResponseModelDataServiceLoad> getResponseModelDataServiceObservable(
            @Header("Authorization") String auth,
            @Body IntitiesParamLoadHttp sendParamJson);

}


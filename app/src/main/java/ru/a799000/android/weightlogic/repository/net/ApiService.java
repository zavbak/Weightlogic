package ru.a799000.android.weightlogic.repository.net;

import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import ru.a799000.android.weightlogic.mvp.model.intities.IntitiesParamHttp;
import rx.Observable;


/**
 * Created by Alex on 20.04.2017.
 */

public interface ApiService {
    @POST("/{namebase}/hs/api/")
    Observable<ResponseModelDataServiceLoad> getResponseModelDataServiceObservable(
            @Path("namebase") String namebase,
            @Header("Authorization") String auth,
            @Body IntitiesParamHttp sendParamJson);

}


package ru.a799000.android.weightlogic.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.RealmList;
import io.realm.RealmResults;
import ru.a799000.android.weightlogic.mvp.model.interactors.GetDataSendInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.HttpInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.DellAllInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.DellBarcodeInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.DellProductByIdInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.GetAllProductInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.GetPaletSummInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.GetProductByIdInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.SaveBarcodeInteractor;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.SaveProductInteractor;
import ru.a799000.android.weightlogic.mvp.model.intities.Barcode;
import ru.a799000.android.weightlogic.mvp.model.intities.Product;
import ru.a799000.android.weightlogic.mvp.model.intities.load.IntitiesLoadObject;
import ru.a799000.android.weightlogic.repository.net.SendModel;
import ru.a799000.android.weightlogic.mvp.view.TestScreenFrView;
import ru.a799000.android.weightlogic.repository.net.AutoritationManager;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by user on 17.06.2017.
 */

@InjectViewState
public class TestScreenFrPr extends MvpPresenter<TestScreenFrView> {


    private String printBarcodes(RealmList<Barcode> barcodes) {
        String str = "\nBarcodes(" + barcodes.size() + "): \n";
        for (Barcode barcode : barcodes) {
            str = str + "   " + barcode.toString() + "\n";
        }

        return str;
    }


    public void onCliskBtShow() {
        GetAllProductInteractor interactor = new GetAllProductInteractor();
        interactor.getObservable()
                .subscribeOn(AndroidSchedulers.mainThread()) //Schedulers.io()
                .observeOn(AndroidSchedulers.mainThread()) //AndroidSchedulers.mainThread()
                .subscribe(
                        resultO -> {
                            RealmResults<Product> results = (RealmResults<Product>) resultO;
                            String str = "Result: \n";
                            for (Product product : results) {
                                str = str + "\n" + product.toString() + " " + printBarcodes(product.getBarcodes()) + "\n";
                            }
                            getViewState().showTvMessageView(str);

                        }
                        , throwable ->
                                getViewState().showTvMessageView(throwable.getMessage()));


    }

    public void onCliskBtAddProduct() {

        Product product = Product.getBuilder()
                .code("10")
                .name("Мясо1")
                .initBarcode("566546546")
                .start(10)
                .finish(13)
                .coef((float) 0.1)
                .unit("кг")
                .build();


        SaveProductInteractor interactor = new SaveProductInteractor(product);
        interactor.getObservable()
                .subscribeOn(AndroidSchedulers.mainThread()) //Schedulers.io()
                .observeOn(AndroidSchedulers.mainThread()) //AndroidSchedulers.mainThread()
                .subscribe(prod -> getViewState().showTvMessageView(prod.toString())
                        , throwable -> getViewState().showTvMessageView(throwable.getMessage()));

    }

    public void onClickBtbtGetProductById() {

        GetProductByIdInteractor interactor = new GetProductByIdInteractor(2);
        interactor.getObservable()
                .subscribeOn(AndroidSchedulers.mainThread()) //Schedulers.io()
                .observeOn(AndroidSchedulers.mainThread()) //AndroidSchedulers.mainThread()
                .subscribe(prod -> getViewState().showTvMessageView("id = 2: " + prod.toString())
                        , throwable -> getViewState().showTvMessageView(throwable.getMessage()));

    }

    public void onClickBtDellProdact() {
        DellProductByIdInteractor interactor = new DellProductByIdInteractor(4);
        interactor.getObservable()
                .subscribeOn(AndroidSchedulers.mainThread()) //Schedulers.io()
                .observeOn(AndroidSchedulers.mainThread()) //AndroidSchedulers.mainThread()
                .subscribe(empty -> getViewState().showTvMessageView("dell id = 2: " + empty), throwable -> {
                    getViewState().showTvMessageView(throwable.toString());
                });

    }

    public void onClickBtAddBarcode() {
        Barcode barcode = Barcode.getBuilder()
                .setBarcode("77777")
                .setPlaces(1)
                .setWeight((float) 2.5)
                .build();
        //barcode.setId(2);

        SaveBarcodeInteractor interactor = new SaveBarcodeInteractor(6, barcode);
        interactor.getObservable().
                subscribeOn(AndroidSchedulers.mainThread()) //Schedulers.io()
                .observeOn(AndroidSchedulers.mainThread()) //AndroidSchedulers.mainThread()
                .subscribe(barc -> getViewState().showTvMessageView(barc.toString())
                        , throwable -> getViewState().showTvMessageView(throwable.getMessage()));

    }

    public void onClickBtDellBarcode() {
        DellBarcodeInteractor interactor = new DellBarcodeInteractor(5);
        interactor.getObservable()
                .subscribeOn(AndroidSchedulers.mainThread()) //Schedulers.io()
                .observeOn(AndroidSchedulers.mainThread()) //AndroidSchedulers.mainThread()
                .subscribe(empty -> getViewState().showTvMessageView("dell id = 3: " + empty), throwable -> {
                    getViewState().showTvMessageView(throwable.toString());
                });

    }

    public void onClickBtDellAll() {
        DellAllInteractor interactor = new DellAllInteractor();
        interactor.getObservable()
                .subscribeOn(AndroidSchedulers.mainThread()) //Schedulers.io()
                .observeOn(AndroidSchedulers.mainThread()) //AndroidSchedulers.mainThread()
                .subscribe(empty -> getViewState().showTvMessageView("dell All: " + empty), throwable -> {
                    getViewState().showTvMessageView(throwable.toString());
                });

    }


    String getAuthTest() {
        return AutoritationManager.getStringAutorization("Admin", "123");
    }

    public void onClickBtLoadNet() {

        JSONObject dataJson = new JSONObject();
        String dataString = null;
        try {
            dataJson.put("code_tsd", "В0023");
            dataString = dataJson.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        SendModel intitiesParamHttp = new SendModel();
        intitiesParamHttp.setCommand("command_data_to_tsd");
        intitiesParamHttp.setStrDataIn(dataString);


        HttpInteractor interactor = new HttpInteractor(intitiesParamHttp);
        interactor.getObservable()
                .flatMap(responseModelDataServiceLoad -> {
                    try {
                        JSONObject jsonObject = new JSONObject(responseModelDataServiceLoad.getResponse());
                        String dataStr = jsonObject.getString("data");

                        GsonBuilder builder = new GsonBuilder();
                        Gson gson = builder.create();
                        return Observable.just(gson.fromJson(dataStr, IntitiesLoadObject.class));


                    } catch (JSONException e) {
                        e.printStackTrace();
                        return Observable.error(e);
                    }
                })
                .flatMap(intitiesLoadObject -> {
                    return Observable.from(intitiesLoadObject.getTovars());
                })
                .subscribeOn(Schedulers.io()) //делаем запрос, преобразование, кэширование в отдельном потоке
                .observeOn(AndroidSchedulers.mainThread()) // обработка результата - в main thread
                .flatMap(intitiesTovar -> {
                    Product product = new Product();
                    product.setCode(intitiesTovar.getCode());
                    product.setName(intitiesTovar.getName());
                    product.setUnit(intitiesTovar.getEd());

                    SaveProductInteractor saveProductInteractor = new SaveProductInteractor(product);
                    return saveProductInteractor.getObservable();

                })
                .count()
                .subscribe(integer -> {

                    getViewState().showTvMessageView("Загружено: " + integer);


                }, throwable -> {

                    getViewState().showTvMessageView(throwable.getMessage());


                }, () -> {


                });
    }


    public void sendHTTP(String auth, SendModel intitiesParamLoadHttp) {
        HttpInteractor interactor = new HttpInteractor(intitiesParamLoadHttp);
        interactor.getObservable()
                .subscribeOn(Schedulers.io()) //делаем запрос, преобразование, кэширование в отдельном потоке
                .observeOn(AndroidSchedulers.mainThread())// обработка результата - в main thread
                .subscribe(responseModelDataServiceLoad -> {

                    getViewState().showTvMessageView(responseModelDataServiceLoad.getResponse());


                }, throwable -> {

                    getViewState().showTvMessageView(throwable.getMessage());


                }, () -> {


                });


    }

    public void onClickBtSendHTTP() {


        Observable<String> oDataSendStrInteractor = new GetDataSendInteractor().getObservable();


        Observable.just(new SendModel())
                .map(intitiesParamHttp1 -> {
                    intitiesParamHttp1.setCommand("command_data_from_tsd");
                    return intitiesParamHttp1;
                })
                .zipWith(oDataSendStrInteractor, (intitiesParamHttp, strData) -> {
                    intitiesParamHttp.setStrDataIn(strData); //Добавили данные
                    return intitiesParamHttp;
                })
                .subscribeOn(AndroidSchedulers.mainThread()) //Schedulers.io()
                .observeOn(AndroidSchedulers.mainThread())//AndroidSchedulers.mainThread()
                .subscribe(intitiesParamHttp -> {
                    getViewState().showTvMessageView(intitiesParamHttp.getStrDataIn());
                    sendHTTP(getAuthTest(), intitiesParamHttp);

                }, throwable -> {
                });

//        oIntitiesParamHttp
//                .map(intitiesParamHttp -> {
//                    return new HttpInteractor(getAuthTest(), intitiesParamHttp);
//                })
//                .subscribeOn(AndroidSchedulers.mainThread()) //Schedulers.io()
//                .flatMap(httpInteractor -> {
//                    return httpInteractor.getObservable();
//                })
//                .map(responseModelDataServiceLoad -> {
//                    return responseModelDataServiceLoad;
//                })
//                .subscribeOn(Schedulers.io()) //делаем запрос, преобразование, кэширование в отдельном потоке
//                .observeOn(AndroidSchedulers.mainThread()) // обработка результата - в main thread
//                .subscribe(o -> {
//                    String s = "s";
//                });


//        oIntitiesParamHttp
//                .flatMap(intitiesParamHttp -> {
//                    return new HttpInteractor(getAuthTest(), intitiesParamHttp).getObservable();
//                })
//                .subscribeOn(Schedulers.io()) //делаем запрос, преобразование, кэширование в отдельном потоке
//                .observeOn(AndroidSchedulers.mainThread()) // обработка результата - в main thread
//                .subscribe(responseModelDataServiceLoad -> {
//
//                    getViewState().showTvMessageView(responseModelDataServiceLoad.toString());
//
//
//                }, throwable -> {
//
//                    getViewState().showTvMessageView(throwable.getMessage());
//
//
//                }, () -> {
//
//
//                });


    }

    public void onClickBtSummPallet() {

        String mess = "";

        GetPaletSummInteractor getPaletSummInteractor = new GetPaletSummInteractor(1);
        getPaletSummInteractor.getObservable()
                .reduce(mess,(paletSumResult, paletSumResult2) -> {
                    paletSumResult = paletSumResult + "\n" +  paletSumResult2.toString();
                    return paletSumResult;
                })
                .doOnNext(o -> {
                    getViewState().showTvMessageView("" + o);
                })
                .subscribe();


    }
}

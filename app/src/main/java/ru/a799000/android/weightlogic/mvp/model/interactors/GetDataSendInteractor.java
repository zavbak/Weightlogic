package ru.a799000.android.weightlogic.mvp.model.interactors;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmResults;
import ru.a799000.android.weightlogic.app.App;
import ru.a799000.android.weightlogic.mvp.model.interactors.realm.GetAllProductInteractor;
import ru.a799000.android.weightlogic.mvp.model.intities.Barcode;
import ru.a799000.android.weightlogic.mvp.model.intities.Product;
import ru.a799000.android.weightlogic.mvp.model.intities.save.IntitiesBarcodeSend;
import ru.a799000.android.weightlogic.mvp.model.intities.save.IntitiesSendObject;
import ru.a799000.android.weightlogic.mvp.model.intities.save.IntitiesTovarSend;
import rx.Observable;

/**
 * Created by user on 20.06.2017.
 */

public class GetDataSendInteractor extends Interactor<String> {


    IntitiesBarcodeSend getIntitiesBarcodeSend(Barcode barcode) {
        IntitiesBarcodeSend barcodeSend = new IntitiesBarcodeSend();
        barcodeSend.setId(barcode.getId());
        barcodeSend.setPlaces(barcode.getPlaces());
        barcodeSend.setWeight(barcode.getWeight());
        barcodeSend.setBarcode(barcode.getBarcode());
        barcodeSend.setPallet(barcode.getPallet());
        barcodeSend.setDate(barcode.getDate());

        return barcodeSend;

    }


    IntitiesTovarSend getTovarSend(Product product) {

        IntitiesTovarSend tovarSend = new IntitiesTovarSend();
        tovarSend.setId(product.getId());
        tovarSend.setCode(product.getCode());
        tovarSend.setUnit(product.getUnit());
        tovarSend.setName(product.getName());
        tovarSend.setInitBarcode(product.getInitBarcode());

        tovarSend.setCoef(product.getCoef());
        tovarSend.setStart(product.getStart());
        tovarSend.setFinish(product.getFinish());


        RealmList<Barcode> barcodes = product.getBarcodes();
        List<IntitiesBarcodeSend> listBar = new ArrayList<IntitiesBarcodeSend>();

        for (Barcode barcode : barcodes) {
            listBar.add(getIntitiesBarcodeSend(barcode));
        }

        tovarSend.setBarcodes(listBar);
        return tovarSend;

    }

    String getIntitiesSendObject(RealmResults<Product> listProduct) {


        List<IntitiesTovarSend> listTovar = new ArrayList<>();

        for (Product product : listProduct) {
            listTovar.add(getTovarSend(product));
        }


        IntitiesSendObject sendObject = new IntitiesSendObject();
        sendObject.setDate(new Date());
        sendObject.setTovars(listTovar);
        sendObject.setCodeTSD(App.getAppComponent().getSettingsApp().getCode1C());

        return new Gson().toJson(sendObject);
    }


    @Override
    public Observable<String> getObservable() {

        GetAllProductInteractor interactor = new GetAllProductInteractor();
        return interactor.getObservable()
                .flatMap(products -> {
                    try {
                        return Observable.just(getIntitiesSendObject(products));
                    } catch (Exception e) {
                        e.printStackTrace();
                        return Observable.error(e);
                    }
                });

    }
}

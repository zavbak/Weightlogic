package ru.a799000.android.weightlogic.mvp.model.intities.save;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Alex on 30.05.2017.
 */

public class IntitiesBarcodeSend {

    @SerializedName("Код")
    @Expose
    long id;
    @SerializedName("Штрихкод")
    @Expose
    String barcode;
    @SerializedName("Мест")
    @Expose
    int places;
    @SerializedName("Паллет")
    @Expose
    int pallet;
    @SerializedName("Вес")
    @Expose
    float weight;

    @SerializedName("Дата")
    @Expose
    Date date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public int getPlaces() {
        return places;
    }

    public void setPlaces(int places) {
        this.places = places;
    }

    public int getPallet() {
        return pallet;
    }

    public void setPallet(int pallet) {
        this.pallet = pallet;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

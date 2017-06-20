package ru.a799000.android.weightlogic.mvp.model.intities.save;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Alex on 30.05.2017.
 */

public class IntitiesTovarSend {

    @SerializedName("id")
    @Expose
    long id;

    @SerializedName("Код")
    @Expose
    String code;

    @SerializedName("Единица")
    @Expose
    String unit;

    @SerializedName("Наименование")
    @Expose
    String name;

    @SerializedName("Штрихкод")
    @Expose
    String initBarcode;

    @SerializedName("Начало")
    @Expose
    int start;

    @SerializedName("Конец")
    @Expose
    int finish;

    @SerializedName("Коэффицент")
    @Expose
    float coef;

    @SerializedName("Подсчет")
    @Expose
    List<IntitiesBarcodeSend> mBarcodes = null;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInitBarcode() {
        return initBarcode;
    }

    public void setInitBarcode(String initBarcode) {
        this.initBarcode = initBarcode;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getFinish() {
        return finish;
    }

    public void setFinish(int finish) {
        this.finish = finish;
    }

    public float getCoef() {
        return coef;
    }

    public void setCoef(float coef) {
        this.coef = coef;
    }

    public List<IntitiesBarcodeSend> getBarcodes() {
        return mBarcodes;
    }

    public void setBarcodes(List<IntitiesBarcodeSend> barcodes) {
        mBarcodes = barcodes;
    }
}

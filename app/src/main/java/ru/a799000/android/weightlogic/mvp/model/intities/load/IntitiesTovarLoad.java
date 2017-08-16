package ru.a799000.android.weightlogic.mvp.model.intities.load;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Alex on 30.05.2017.
 */

public class IntitiesTovarLoad {

    @SerializedName("Код")
    @Expose
    private String code;
    @SerializedName("Наименование")
    @Expose
    private String name;
    @SerializedName("Единица")
    @Expose
    private String ed;

    @SerializedName("Начало")
    @Expose
    int start;
    @SerializedName("Конец")
    @Expose
    int finish;

    @SerializedName("Коэффициент")
    @Expose
    float coef;


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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEd() {
        return ed;
    }

    public void setEd(String ed) {
        this.ed = ed;
    }
}

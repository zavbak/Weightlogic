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

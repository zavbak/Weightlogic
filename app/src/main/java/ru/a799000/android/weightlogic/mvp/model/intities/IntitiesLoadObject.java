package ru.a799000.android.weightlogic.mvp.model.intities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Alex on 30.05.2017.
 */

public class IntitiesLoadObject {

    @SerializedName("Номенклатура")
    @Expose
    private List<IntitiesTovarLoad> tovars = null;

    public List<IntitiesTovarLoad> getTovars() {
        return tovars;
    }

    public void setTovars(List<IntitiesTovarLoad> tovars) {
        this.tovars = tovars;
    }
}

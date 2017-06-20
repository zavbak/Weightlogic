package ru.a799000.android.weightlogic.mvp.model.intities.save;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by Alex on 30.05.2017.
 */

public class IntitiesSendObject {

    @SerializedName("Дата")
    @Expose
    Date date;

    @SerializedName("Номенклатура")
    @Expose
    List<IntitiesTovarSend> tovars = null;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<IntitiesTovarSend> getTovars() {
        return tovars;
    }

    public void setTovars(List<IntitiesTovarSend> tovars) {
        this.tovars = tovars;
    }
}

package ru.a799000.android.weightlogic.mvp.model.intities.save;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by Alex on 30.05.2017.
 */

public class IntitiesSendObject {

    @SerializedName("date")
    @Expose
    Date date;

    @SerializedName("tovar")
    @Expose
    List<IntitiesTovarSend> tovars = null;

    @SerializedName("code_tsd")
    @Expose
    String codeTSD;

    public String getCodeTSD() {
        return codeTSD;
    }

    public void setCodeTSD(String codeTSD) {
        this.codeTSD = codeTSD;
    }

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

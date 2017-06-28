package ru.a799000.android.weightlogic.mvp.model.intities.load;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 28.06.2017.
 */

public class IntitiesParamLoadHttp {

    @SerializedName("command")
    @Expose
    private String command;
    @SerializedName("str_data_in")
    @Expose
    private String strDataIn;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getStrDataIn() {
        return strDataIn;
    }

    public void setStrDataIn(String strDataIn) {
        this.strDataIn = strDataIn;
    }

    @Override
    public String toString() {
        return "SendModelDataService{" +
                "command='" + command + '\'' +
                ", strDataIn='" + strDataIn + '\'' +
                '}';
    }
}

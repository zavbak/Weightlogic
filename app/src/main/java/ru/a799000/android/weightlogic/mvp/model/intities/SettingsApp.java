package ru.a799000.android.weightlogic.mvp.model.intities;

/**
 * Created by user on 17.06.2017.
 */

public class SettingsApp {
    String mFileName;

    public String getFileNameLoad() {
        return mFileName;
    }

    public void setFileNameLoad(String fileName) {
        mFileName = fileName;
    }

    public String getFileNameSave(){
        return "to1C.json";
    }
}

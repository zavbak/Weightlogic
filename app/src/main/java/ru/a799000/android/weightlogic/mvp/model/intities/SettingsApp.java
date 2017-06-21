package ru.a799000.android.weightlogic.mvp.model.intities;

/**
 * Created by user on 17.06.2017.
 */

public class SettingsApp {
    String mFileName;
    int mCurentProduct;
    int mCurentBarcode;
    int mCurentPallet;

    public int getCurentPallet() {
        return mCurentPallet;
    }

    public void setCurentPallet(int curentPallet) {
        mCurentPallet = curentPallet;
    }

    public int getCurentBarcode() {
        return mCurentBarcode;
    }

    public void setCurentBarcode(int curentBarcode) {
        mCurentBarcode = curentBarcode;
    }

    public String getFileName() {
        return mFileName;
    }

    public void setFileName(String fileName) {
        mFileName = fileName;
    }

    public int getCurentProduct() {
        return mCurentProduct;
    }

    public void setCurentProduct(int curentProduct) {
        mCurentProduct = curentProduct;
    }

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

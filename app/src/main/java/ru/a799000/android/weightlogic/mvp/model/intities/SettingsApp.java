package ru.a799000.android.weightlogic.mvp.model.intities;

/**
 * Created by user on 17.06.2017.
 */

public class SettingsApp {
    String mFileName;
    int mCurentProduct;
    int mCurentBarcode;
    int mCurentPallet;
    String mNameServer;
    String mNameBase;
    String mCode1C;
    String mLogin;
    String mPass;
    Boolean mControlLengthBK;

    public Boolean getControlLengthBK() {
        return mControlLengthBK;
    }

    public void setControlLengthBK(Boolean controlLengthBK) {
        mControlLengthBK = controlLengthBK;
    }

    public String getLogin() {
        return mLogin;
    }

    public void setLogin(String login) {
        mLogin = login;
    }

    public String getPass() {
        return mPass;
    }

    public void setPass(String pass) {
        mPass = pass;
    }

    public String getNameBase() {
        return mNameBase;
    }

    public void setNameBase(String nameBase) {
        this.mNameBase = nameBase;
    }

    public String getNameServer() {
        return mNameServer;
    }

    public void setNameServer(String nameServer) {
        this.mNameServer = nameServer;
    }

    public String getCode1C() {
        return mCode1C;
    }

    public void setCode1C(String code1C) {
        this.mCode1C = code1C;
    }

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

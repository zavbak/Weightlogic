package ru.a799000.android.weightlogic.ui.activityes;

/**
 * Created by user on 17.06.2017.
 */

public interface CallBackScreens {
    void  startGeneralSreenView();
    void startTestScreenView();
    void startProductsScreenView(String id);
    void startDetailProductScreenView(String id);
    void startSettingsScreenView();
    void backStack();
    void startListScreenBarcodes(String id);
    void startBarcodeProductScreenView(String idProduct,String idBarcode);
    void startDetailBarcodeForNewBarcodeScreenView(String idProduct, String barcode);
    void startReportPaletScreenView(String paramIdProduct);
}

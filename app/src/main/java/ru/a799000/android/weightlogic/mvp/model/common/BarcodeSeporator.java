package ru.a799000.android.weightlogic.mvp.model.common;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

import java.math.BigDecimal;

import ru.a799000.android.weightlogic.mvp.model.intities.Product;


/**
 * Created by Alex on 24.05.2017.
 */

public class BarcodeSeporator {

    String barcode;
    Product mProduct;
    String messError;
    float weight;
    int countSimbol;

    SpannableStringBuilder text;



    Boolean error;


    public BarcodeSeporator(String barcode, Product product) {
        this.barcode = barcode;
        mProduct = product;
        countSimbol = barcode.length();

        error = true;
        text = null;



        if (barcode == null) {
            messError = "Не заполнен ШК";
            return;
        }

        if(mProduct.getStart()==0){
            messError = "Не верная Начальная позиция";
            return;
        }

        if(mProduct.getFinish()==0){
            messError = "Не верная Конечной позиция";
            return;
        }


        if (mProduct.getFinish() == mProduct.getStart()) {
            messError = "Начальная и конечная позиция равны";
            return;
        }

        if (mProduct.getFinish() > countSimbol) {
            messError = "Не верная Конечной позиция";
            return;
        }

        if (mProduct.getFinish() < mProduct.getStart()) {
            messError = "Не верная Конечной позиция";
            return;
        }


        int simbolsWeight = 0;
        try {

            simbolsWeight = Integer.parseInt(barcode.substring(mProduct.getStart()-1, mProduct.getFinish()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            messError = "Ошибка получения веса из строки";
            return;
        }

        if (simbolsWeight == 0) {
            messError = "Ошибка получения веса из строки";
            return;
        } else {

            BigDecimal weightBigDecimal = new BigDecimal(Integer.toString(simbolsWeight)).multiply(new BigDecimal(Float.toString(mProduct.getCoef())));
            weight = Float.parseFloat(weightBigDecimal.toString());
            error = false;

            text = new SpannableStringBuilder(mProduct.getInitBarcode());
            ForegroundColorSpan style = new ForegroundColorSpan(Color.rgb(255, 0, 0));
            text.setSpan(style, mProduct.getStart()-1, mProduct.getFinish(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        }

    }

    public SpannableStringBuilder getFormatText() {
        return text;
    }

    public int getCountSimbol() {
        return countSimbol;
    }

    public String getMessError() {
        return messError;
    }

    public float getWeight() {
        return weight;
    }

    public Boolean getError() {
        return error;
    }
}

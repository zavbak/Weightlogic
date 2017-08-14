package ru.a799000.android.weightlogic.mvp.model.intities;

import java.math.BigDecimal;

/**
 * Created by Alex on 11.08.2017.
 */

public class PaletSumResult {

    int mPullet;

    int mPlaces;
    float mWeight;

    Product mProduct;

    public Product getProduct() {
        return mProduct;
    }

    public void setProduct(Product product) {
        mProduct = product;
    }

    public PaletSumResult(int pullet, Product product) {
        mPullet = pullet;
        mProduct = product;
    }

    public int getPlaces() {
        return mPlaces;
    }

    public void setPlaces(int places) {
        mPlaces = places;
    }

    public float getWeight() {
        return mWeight;
    }

    public void setWeight(float weight) {
        mWeight = weight;
    }

    public int getPullet() {
        return mPullet;
    }

    @Override
    public String toString() {
        return "PaletSumResult{" +
                "mPullet=" + mPullet +
                ", mPlaces=" + mPlaces +
                ", mWeight=" + mWeight +
                '}';
    }
}

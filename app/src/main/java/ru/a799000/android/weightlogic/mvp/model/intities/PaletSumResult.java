package ru.a799000.android.weightlogic.mvp.model.intities;

import java.math.BigDecimal;

/**
 * Created by Alex on 11.08.2017.
 */

public class PaletSumResult {

    int mPullet;

    int mPlaces;
    float mWeight;

    public PaletSumResult(int pullet) {
        mPullet = pullet;
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

    @Override
    public String toString() {
        return "PaletSumResult{" +
                "mPullet=" + mPullet +
                ", mPlaces=" + mPlaces +
                ", mWeight=" + mWeight +
                '}';
    }
}

package ru.a799000.android.weightlogic.mvp.model.intities;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Alex on 23.05.2017.
 */

public class Barcode extends RealmObject {
    @PrimaryKey
    long id;
    String barcode;
    int places;
    float weight;


    public Barcode() {


    }

    public Barcode(Builder barcodeBuilder) {
        this.id = barcodeBuilder.id;
        this.barcode = barcodeBuilder.barcode;
        this.places = barcodeBuilder.places;
        this.weight = barcodeBuilder.weight;

    }

    public static Builder getBuilder() {
        return new Barcode().new Builder();
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public int getPlaces() {
        return places;
    }

    public void setPlaces(int places) {
        this.places = places;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }


    @Override
    public String toString() {
        return "Barcode{" +
                "id=" + id +
                ", barcode='" + barcode + '\'' +
                ", places=" + places +
                ", weight=" + weight +
                '}';
    }


    public  class Builder {

        long id;
        String barcode;
        int places;
        float weight;

        public Builder() {
        }


        public Builder setBarcode(String barcode) {
            this.barcode = barcode;
            return this;
        }

        public Builder setPlaces(int places) {
            this.places = places;
            return this;
        }

        public Builder setWeight(float weight) {
            this.weight = weight;
            return this;
        }

        public Barcode build() {
            isValidateEmployeeData();
            return new Barcode(this);
        }

        private boolean isValidateEmployeeData() {
            //Do some basic validations to check
            return true;
        }


    }
}

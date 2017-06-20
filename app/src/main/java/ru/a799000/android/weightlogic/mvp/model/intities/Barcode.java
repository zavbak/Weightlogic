package ru.a799000.android.weightlogic.mvp.model.intities;

import java.util.Date;

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
    int pallet;
    float weight;
    Date date;


    public Barcode() {


    }

    public Barcode(Builder barcodeBuilder) {
        this.id = barcodeBuilder.id;
        this.barcode = barcodeBuilder.barcode;
        this.places = barcodeBuilder.places;
        this.weight = barcodeBuilder.weight;
        this.date = barcodeBuilder.date;
        this.pallet = barcodeBuilder.pallet;

    }

    public int getPallet() {
        return pallet;
    }

    public void setPallet(int pallet) {
        this.pallet = pallet;
    }

    public static Builder getBuilder() {
        return new Barcode().new Builder();
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
                ", pallet=" + pallet +
                ", weight=" + weight +
                ", date=" + date +
                '}';
    }

    public class Builder {

        long id;
        String barcode;
        int places;
        int pallet;
        float weight;
        Date date;

        public Builder() {
        }

        public long getId() {
            return id;
        }

        public Date getDate() {
            return date;
        }


        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setDate(Date date) {
            this.date = date;
            return this;
        }


        public Builder setPallet(int pallet) {
            this.pallet = pallet;
            return this;
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



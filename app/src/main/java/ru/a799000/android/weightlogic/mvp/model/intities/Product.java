package ru.a799000.android.weightlogic.mvp.model.intities;


import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Product extends RealmObject {

    @PrimaryKey
    long id;
    String code;
    String unit;
    String name;
    String initBarcode;
    int start;
    int finish;
    float coef;
    RealmList<Barcode> barcodes;


    public Product() {
    }

    private Product(Builder builder) {

        this.id   = builder.id;
        this.code = builder.code;
        this.unit = builder.unit;
        this.name = builder.name;
        this.initBarcode = builder.initBarcode;
        this.start = builder.start;
        this.finish = builder.finish;
        this.coef = builder.coef;

    }

    public static Builder getBuilder() {
        return new Product().new Builder();
    }


    public RealmList<Barcode> getBarcodes() {
        return barcodes;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInitBarcode() {
        return initBarcode;
    }

    public void setInitBarcode(String initBarcode) {
        this.initBarcode = initBarcode;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getFinish() {
        return finish;
    }

    public void setFinish(int finish) {
        this.finish = finish;
    }

    public float getCoef() {
        return coef;
    }

    public void setCoef(float coef) {
        this.coef = coef;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", unit='" + unit + '\'' +
                ", name='" + name + '\'' +
                ", initBarcode='" + initBarcode + '\'' +
                ", onStart=" + start +
                ", finish=" + finish +
                ", coef=" + coef +
                ", barcodes=" + barcodes +
                '}';
    }

    public  class Builder {

        private long id;
        private String code;
        private String unit;
        private String name;
        private String initBarcode;
        private int start;
        private int finish;
        private float coef;


        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder unit(String unit) {
            this.unit = unit;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder initBarcode(String initBarcode) {
            this.initBarcode = initBarcode;
            return this;
        }

        public Builder start(int start) {
            this.start = start;
            return this;
        }

        public Builder finish(int finish) {
            this.finish = finish;
            return this;
        }

        public Builder coef(float coef) {
            this.coef = coef;
            return this;
        }

        private boolean isValidateEmployeeData() {
            //Do some basic validations to check
            return true;
        }

        public Product build() {
            isValidateEmployeeData();
            return new Product(this);
        }
    }
}

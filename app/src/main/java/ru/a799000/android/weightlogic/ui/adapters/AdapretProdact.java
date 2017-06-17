package ru.a799000.android.weightlogic.ui.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import butterknife.BindView;
import io.realm.OrderedRealmCollection;
import io.realm.RealmList;
import ru.a799000.android.weightlogic.R;
import ru.a799000.android.weightlogic.mvp.model.intities.Barcode;
import ru.a799000.android.weightlogic.mvp.model.intities.Product;


public class AdapretProdact extends RealmBaseAdapter<Product> {

    private Context mContext;

    public AdapretProdact(@Nullable OrderedRealmCollection<Product> data) {
        super(data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {


            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_products, parent, false);


            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (adapterData != null) {
            final Product item = adapterData.get(position);
            viewHolder.setProduct(item);
        }
        return convertView;
    }

    private static class ViewHolder {


        TextView tvId;
        TextView tvName;
        TextView tvInfo;


        Product mProduct;
        float mWeight;
        int mSites;


        public ViewHolder(View view) {
            tvId = (TextView)view.findViewById(R.id.tvId);
            tvName = (TextView)view.findViewById(R.id.tvName);
            tvInfo = (TextView)view.findViewById(R.id.tvInfo);
        }

        private void init() {
            RealmList<Barcode> barcodes = mProduct.getBarcodes();
            for (Barcode barcode : barcodes) {
                mSites = mSites + barcode.getPlaces();
                mWeight = mWeight + barcode.getWeight();
            }
        }

        public void setProduct(Product product) {
            mProduct = product;
            init();

            tvId.setText(Long.toString(mProduct.getId()));
            tvName.setText(product.getName() == null ? "" : product.getName());
            String info = String.format("  Вес: %s, Мест: %s", mWeight, mSites);
            tvInfo.setText(info);
        }

    }


}

package ru.a799000.android.weightlogic.ui.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.realm.OrderedRealmCollection;
import io.realm.RealmList;
import ru.a799000.android.weightlogic.R;
import ru.a799000.android.weightlogic.mvp.model.intities.Barcode;
import ru.a799000.android.weightlogic.mvp.model.intities.Product;


public class AdaprerBarcode extends RealmBaseAdapter<Barcode> {

    private Context mContext;

    public AdaprerBarcode(@Nullable OrderedRealmCollection<Barcode> data) {
        super(data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {


            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_barcode, parent, false);


            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (adapterData != null) {
            final Barcode item = adapterData.get(position);
            viewHolder.setProduct(item);
        }
        return convertView;
    }

    private static class ViewHolder {


        TextView tvId;
        TextView tvInfoWeightSites;
        TextView tvInfoBarcodeDate;


        Barcode mBarcode;



        public ViewHolder(View view) {
            tvId = (TextView)view.findViewById(R.id.tvId);
            tvInfoWeightSites = (TextView)view.findViewById(R.id.tvInfoWeightSites);
            tvInfoBarcodeDate = (TextView)view.findViewById(R.id.tvInfoBarcodeDate);
        }



        public void setProduct(Barcode barcode) {
            mBarcode = barcode;


            tvId.setText(Long.toString(mBarcode.getId()));

            String infoWeightSites = String.format("  Вес: %s, Мест: %s", mBarcode.getWeight(), mBarcode.getPlaces());
            tvInfoWeightSites.setText(infoWeightSites);

            String infoBarcodeDate = String.format("(%s),   %s"
                    ,mBarcode.getDate()==null?"":mBarcode.getDate()
                    ,mBarcode.getBarcode()==null?"":mBarcode.getBarcode());


            tvInfoBarcodeDate.setText(infoBarcodeDate);
        }

    }


}

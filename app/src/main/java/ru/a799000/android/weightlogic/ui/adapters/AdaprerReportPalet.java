package ru.a799000.android.weightlogic.ui.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ru.a799000.android.weightlogic.R;
import ru.a799000.android.weightlogic.mvp.model.intities.PaletSumResult;

public class AdaprerReportPalet extends BaseAdapter {

    private Context mContext;
    private List<PaletSumResult> mData;

    public AdaprerReportPalet(Context context, List data) {
        mContext = context;
        mData = data;
    }

    static class ViewHolder {
        TextView txtItem;
    }

    @Override
    public PaletSumResult getItem(int i) {
        return mData.get(i);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_palet_report_screen, viewGroup, false);
            viewHolder = new ViewHolder();
            //viewHolder.txtItem = (TextView) convertView.findViewById(R.id.txtItem);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //viewHolder.txtItem.setText(getItem(position));

        return convertView;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getCount() {
        return mData.size();
    }
}

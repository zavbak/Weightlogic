package ru.a799000.android.weightlogic.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.a799000.android.weightlogic.R;

/**
 * Created by Alex on 16.06.2017.
 */

public class CommandsAdapter extends BaseAdapter {

    List<String> mlist;
    private Context mContext;

    public CommandsAdapter(Context context,List<String> list) {
        this.mlist = list;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public String getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_commands, parent, false);
            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.setData(Integer.toString(position + 1), mlist.get(position));

        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.tvId)
        TextView tvId;
        @BindView(R.id.tvName)
        TextView tvName;


        public ViewHolder(View view) {
            ButterKnife.bind(this,view);
        }

        void setData(String id,String name ){
            tvId.setText(id);
            tvName.setText(name);
        }

    }

}

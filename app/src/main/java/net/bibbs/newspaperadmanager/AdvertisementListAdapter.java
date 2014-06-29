package net.bibbs.newspaperadmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Christopher on 6/29/2014.
 */
public class AdvertisementListAdapter extends BaseAdapter {
    private ArrayList listData;

    private LayoutInflater layoutInflater;

    public AdvertisementListAdapter(Context context, ArrayList listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.advertisement_list_row, null);
            holder = new ViewHolder();

            holder.advertisementNameView = (TextView) convertView.findViewById(R.id.advertisementName);
            holder.advertisementDescriptionView = (TextView) convertView.findViewById(R.id.advertisementDescription);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Advertisement advertisementItem = (Advertisement)listData.get(position);
        holder.advertisementNameView.setText(advertisementItem.getAdvertisementName());
        holder.advertisementDescriptionView.setText(advertisementItem.getAdvertisementDescription());

        return convertView;
    }

    static class ViewHolder {
        TextView advertisementNameView;
        TextView advertisementDescriptionView;
    }
}

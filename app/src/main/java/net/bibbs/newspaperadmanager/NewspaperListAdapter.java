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
public class NewspaperListAdapter extends BaseAdapter {
    private ArrayList listData;

    private LayoutInflater layoutInflater;

    public NewspaperListAdapter(Context context, ArrayList listData) {
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
            convertView = layoutInflater.inflate(R.layout.newspaper_list_row, null);
            holder = new ViewHolder();

            holder.newspaperNameView = (TextView) convertView.findViewById(R.id.newspaper_name);
            holder.newspaperIdView = (TextView) convertView.findViewById(R.id.newspaper_id);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Newspaper newsItem = (Newspaper)listData.get(position);
        holder.newspaperNameView.setText(newsItem.getNewspaperName());
        holder.newspaperIdView.setText(newsItem.getId());

        return convertView;
    }

    static class ViewHolder {
        TextView newspaperNameView;
        TextView newspaperIdView;
    }
}

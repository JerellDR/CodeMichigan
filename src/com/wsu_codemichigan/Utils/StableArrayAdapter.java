package com.wsu_codemichigan.Utils;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.wsu_codemichigan.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Mrgadgetz
 * Date: 8/14/13
 * Time: 1:38 PM
 */
public class StableArrayAdapter extends ArrayAdapter<String> {
    private final Activity activity;
    private final ArrayList<String> objects;
    HashMap< String, Integer> mIdMap = new HashMap<String, Integer>();

    static class ViewHolder {
        public TextView text;
        public ImageView image;
    }

    public StableArrayAdapter(Activity activity, int textViewResourceId, ArrayList<String> objects) {
        super(activity, textViewResourceId, objects);
        this.activity = activity;
        this.objects = objects;
        for (Integer i = 0; i < objects.size(); i++) {
            mIdMap.put(objects.get(i), i);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null) {
            Log.e("StableArrayAdapter", objects.get(position));
            LayoutInflater inflater = activity.getLayoutInflater();
            rowView = inflater.inflate(R.layout.row_layout, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = (TextView) rowView.findViewById(R.id.label);
            viewHolder.image = (ImageView) rowView
                    .findViewById(R.id.icon);
            rowView.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder) rowView.getTag();
        String s = objects.get(position);

        //TODO fix, not working properly
        if ( s.toLowerCase().startsWith("un")) {
            holder.image.setImageResource(android.R.drawable.star_off);
        } else {
            holder.image.setImageResource(android.R.drawable.star_on);
            holder.text.setText(s);
        }
        return rowView;
    }


    @Override
    public long getItemId(int position) {
        String item = getItem(position);
        return mIdMap.get(item);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
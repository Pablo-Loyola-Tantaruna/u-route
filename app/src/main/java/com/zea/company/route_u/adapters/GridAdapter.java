package com.zea.company.route_u.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.zea.company.route_u.R;

public class GridAdapter extends BaseAdapter {

    Context context;
    String[] flowersName;
    int[] image;

    LayoutInflater inflater;

    public GridAdapter(Context context, String[] flowersName, int[] image) {
        this.context = context;
        this.flowersName = flowersName;
        this.image = image;
    }

    @Override
    public int getCount() {
        return flowersName.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater==null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView==null){
            convertView = inflater.inflate(R.layout.gridview_item,null);
        }
        ImageButton imageButton = convertView.findViewById(R.id.grid_image);
        TextView textView = convertView.findViewById(R.id.item_name);

        imageButton.setImageResource(image[position]);
        textView.setText(flowersName[position]);
        return convertView;
    }
}

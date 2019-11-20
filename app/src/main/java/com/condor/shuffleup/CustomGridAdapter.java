package com.condor.shuffleup;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

public class CustomGridAdapter extends BaseAdapter {
    private Activity mContext;

    // Keep all Images in array
    public ArrayList<Integer> mThumbIds;

    // Constructor
    public CustomGridAdapter(Activity mainActivity, ArrayList<Integer> items) {
        this.mContext = mainActivity;
        this.mThumbIds = items;
    }

    @Override
    public int getCount() {
        return mThumbIds.size();
    }

    @Override
    public Object getItem(int position) {
        return mThumbIds;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(mThumbIds.get(position));

        imageView.setLayoutParams(new GridView.LayoutParams(320, 480));
        return imageView;
    }

}
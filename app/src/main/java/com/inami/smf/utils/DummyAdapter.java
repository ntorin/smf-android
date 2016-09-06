package com.inami.smf.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.inami.smf.R;

/**
 * Created by Ntori on 9/5/2016.
 */
public class DummyAdapter<T> extends ArrayAdapter<T> {


    private LayoutInflater mInflater;

    public DummyAdapter(Context context, int resource, T[] objects, LayoutInflater inflater) {
        super(context, resource, (T[]) DummyItems.items);
        mInflater = inflater;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = mInflater.inflate(R.layout.item_layouts, parent, false);
        ListPackage listPackage = (ListPackage) getItem(position);
        switch (listPackage.getType()){
            case 0:
                v.findViewById(R.id.profile_preview).setVisibility(View.VISIBLE);
                break;
            case 1:
                v.findViewById(R.id.group_preview).setVisibility(View.VISIBLE);
                break;
            case 2:
                v.findViewById(R.id.message_preview).setVisibility(View.VISIBLE);
                break;
            case 3:
                v.findViewById(R.id.thread_post).setVisibility(View.VISIBLE);
                break;
            case 4:
                v.findViewById(R.id.thread_original_post).setVisibility(View.VISIBLE);
                break;
            case 5:
                v.findViewById(R.id.thread_preview).setVisibility(View.VISIBLE);
                break;
        }
        return v;
    }
}

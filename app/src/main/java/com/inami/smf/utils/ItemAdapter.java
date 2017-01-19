package com.inami.smf.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.inami.smf.R;

import java.util.ArrayList;

/**
 * Created by Ntori on 9/5/2016.
 */
public class ItemAdapter<T> extends ArrayAdapter<T> {


    private LayoutInflater mInflater;
    private int mType;

    public ItemAdapter(Context context, int resource, ArrayList<T> objects, LayoutInflater inflater, int type) {
        super(context, resource, objects);
        mInflater = inflater;
        mType = type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = mInflater.inflate(R.layout.item_layouts, parent, false);
        switch (mType){
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
                setupThreadPreview(v);
                break;
        }
        return v;
    }

    private void setupThreadPreview(View v) {
        v.findViewById(R.id.thread_preview).setVisibility(View.VISIBLE);
    }
}

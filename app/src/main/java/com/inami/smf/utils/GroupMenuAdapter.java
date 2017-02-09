package com.inami.smf.utils;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.inami.smf.R;

import java.util.ArrayList;

/**
 * Created by Ntori on 2/7/2017.
 */

public class GroupMenuAdapter extends ArrayAdapter<String>{

    private LayoutInflater mInflater;
    private GroupPreview mGroupPreview;

    public GroupMenuAdapter(Context context, int resource, LayoutInflater inflater, GroupPreview groupPreview) {
        super(context, resource, new String[]{"", "", "", ""});

        mInflater = inflater;
        mGroupPreview = groupPreview;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View v = mInflater.inflate(R.layout.group_menu_layouts, parent, false);
        switch (position){
            case 0:
                setupGroupBBS(v);
                break;
            case 1:
                setupThreads(v);
                break;
            case 2:
                setupPosts(v);
                break;
            case 3:
                setupMembers(v);
                break;
        }
        return v;
    }

    private void setupMembers(View v) {
        v.findViewById(R.id.group_menu_members).setVisibility(View.VISIBLE);
    }

    private void setupPosts(View v) {
        v.findViewById(R.id.group_menu_posts).setVisibility(View.VISIBLE);

    }

    private void setupThreads(View v) {
        v.findViewById(R.id.group_menu_threads).setVisibility(View.VISIBLE);

    }

    private void setupGroupBBS(View v) {
        v.findViewById(R.id.group_menu_bbs).setVisibility(View.VISIBLE);
    }

}

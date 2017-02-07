package com.inami.smf.utils;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Ntori on 2/7/2017.
 */

public class GroupMenuAdapter extends ArrayAdapter<GroupMenu>{

    public GroupMenuAdapter(Context context, int resource, ArrayList<GroupMenu> objects) {
        super(context, resource, objects);
    }

}

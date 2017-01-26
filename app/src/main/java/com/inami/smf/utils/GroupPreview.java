package com.inami.smf.utils;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by Ntori on 1/26/2017.
 */

public class GroupPreview {

    public GroupPreview() {
    }

    public static GroupPreview createGroupPreview(DataSnapshot dataSnapshot) {
        GroupPreview gp = new GroupPreview();
        return gp;
    }
}

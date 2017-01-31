package com.inami.smf.utils;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

/**
 * Created by Ntori on 1/26/2017.
 */

public class GroupPreview {

    String groupID;
    String creatorID;
    String groupName;
    String groupScreenID;
    String groupShortDescription;
    String[] groupTags;
    long groupPostCount;
    long groupMemberCount;
    long unixStamp;

    public GroupPreview() {
    }

    public GroupPreview(String groupID, String creatorID, String groupName, String groupScreenID, String groupShortDescription, String[] groupTags, long groupPostCount, long groupMemberCount, long unixStamp) {
        this.groupID = groupID;
        this.creatorID = creatorID;
        this.groupName = groupName;
        this.groupScreenID = groupScreenID;
        this.groupShortDescription = groupShortDescription;
        this.groupTags = groupTags;
        this.groupPostCount = groupPostCount;
        this.groupMemberCount = groupMemberCount;
        this.unixStamp = unixStamp;
    }

    public String getGroupID() {
        return groupID;
    }

    public String getCreatorID() {
        return creatorID;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getGroupScreenID() {
        return groupScreenID;
    }

    public String getGroupShortDescription() {
        return groupShortDescription;
    }

    public String[] getGroupTags() {
        return groupTags;
    }

    public long getGroupPostCount() {
        return groupPostCount;
    }

    public long getGroupMemberCount() {
        return groupMemberCount;
    }

    public long getUnixStamp() {
        return unixStamp;
    }

    public static GroupPreview createGroupPreview(DataSnapshot dataSnapshot) {
        GroupPreview gp;
        String groupID = dataSnapshot.getKey();
        String creatorID = (String) dataSnapshot.child("creatorid").getValue();
        String groupName = (String) dataSnapshot.child("groupname").getValue();
        String groupScreenID = (String) dataSnapshot.child("groupscreenid").getValue();
        String groupShortDescription = (String) dataSnapshot.child("groupshortdescription").getValue();
        Log.d("groupPreview", "" + groupShortDescription);


        long memberCount = 0;
        if(dataSnapshot.child("unixstamp").getValue() != null) {
            memberCount = (long) dataSnapshot.child("membercount").getValue();
        }

        long postCount = 0;
        if(dataSnapshot.child("unixstamp").getValue() != null) {
            postCount = (long) dataSnapshot.child("postcount").getValue();
        }

        long unixStamp = 0;
        if(dataSnapshot.child("unixstamp").getValue() != null) {
            unixStamp = (long) dataSnapshot.child("unixstamp").getValue();
        }

        ArrayList<String> tags = new ArrayList<>();
        for( DataSnapshot d : dataSnapshot.child("grouptags").getChildren()){
            tags.add((String) d.getValue());
        }

        gp = new GroupPreview(groupID, creatorID, groupName, groupScreenID, groupShortDescription, tags.toArray(new String[tags.size()]), postCount, memberCount, unixStamp);
        return gp;
    }
}

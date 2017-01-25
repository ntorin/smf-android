package com.inami.smf.utils;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

/**
 * Created by Ntori on 1/17/2017.
 */

public class ThreadPreview {
    private String threadTitle;
    private String[] threadTags;
    private String opID;
    private String threadID;
    private long unixStamp;



    public String[] getThreadTags() {
        return threadTags;
    }

    public String getOpID() {
        return opID;
    }

    public String getThreadID() {
        return threadID;
    }

    public long getUnixStamp() {
        return unixStamp;
    }

    public String getThreadTitle() {

        return threadTitle;
    }

    public ThreadPreview(String threadTitle, String[] threadTags, String opID, String threadID, long unixStamp) {
        this.threadTitle = threadTitle;
        this.threadTags = threadTags;
        this.opID = opID;
        this.threadID = threadID;
        this.unixStamp = unixStamp;
    }

    public ThreadPreview() {

    }

    public static ThreadPreview createThreadPreview(DataSnapshot dataSnapshot) {
        ThreadPreview tp;
        String threadID = dataSnapshot.getKey();
        String threadTitle = (String) dataSnapshot.child("threadtitle").getValue();
        String opID = (String) dataSnapshot.child("userid").getValue();
        long unixStamp = 0;
        if(dataSnapshot.child("unixstamp").getValue() != null) {
            unixStamp = (long) dataSnapshot.child("unixstamp").getValue();
        }
        ArrayList<String> tags = new ArrayList<>();
        for( DataSnapshot d : dataSnapshot.child("threadtags").getChildren()){
            tags.add((String) d.getValue());
        }

        tp = new ThreadPreview(threadTitle, tags.toArray(new String[tags.size()]), opID, threadID, unixStamp);
        return tp;
    }
}

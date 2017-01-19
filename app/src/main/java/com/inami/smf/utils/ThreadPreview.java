package com.inami.smf.utils;

/**
 * Created by Ntori on 1/17/2017.
 */

public class ThreadPreview {
    String threadTitle;
    String threadContent;
    String[] threadTags;
    String opID;
    String threadID;
    String opScreenName;
    long unixStamp;

    public ThreadPreview(String threadTitle, String[] threadTags, String opID, String threadID, long unixStamp) {
        this.threadTitle = threadTitle;
        this.threadContent = threadContent;
        this.threadTags = threadTags;
        this.opID = opID;
        this.threadID = threadID;
        this.opScreenName = opScreenName;
        this.unixStamp = unixStamp;
    }

    public ThreadPreview() {

    }
}

package com.inami.smf.utils;

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
}

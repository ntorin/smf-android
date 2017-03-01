package com.inami.smf.utils;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by Ntori on 2/28/2017.
 */

public class MessagePreview {
    private String messageID;
    private String messageTitle;
    private String[] messageTags;
    private String opID;
    private long unixStamp;

    public MessagePreview(String messageID, String messageTitle, String[] messageTags, String opID, long unixStamp) {
        this.messageID = messageID;
        this.messageTitle = messageTitle;
        this.messageTags = messageTags;
        this.opID = opID;
        this.unixStamp = unixStamp;
    }

    public MessagePreview() {
    }

    public static MessagePreview createMessagePreview(DataSnapshot dataSnapshot) {
        return null;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String[] getMessageTags() {
        return messageTags;
    }

    public void setMessageTags(String[] messageTags) {
        this.messageTags = messageTags;
    }

    public String getOpID() {
        return opID;
    }

    public void setOpID(String opID) {
        this.opID = opID;
    }

    public long getUnixStamp() {
        return unixStamp;
    }

    public void setUnixStamp(long unixStamp) {
        this.unixStamp = unixStamp;
    }
}

package com.inami.smf.utils;

import android.os.Message;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

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
        MessagePreview mp;
        String messageID = dataSnapshot.getKey();
        String messageTitle = (String) dataSnapshot.child("messagetitle").getValue();
        String opID = (String) dataSnapshot.child("userid").getValue();
        long unixStamp = 0;
        if(dataSnapshot.child("unixstamp").getValue() != null) {
            unixStamp = (long) dataSnapshot.child("unixstamp").getValue();
        }
        ArrayList<String> tags = new ArrayList<>();
        for( DataSnapshot d : dataSnapshot.child("threadtags").getChildren()){
            tags.add(d.getKey());
        }

        mp = new MessagePreview(messageID, messageTitle, tags.toArray(new String[tags.size()]), opID, unixStamp);

        return mp;
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

package com.inami.smf.utils;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by Ntori on 1/20/2017.
 */

public class Post {
    private String postID;
    private String userID;
    private String postContent;
    private long unixStamp;

    public Post() {
    }

    public Post(String postID, String userID, String postContent, long unixStamp) {
        this.postID = postID;
        this.userID = userID;
        this.postContent = postContent;
        this.unixStamp = unixStamp;
    }

    public String getPostID() {
        return postID;
    }

    public String getUserID() {
        return userID;
    }

    public String getPostContent() {
        return postContent;
    }

    public long getUnixStamp() {
        return unixStamp;
    }

    public static Post createPost(DataSnapshot dataSnapshot) {

        Post p;
        String postID = dataSnapshot.getKey();
        String postContent = (String) dataSnapshot.child("content").getValue();
        String userID = (String) dataSnapshot.child("userid").getValue();
        long unixStamp = 0;
        if(dataSnapshot.child("unixstamp").getValue() != null) {
            unixStamp = (long) dataSnapshot.child("unixstamp").getValue();
        }

        p = new Post(postID, userID, postContent, unixStamp);
        return p;
    }
}

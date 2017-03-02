package com.inami.smf.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Ntori on 3/1/2017.
 */

public class Search {
    private static DatabaseReference mDatabase;
    private static FirebaseAuth firebaseRef;
    public static String getUser(String userid){
        firebaseRef = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        final String[] Uid = new String[1];

        mDatabase.child("users").orderByChild("accountid").equalTo(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Uid[0] = dataSnapshot.getKey();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return Uid[0];
    }
}

package com.inami.smf.utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.inami.smf.R;

import java.util.ArrayList;

/**
 * Created by Ntori on 9/5/2016.
 */
public class ItemAdapter<T> extends ArrayAdapter<T> {

    DatabaseReference mDatabase;
    FirebaseAuth firebaseRef;
    String Uid;


    private LayoutInflater mInflater;
    private int mType;

    public ItemAdapter(Context context, int resource, ArrayList<T> objects, LayoutInflater inflater, int type, DatabaseReference databaseReference) {
        super(context, resource, objects);

        firebaseRef = FirebaseAuth.getInstance();
        Uid = firebaseRef.getCurrentUser().getUid();
        if(databaseReference != null) {
            mDatabase = databaseReference;
        }else {
            mDatabase = FirebaseDatabase.getInstance().getReference();
        }
        mInflater = inflater;
        mType = type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = mInflater.inflate(R.layout.item_layouts, parent, false);
        T item = getItem(position);
        switch (mType) {
            case ItemTypes.PROFILE_PREVIEW:
                v.findViewById(R.id.profile_preview).setVisibility(View.VISIBLE);
                break;
            case ItemTypes.GROUP_PREVIEW:
                setupGroupPreview(v, item);
                break;
            case ItemTypes.MESSAGE_PREVIEW:
                setupMessagePreview(v, item);
                break;
            case ItemTypes.THREAD_POST:
                setupPost(v, item);
                break;
            case ItemTypes.THREAD_ORIGINAL_POST:
                v.findViewById(R.id.thread_original_post).setVisibility(View.VISIBLE);
                break;
            case ItemTypes.THREAD_PREVIEW:
                setupThreadPreview(v, item);
                break;
        }
        return v;
    }

    private void setupMessagePreview(View v, T item) {
        v.findViewById(R.id.message_preview).setVisibility(View.VISIBLE);
        MessagePreview mp = (MessagePreview) item;

        final TextView screenName = (TextView) v.findViewById(R.id.message_preview_sender);

        mDatabase.child("users").child(mp.getOpID()).child("screenname").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                screenName.setText((String) dataSnapshot.getValue());
                Log.d("screenName", "" + dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        TextView messageTitle = (TextView) v.findViewById(R.id.message_preview_title);
        messageTitle.setText(mp.getMessageTitle());

        final TextView messageContent = (TextView) v.findViewById(R.id.message_preview_message);

        mDatabase.child("posts").child(mp.getMessageID()).orderByChild("unixstamp").limitToFirst(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    messageContent.setText((String) d.child("content").getValue());
                    Log.d("post", "" + d.child("content").getValue());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setupGroupPreview(View v, T item) {
        v.findViewById(R.id.group_preview).setVisibility(View.VISIBLE);
        GroupPreview gp = (GroupPreview) item;

        TextView groupName = (TextView) v.findViewById(R.id.group_preview_name);
        TextView groupID = (TextView) v.findViewById(R.id.group_preview_id);
        TextView groupShortDescription = (TextView) v.findViewById(R.id.group_preview_short_description);
        TextView groupPostCount = (TextView) v.findViewById(R.id.group_preview_post_count);
        TextView groupMemberCount = (TextView) v.findViewById(R.id.group_preview_member_count);

        groupName.setText(gp.getGroupName());
        groupID.setText(gp.getGroupScreenID());
        groupShortDescription.setText(gp.getGroupShortDescription());
        groupPostCount.setText(String.valueOf(gp.getGroupPostCount()));
        groupMemberCount.setText(String.valueOf(gp.getGroupMemberCount()));
    }

    private void setupPost(View v, T item) {
        v.findViewById(R.id.thread_post).setVisibility(View.VISIBLE);
        Post p = (Post) item;

        final TextView screenName = (TextView) v.findViewById(R.id.replier_screen_name);

        mDatabase.child("users").child(p.getUserID()).child("screenname").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                screenName.setText((String) dataSnapshot.getValue());
                Log.d("screenName", "" + dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        TextView postContent = (TextView) v.findViewById(R.id.replier_content);
        postContent.setText(p.getPostContent());
    }

    private void setupThreadPreview(View v, T item) {

        v.findViewById(R.id.thread_preview).setVisibility(View.VISIBLE);
        ThreadPreview tp = (ThreadPreview) item;

        final TextView screenName = (TextView) v.findViewById(R.id.preview_thread_op);

        mDatabase.child("users").child(tp.getOpID()).child("screenname").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                screenName.setText((String) dataSnapshot.getValue());
                Log.d("screenName", "" + dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        TextView threadTitle = (TextView) v.findViewById(R.id.preview_thread_title);
        threadTitle.setText(tp.getThreadTitle());

        final TextView threadContent = (TextView) v.findViewById(R.id.preview_thread_content);

         mDatabase.child("posts").child(tp.getThreadID()).orderByChild("unixstamp").limitToFirst(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    threadContent.setText((String) d.child("content").getValue());
                    Log.d("post", "" + d.child("content").getValue());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        LinearLayout tagList = (LinearLayout) v.findViewById(R.id.preview_thread_tags);
        for (String tag : tp.getThreadTags()){
            TextView textView = new TextView(getContext());
            textView.setText("#" + tag);
            tagList.addView(textView);
        }
    }
}

package com.inami.smf.utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

    public ItemAdapter(Context context, int resource, ArrayList<T> objects, LayoutInflater inflater, int type) {
        super(context, resource, objects);

        firebaseRef = FirebaseAuth.getInstance();
        Uid = firebaseRef.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mInflater = inflater;
        mType = type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = mInflater.inflate(R.layout.item_layouts, parent, false);
        T item = getItem(position);
        switch (mType) {
            case 0:
                v.findViewById(R.id.profile_preview).setVisibility(View.VISIBLE);
                break;
            case 1:
                v.findViewById(R.id.group_preview).setVisibility(View.VISIBLE);
                break;
            case 2:
                v.findViewById(R.id.message_preview).setVisibility(View.VISIBLE);
                break;
            case 3:
                v.findViewById(R.id.thread_post).setVisibility(View.VISIBLE);
                break;
            case 4:
                v.findViewById(R.id.thread_original_post).setVisibility(View.VISIBLE);
                break;
            case 5:
                setupThreadPreview(v, item);
                break;
        }
        return v;
    }

    private void setupThreadPreview(View v, T item) {

        v.findViewById(R.id.thread_preview).setVisibility(View.VISIBLE);
        ThreadPreview tp = (ThreadPreview) item;

        final TextView screenName = (TextView) v.findViewById(R.id.preview_thread_op);

        mDatabase.child("users").child(tp.opID).child("screenname").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                screenName.setText((String) dataSnapshot.getValue());
                Log.d("screenName", "" + dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        screenName.setText(tp.opScreenName);

        TextView threadTitle = (TextView) v.findViewById(R.id.preview_thread_title);
        threadTitle.setText(tp.threadTitle);

        final TextView threadContent = (TextView) v.findViewById(R.id.preview_thread_content);

        /* mDatabase.child("posts").child(tp.threadID).orderByChild("unixstamp").limitToFirst(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                threadContent.setText((String) dataSnapshot.child("content").getValue());
                Log.d("post", "" + dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        }); */

        //threadContent.setText(tp.threadContent);
    }
}

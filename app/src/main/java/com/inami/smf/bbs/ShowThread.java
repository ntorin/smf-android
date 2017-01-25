package com.inami.smf.bbs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inami.smf.R;
import com.inami.smf.utils.ItemAdapter;
import com.inami.smf.utils.ItemTypes;
import com.inami.smf.utils.Post;
import com.inami.smf.utils.ThreadPreview;

import java.util.ArrayList;

public class ShowThread extends AppCompatActivity {

    private ListView mListView;

    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseRef;
    private String Uid;

    private ItemAdapter<Post> mItemAdapter;

    private ArrayList<Post> mPostList;
    private ArrayList<String> mKeys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_thread);

        firebaseRef = FirebaseAuth.getInstance();
        Uid = firebaseRef.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Bundle b = getIntent().getExtras();
        String threadTitle = b.getString("threadtitle");
        final String threadID = b.getString("threadid");

        mPostList = new ArrayList<>();
        mKeys = new ArrayList<>();

        mItemAdapter = new ItemAdapter<>(this, R.layout.item_list, mPostList, getLayoutInflater(), ItemTypes.THREAD_POST);

        mListView = (ListView) findViewById(R.id.showthread_replies);
        mListView.setAdapter(mItemAdapter);


        setTitle(threadTitle);

        mDatabase.child("posts").child(threadID).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String key = dataSnapshot.getKey();
                mKeys.add(key);

                Post post = Post.createPost(dataSnapshot);
                mPostList.add(post);

                mItemAdapter.notifyDataSetChanged();

                Log.d("onChildAdded", "" + dataSnapshot.getValue());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String key = dataSnapshot.getKey();
                int i = mKeys.indexOf(key);
                Post post = Post.createPost(dataSnapshot);
                mPostList.set(i, post);
                mItemAdapter.notifyDataSetChanged();

                Log.d("onChildChanged", "" + dataSnapshot.getValue());

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                int i = mKeys.indexOf(key);
                mPostList.remove(i);
                mItemAdapter.notifyDataSetChanged();

                Log.d("onChildRemoved", "" + dataSnapshot.getValue());

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.d("onChildMoved", "" + dataSnapshot.getValue());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        Button reply = (Button) findViewById(R.id.showthread_btn_reply);

        reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ShowThread.this, ReplyThread.class);
                Bundle b = new Bundle();
                b.putString("threadid", threadID);
                i.putExtras(b);
                startActivity(i);
            }
        });
    }


}

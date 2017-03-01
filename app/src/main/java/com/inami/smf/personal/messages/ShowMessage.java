package com.inami.smf.personal.messages;

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
import com.inami.smf.bbs.ReplyThread;
import com.inami.smf.utils.ItemAdapter;
import com.inami.smf.utils.ItemTypes;
import com.inami.smf.utils.Post;
import com.inami.smf.utils.ThreadPreview;

import java.util.ArrayList;

public class ShowMessage extends AppCompatActivity {

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
        setContentView(R.layout.activity_show_message);


        firebaseRef = FirebaseAuth.getInstance();
        Uid = firebaseRef.getCurrentUser().getUid();
            mDatabase = FirebaseDatabase.getInstance().getReference();


        Bundle b = getIntent().getExtras();
        String messageTitle = b.getString("messagetitle");
        final String messageID = b.getString("messageid");

        mPostList = new ArrayList<>();
        mKeys = new ArrayList<>();

        mItemAdapter = new ItemAdapter<>(this, R.layout.item_list, mPostList, getLayoutInflater(), ItemTypes.THREAD_POST, null);

        mListView = (ListView) findViewById(R.id.showmessage_replies);
        mListView.setAdapter(mItemAdapter);


        setTitle(messageTitle);

        mDatabase.child("posts").child(messageID).addChildEventListener(new ChildEventListener() {
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


        Button reply = (Button) findViewById(R.id.showmessage_btn_reply);

        reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ShowMessage.this, ReplyThread.class);
                Bundle b = new Bundle();
                b.putString("messageid", messageID);
                i.putExtras(b);
                startActivity(i);
            }
        });
    }


}

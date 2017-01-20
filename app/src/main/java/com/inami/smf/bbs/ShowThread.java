package com.inami.smf.bbs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inami.smf.R;
import com.inami.smf.utils.ItemAdapter;
import com.inami.smf.utils.ItemTypes;
import com.inami.smf.utils.Post;

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

        mItemAdapter = new ItemAdapter<>(this, R.layout.item_list, mPostList, getLayoutInflater(), ItemTypes.THREAD_POST);

        mListView = (ListView) findViewById(R.id.showthread_replies);


        setTitle(threadTitle);
    }
}
